<template>
  <div ref="container" class="frc-captcha"
       data-sitekey="FCMMIMMO56VDHAQ7"
  ></div>
</template>

<script>
import { WidgetInstance } from "friendly-challenge";
import { ref } from "vue";
import friendlyCaptchaController from "./controller/friendly-captcha.controller";

export default {
  data() {
    return {
      container: ref(),
      widget: ref(),
    };
  },
  methods: {
    async verifyCaptcha(solution) {
      return await friendlyCaptchaController.verifyFriendlyCaptcha(solution);
    },
    async doneCallback(solution) {
      const response = await this.verifyCaptcha(solution);
      const { success, errors} = response;
      if (success) {
        console.info("Captcha solved");
        this.$emit("update", true);
      } else {
        console.log("There was an error when trying to solve the Captcha.");
        console.log(errors);
        this.widget.reset();
        this.$emit("update", false);
      }
    },
    errorCallback(err) {
      console.log("There was an error when trying to solve the Captcha.");
      console.log(err);
      this.$emit("update", false);
      //reset the captcha
      this.widget.reset();
    },
  },
  mounted() {
    if (this.$refs.container) {
      this.widget = new WidgetInstance(this.$refs.container, {
        startMode: "none",
        doneCallback: this.doneCallback,
        errorCallback: this.errorCallback,
        puzzleEndpoint: "https://api.friendlycaptcha.com/api/v1/puzzle",
        language: "es",
        solutionFieldName: "frc-captcha-solution",
      });
    }
  },
  beforeDestroy() {
    if (this.widget) {
      this.widget.destroy();
    }
  },
};
</script>