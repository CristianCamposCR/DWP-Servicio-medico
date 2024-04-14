<template>
  <div>
    <loading-custom :isLoading="isLoading" />
    <b-modal
      id="modal-confirm-signup"
      title="Confirmación de cuenta"
      centered
      hide-footer
      scrollable
      :no-close-on-backdrop="true"
      @hidden="cleanForm"
      @close="cleanForm"
    >
      <b-form>
        <label
          >Código de verificación:&nbsp;<span class="text-danger"
            >*</span
          ></label
        >
        <b-form-input
          id="verificationCode"
          type="text"
          v-model.trim="v$.code.$model"
          :state="v$.code.$dirty ? !v$.code.$error : null"
          @blur="v$.code.$touch()"
          required
          maxlength="45"
          trim
        ></b-form-input>
        <b-form-invalid-feedback v-if="!v$.code.required.$response">{{
          errorMessages.required
        }}</b-form-invalid-feedback>
        <div class="col-12 mt-4 px-5 d-flex justify-content-center">
          <b-button
            variant="primary"
            class="ml-2"
            :disabled="v$.code.$invalid"
            @click="activateAccount"
          >
            Activar cuenta
          </b-button>
        </div>
      </b-form>
    </b-modal>
  </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers } from "@vuelidate/validators";
import authController from "../services/controller/auth.controller";
import SweetAlertCustom from "../../../kernel/SweetAlertCustom";

export default Vue.extend({
  name: "ConfirmSignup",
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  components: {
    LoadingCustom: () => import("../../../views/components/LoadingCustom.vue"),
  },
  data() {
    return {
      isLoading: false,
      code: "",
      errorMessages: {
        required: "Campo obligatorio",
      },
    };
  },
  methods: {
    async activateAccount() {
      try {
        this.isLoading = true;
        const response = await authController.activateAccount(this.code);
        console.log(response);
        if (!response.error) {
          SweetAlertCustom.successMessage();
          this.$bvModal.hide("modal-confirm-signup");
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    cleanForm() {
      this.code = "";
      this.v$.code.$reset();
    },
  },
  validations() {
    return {
      code: {
        required: helpers.withMessage(this.errorMessages.required, required),
      },
    };
  },
});
</script>