import { maxLength, minLength } from '@vuelidate/validators';
<template>
  <div>
    <b-modal
      id="modal-save-speciality"
      title="Registrar especialidad"
      centered
      hide-footer
      scrollable
      :no-close-on-backdrop="true"
    >
      <b-form>
        <label>
          Nombre de la especialidad :&nbsp;
          <span class="text-danger">*</span>
        </label>
        <b-form-input
          id="name"
          type="text"
          v-model.trim="v$.speciality.name.$model"
          :state="v$.speciality.name.$dirty ? !v$.speciality.name.$error : null"
          @blur="v$.speciality.name.$touch()"
          required
          maxLength="45"
          trim
        ></b-form-input>
        <b-form-invalid-feedback v-if="!v$.speciality.name.required.$response">
          {{ errorMessages.required }}
        </b-form-invalid-feedback>
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.name.valid.$response"
        >
          {{ errorMessages.name.valid }}
        </b-form-invalid-feedback>
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.name.notScript.$response"
        >
          {{ errorMessages.name.noneScripts }}
        </b-form-invalid-feedback>
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.name.minLength.$response"
        >
          {{ errorMessages.name.minLength }}
        </b-form-invalid-feedback>
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.name.maxLength.$response"
        >
          {{ errorMessages.name.maxLength }}
        </b-form-invalid-feedback>

        <label class="mt-2"
          >Descripción :&nbsp; <span class="text-danger">*</span></label
        >
        <b-form-textarea
          v-model="v$.speciality.description.$model"
          :state="
            v$.speciality.description.$dirty
              ? !v$.speciality.description.$error
              : null
          "
          @blur="v$.speciality.description.$touch()"
          required
        ></b-form-textarea>
        <b-form-invalid-feedback
          v-for="error in v$.speciality.description.$errors"
          :key="error.$uid"
        >
          {{ error.$message }}
        </b-form-invalid-feedback>

        <label class="mt-2">
          Costo :&nbsp;
          <span class="text-danger">*</span>
        </label>
        <b-form-input
          id="costo"
          type="number"
          v-model.trim="v$.speciality.costo.$model"
          :state="
            v$.speciality.costo.$dirty ? !v$.speciality.costo.$error : null
          "
          @blur="v$.speciality.costo.$touch()"
          required
          trim
        ></b-form-input>
        <b-form-invalid-feedback
          v-if="!v$.speciality.costo.required.$response"
          >{{ errorMessages.required }}</b-form-invalid-feedback
        >
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.costo.valid.$response"
        >
          {{ errorMessages.name.valid }}
        </b-form-invalid-feedback>
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.costo.notScript.$response"
        >
          {{ errorMessages.name.noneScripts }}
        </b-form-invalid-feedback>

        <label class="mt-2">
          Selecciona una imagen :&nbsp;
          <span class="text-danger">*</span>
        </label>
        <b-form-file
          v-model="speciality.bannerImage"
          :state="Boolean(speciality.bannerImage)"
          browse-text="Buscar"
          placeholder="Selecciona una imagen"
          drop-placeholder="Suelta el archivo aquí..."
        ></b-form-file>

        <label class="mt-2">
          Selecciona el área de la especialidad :&nbsp;
          <span class="text-danger">*</span>
        </label>
        <b-form-select v-model="selectedArea.id" :options="areasOptions">
          <template #first>
            <b-form-select-option :value="null">
              Selecciona un Área
            </b-form-select-option>
          </template>
        </b-form-select>

        <div class="col-12 mt-4 px-5 d-flex justify-content-between">
          <b-button variant="danger" @click="saveSpeciality">Cancelar</b-button>
          <b-button
            variant="success"
            class="ml-2"
            :disabled="v$.speciality.$invalid"
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
import specialityController from "../../services/controller/speciality.controller";

export default Vue.extend({
  name: "SaveSpeciality",
  setup() {
    return { v$: useVuelidate() };
  },
  data() {
    return {
      areasOptions: [],
      speciality: {
        name: "",
        description: "",
        costo: 0,
        bannerImage: null,
        area: {
          id: "",
          name: "",
        },
      },
      errorMessages: {
        required: "Campo Obligatorio",
        name: {
          minLength: "Mínimo 2 caracteres",
          maxLength: "Máximo 45 caracteres",
          noneScripts: "Campo inválido no se aceptar scripts",
          valid: "Campos inválidos - caracteres inválidos",
        },
      },
    };
  },
  computed: {
    selectedArea() {
      if (this.speciality) {
        return { id: this.speciality.area.id };
      } else {
        return null;
      }
    },
  },
  methods: {
    async saveSpeciality() {
      try {
        const resp = await specialityController.saveSpeciality({
          name: "Otorrinolaringología",
          description: "Descripción",
          costo: 1546.25,
          bannerImage: null,
          area: {
            id: 1,
            name: "Quirúrgica",
          },
        });
        console.log("respuesta save", resp);
      } catch (error) {
        console.log(error);
      }
    },
  },
  validations() {
    return {
      speciality: {
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
        costo: {
          required: helpers.withMessage(this.errorMessages.required, required),
          notScript: helpers.withMessage(
            this.errorMessages.name.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(
            this.errorMessages.name.valid,
            (value) => /^-?\d*\.?\d+$/.test(value) && !isNaN(parseFloat(value))
          ),
        },
      },
    };
  },
});
</script>
