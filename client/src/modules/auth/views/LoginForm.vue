<template>
  <div>
    <b-form @submit.prevent="submitForm">
      <loading-custom :isLoading="isLoading" />
      <div>
        <button
          class="btn btn-transparent position-absolute start-0 top-0 goBack"
          @click="goBack"
          v-b-tooltip.hover.v-info
          title="Regresar"
        >
          <b-icon icon="arrow-left"></b-icon>
        </button>
      </div>
      <b-card-img
        src="/src/assets/Hospital.jpg"
        width="100px"
        height="140px"
        style="object-fit: cover; object-position: center"
      ></b-card-img>
      <div class="mt-5 mx-4">
        <b-col sm="12">
          <b-form-group>
            <b-input-group class="mb-3">
              <b-input
                placeholder="Nombre de usuario"
                type="text"
                required
                v-model.trim="v$.signinPayload.username.$model"
                trim
                :state="
                  v$.signinPayload.username.$dirty
                    ? !v$.signinPayload.username.$error
                    : null
                "
                @blur="v$.signinPayload.username.$touch()"
              ></b-input>
              <b-form-invalid-feedback
                v-for="error in v$.signinPayload.username.$errors"
                :key="error.$uid"
              >
                {{ error.$message }}
              </b-form-invalid-feedback>
            </b-input-group>
          </b-form-group>
          <b-form-group>
            <b-input-group>
              <b-form-input
                :type="showPasswordState ? 'text' : 'password'"
                placeholder="Contraseña"
                style="border-right: none !important"
                required
                v-model.trim="v$.signinPayload.password.$model"
                trim
                :state="
                  v$.signinPayload.password.$dirty
                    ? !v$.signinPayload.password.$error
                    : null
                "
                @blur="v$.signinPayload.password.$touch()"
              >
              </b-form-input>
              <b-input-group-prepend>
                <span
                  class="input-group-text"
                  style="background-color: white; border-left: none !important"
                  @click="showPassword"
                  role="button"
                >
                  <b-icon
                    :icon="showPasswordState ? 'eye-slash' : 'eye'"
                  ></b-icon>
                </span>
              </b-input-group-prepend>
              <b-form-invalid-feedback
                v-for="error in v$.signinPayload.password.$errors"
                :key="error.$uid"
              >
                {{ error.$message }}
              </b-form-invalid-feedback>
            </b-input-group>
          </b-form-group>
        </b-col>
        <b-col class="d-flex justify-content-center">
          <CaptchaFriendly @update="isValidFriendlyCaptcha = $event" />
        </b-col>
      </div>
      <div class="d-flex justify-content-center mt-3 mb-5 mx-4">
        <b-col cols="12" sm="6">
          <b-button
            block
            class="custom-button"
            type="submit"
            :disabled="v$.signinPayload.$invalid || !isValidFriendlyCaptcha"
            @click="sigin"
            variant="primary"
          >
            Iniciar sesión
          </b-button>
          <div class="text-center mt-2 mb-0">
            <b-link>¿Olvidaste tu contraseña?</b-link>
          </div>
        </b-col>
      </div>
    </b-form>
  </div>
</template>
<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers } from "@vuelidate/validators";
import CaptchaFriendly from "@/components/FriendlyCaptcha/CaptchaFriendly.vue";
import { jwtDecode } from "jwt-decode";
import authController from "../services/controller/auth.controller";
import SweetAlertCustom from "../../../kernel/SweetAlertCustom";
import { ERoles } from "../../../kernel/types";
export default Vue.extend({
  name: "LoginForm",
  components: {
    CaptchaFriendly,
    LoadingCustom: () => import("../../../views/components/LoadingCustom.vue"),
  },
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    return {
      isLoading: false,
      showPasswordState: false,
      isValidFriendlyCaptcha: false,
      signinPayload: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    showPassword() {
      this.showPasswordState = !this.showPasswordState;
    },
    async goBack() {
      await this.$router.push("/cimi");
    },
    submitForm() {
      //prefer not active button submit if not valid the captcha
      console.log("Formulario válido", this.isValidFriendlyCaptcha);
    },
    async sigin() {
      try {
        if (this.v$.signinPayload.$invalid) {
          SweetAlertCustom.invalidForm();
        } else {
          this.isLoading = true;
          if (
            this.signinPayload.email != "" &&
            this.signinPayload.password != ""
          ) {
            const response = await authController.login(this.signinPayload);
            if (!response.error) {
              localStorage.setItem("token", response.token);
              if (await this.checkNextRedirect())
                SweetAlertCustom.welcomeMessage();
            } else {
              this.isValidFriendlyCaptcha = false;
            }
          }
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    async checkNextRedirect() {
      if (localStorage.token) {
        if (jwtDecode(localStorage.token).roles[0].authority === ERoles.ADMIN) {
          await this.$router.replace("/management");
          return true;
        } else if (
          jwtDecode(localStorage.token).roles[0].authority === ERoles.DOCTOR
        ) {
          await this.$router.replace("/doctor");
          return true;
        } else if (
          jwtDecode(localStorage.token).roles[0].authority === ERoles.PATIENT
        ) {
          await this.$router.replace("/patient");
          return true;
        }
      }
    },
  },
  validations() {
    return {
      signinPayload: {
        username: {
          required: helpers.withMessage("Campo obligatorio", required),
        },
        password: {
          required: helpers.withMessage("Campo obligatorio", required),
        },
      },
    };
  },
});
</script>
