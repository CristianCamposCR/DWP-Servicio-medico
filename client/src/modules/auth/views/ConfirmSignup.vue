<template>
  <div>
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
      <div class="text-danger mb-3 text-center">
        *Importante el código caduca en 5 min.*
      </div>

      <div class="mb-2">
        Ingresa tú codigo de verificación, una vez validado inicia sesión
        nuevamente.
      </div>
      <b-form>
        <label
          >Código de verificación:&nbsp;<span class="text-danger"
            >*</span
          ></label
        >
        <b-form-input
          id="verificationCode"
          type="text"
          placeholder="Ej. AFR88"
          v-model.trim="v$.code.$model"
          :state="v$.code.$dirty ? !v$.code.$error : null"
          @blur="v$.code.$touch()"
          required
          maxlength="5"
          trim
          :formatter="formatter"
        ></b-form-input>
        <b-form-invalid-feedback v-if="!v$.code.required.$response">{{
          errorMessages.required
        }}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="!v$.code.valid.$response">{{
          errorMessages.valid
        }}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="!v$.code.notScript.$response">{{
          errorMessages.noneScripts
        }}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="!v$.code.minLength.$response">{{
          errorMessages.minLength
        }}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="!v$.code.maxLength.$response">{{
          errorMessages.maxLength
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
          <b-button
            variant="secondary"
            class="ml-2"
            v-b-modal.modal-refresh-code
          >
            Reenviar código
          </b-button>
        </div>
      </b-form>
    </b-modal>

    <b-modal
      id="modal-refresh-code"
      title="Reenvio de código de verificación"
      centered
      hide-footer
      scrollable
      :no-close-on-backdrop="true"
      @hidden="cleanForm"
      @close="cleanForm"
    >
      <label>Nombre de usuario:&nbsp;<span class="text-danger">*</span></label>
      <b-form-input
        id="username"
        type="text"
        placeholder="Ej. doctorCR"
        v-model.trim="v$.refreshCode.username.$model"
        :state="
          v$.refreshCode.username.$dirty
            ? !v$.refreshCode.username.$error
            : null
        "
        @blur="v$.refreshCode.username.$touch()"
        required
        maxlength="45"
        trim
      ></b-form-input>
      <b-form-invalid-feedback
        v-if="!v$.refreshCode.username.required.$response"
        >{{ errorMessages.required }}</b-form-invalid-feedback
      >
      <b-form-invalid-feedback
        v-else-if="!v$.refreshCode.username.valid.$response"
        >{{ errorMessages.valid }}</b-form-invalid-feedback
      >
      <b-form-invalid-feedback
        v-else-if="!v$.refreshCode.username.notScript.$response"
        >{{ errorMessages.noneScripts }}</b-form-invalid-feedback
      >
      <b-form-invalid-feedback
        v-else-if="!v$.refreshCode.username.minLength.$response"
        >{{ errorMessages.refreshCode.minLength }}</b-form-invalid-feedback
      >
      <b-form-invalid-feedback
        v-else-if="!v$.refreshCode.username.maxLength.$response"
        >{{ errorMessages.refreshCode.maxLength }}</b-form-invalid-feedback
      >
      <b-form-valid-feedback v-else-if="refreshCode.username.length === 45">{{
        errorMessages.name.allowedMax
      }}</b-form-valid-feedback>
      <div class="col-12 mt-4 px-5 d-flex justify-content-center">
        <b-button
          variant="primary"
          class="ml-2"
          :disabled="v$.refreshCode.username.$invalid"
          @click="refreshActivationCode"
        >
          Envíar
        </b-button>
      </div>
    </b-modal>
  </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength, maxLength } from "@vuelidate/validators";
import authController from "../services/controller/auth.controller";
import SweetAlertCustom from "../../../kernel/SweetAlertCustom";
import { refresh } from "aos";

export default Vue.extend({
  name: "ConfirmSignup",
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    return {
      isLoading: false,
      code: "",
      errorMessages: {
        required: "Campo obligatorio",
        minLength: "Mínimo 5 caracteres",
        maxLength: "Máximo 5 caracteres",
        noneScripts: "Campo inválido no se aceptan scripts",
        valid: "Caracteres inválidos solo se aceptan letras y números",
        refreshCode: {
          minLength: "Mínimo 2 caracteres",
          maxLength: "Máximo 45 caracteres",
        },
      },
      isFor: null,
      refreshCode: {
        username: null,
        mode: 2,
      },
    };
  },
  methods: {
    formatter(value) {
      return value.toUpperCase();
    },
    async activateAccount() {
      try {
        const response = await authController.activateAccount(this.code);
        if (!response.error) {
          SweetAlertCustom.successMessage();
          this.$bvModal.hide("modal-confirm-signup");
        }
      } catch (error) {
        console.log(error);
      }
    },
    async refreshActivationCode() {
      try {
        const response = await authController.refreshActivationCode(
          this.refreshCode
        );
        if (!response.error) {
          SweetAlertCustom.successMessage();
          this.$bvModal.hide("modal-refresh-code");
          this.refreshCode = {
            username: "",
            type: 2,
          };
          this.v$.refreshCode.$reset();
        }
      } catch (error) {
        console.log(error);
      }
    },
    cleanForm() {
      this.code = "";
      this.v$.code.$reset();
    },
    async getRefreshCode() {},
  },
  validations() {
    return {
      code: {
        required: helpers.withMessage(this.errorMessages.required, required),
        minLength: helpers.withMessage(
          this.errorMessages.minLength,
          minLength(5)
        ),
        maxLength: helpers.withMessage(
          this.errorMessages.maxLength,
          maxLength(5)
        ),
        notScript: helpers.withMessage(
          this.errorMessages.noneScripts,
          (value) => {
            return !/<.*?script.*\/?>/gi.test(value);
          }
        ),
        valid: helpers.withMessage(this.errorMessages.valid, (value) =>
          /^[A-Z0-9]*$/.test(value)
        ),
      },
      refreshCode: {
        username: {
          required: helpers.withMessage(this.errorMessages.required, required),
          minLength: helpers.withMessage(
            this.errorMessages.minLength,
            minLength(2)
          ),
          maxLength: helpers.withMessage(
            this.errorMessages.maxLength,
            maxLength(45)
          ),
          notScript: helpers.withMessage(
            this.errorMessages.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(this.errorMessages.valid, (value) =>
            /^[a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*$/.test(value)
          ),
        },
      },
    };
  },
});
</script>
