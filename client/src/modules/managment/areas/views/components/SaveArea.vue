<template>
  <div>
    <b-modal
      id="modal-save-area"
      title="Registrar área"
      centered
      hide-footer
      scrollable
      :no-close-on-backdrop="true"
    >
      <b-form>
        <label>Nombre del área :&nbsp;<span class="text-danger">*</span></label>
        <b-form-input
          id="name"
          type="text"
          v-model.trim="v$.area.name.$model"
          :state="v$.area.name.$dirty ? !v$.area.name.$error : null"
          @blur="v$.area.name.$touch()"
          required
          maxlength="45"
          trim
        ></b-form-input>
        <b-form-invalid-feedback v-if="!v$.area.name.required.$response">{{
          errorMessages.required
        }}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="!v$.area.name.valid.$response">{{
          errorMessages.name.valid
        }}</b-form-invalid-feedback>
        <b-form-invalid-feedback
          v-else-if="!v$.area.name.notScript.$response"
          >{{ errorMessages.name.noneScripts }}</b-form-invalid-feedback
        >
        <b-form-invalid-feedback
          v-else-if="!v$.area.name.minLength.$response"
          >{{ errorMessages.name.minLength }}</b-form-invalid-feedback
        >
        <b-form-invalid-feedback
          v-else-if="!v$.area.name.maxLength.$response"
          >{{ errorMessages.name.maxLength }}</b-form-invalid-feedback
        >

        <label class="mt-2"
          >Descripción :&nbsp;<span class="text-danger">*</span></label
        >
        <b-form-textarea
          v-model="v$.area.description.$model"
          :state="
            v$.area.description.$dirty ? !v$.area.description.$error : null
          "
          @blur="v$.area.description.$touch()"
          required
        ></b-form-textarea>
        <b-form-invalid-feedback
          v-for="error in v$.area.description.$errors"
          :key="error.$uid"
        >
          {{ error.$message }}
        </b-form-invalid-feedback>

        <label class="mt-2"
          >Selecciona una imagen :&nbsp;<span class="text-danger"
            >*</span
          ></label
        >
        <b-form-file
          v-model="area.bannerImage"
          :state="Boolean(area.bannerImage)"
          browse-text="Buscar"
          placeholder="Selecciona una imagen"
          drop-placeholder="Suelta el archivo aquí..."
        ></b-form-file>

        <div class="col-12 mt-4 px-5 d-flex justify-content-between">
          <b-button variant="danger" @click="saveArea">Cancelar</b-button>
          <b-button variant="success" class="ml-2" :disabled="v$.area.$invalid"
            >Registrar</b-button
          >
        </div>
      </b-form>
    </b-modal>
  </div>
</template>
<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength, maxLength } from "@vuelidate/validators";
import areaController from "../../services/controller/area.controller";
export default Vue.extend({
  name: "SaveArea",
  setup() {
    return { v$: useVuelidate() };
  },
  data() {
    return {
      area: {
        name: "",
        description: "",
        bannerImage: null,
      },
      errorMessages: {
        required: "Campo obligatorio",
        name: {
          minLength: "Mínimo 2 caracteres",
          maxLength: "Máximo 45 caracteres",
          noneScripts: "Campo inválido no se aceptan scripts",
          valid: "Campos inválido - caracteres inválidos",
        },
      },
    };
  },
  methods: {
    async saveArea() {
      try {
        const resp = await areaController.saveArea({
          name: "Odonotología",
          description: "Descripción odontología",
          bannerImage: null,
        });
        console.log("respuesta save", resp);
      } catch (error) {
        console.log(error);
      }
    },
  },
  validations() {
    return {
      area: {
        name: {
          required: helpers.withMessage(this.errorMessages.required, required),
          minLength: helpers.withMessage(
            this.errorMessages.name.minLength,
            minLength(2)
          ),
          maxLength: helpers.withMessage(
            this.errorMessages.name.maxLength,
            maxLength(45)
          ),
          notScript: helpers.withMessage(
            this.errorMessages.name.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(this.errorMessages.name.valid, (value) =>
            /^[a-zA-Z0-9][a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*$/.test(value)
          ),
        },
        description: {
          required: helpers.withMessage(this.errorMessages.required, required),
        },
      },
    };
  },
});
</script>
