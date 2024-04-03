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
  methods: {
    async getAreas() {
      try {
        const areas = await specialityController.getAreas();
        this.areasOptions = areas.map((area) => ({
          value: area.id,
          text: area.name,
        }));
        console.log(this.areasOptions);
      } catch (error) {
        console.error("Error al obtener las áreas:", error);
      }
    },
  },
  methods: {
    async saveSpeciality() {
      try {
        SweetAlertCustom.questionMessage().then(async (result) => {
          if (result.isConfirmed) {
            const resp = await specialityController.saveSpeciality(
              this.speciality
            );
            const { error } = resp;
            if (!error) {
              this.$emit("reloadRegisters");
              setTimeout(() => {
                SweetAlertCustom.successMessage();
              }, 1000);
              this.$nextTick(() => this.$bvModal.hide("modal-save-speciality"));
              this.cleanForm();
              return;
            }
          }
        });
        console.log("respuesta save", resp);
      } catch (error) {
        console.log(error);
      }
    },
    cleanForm() {
      this.speciality = {
        name: "",
        description: "",
        costo: 0,
        bannerImage: null,
        area: {
          id: "",
          name: "",
        },
      };
      this.previewImage = null;
      this.validFile = null;
      this.v$.speciality.$reset();
    },
    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        if (!this.isValidImage(file)) {
          this.validFile = false;
          event.target.value = null;
          this.previewImage = null;
          this.speciality.bannerImage = null;
          return;
        }
        this.validFile = true;
        this.previewImage = URL.createObjectURL(file);
        this.convertFileToBase64(file);
      } else {
        this.validFile = true;
        this.previewImage = null;
      }
    },
    isValidImage(file) {
      return file.type === "image/png" || file.type === "image/jpeg";
    },
    convertFileToBase64(file) {
      const reader = new FileReader();

      reader.readAsDataURL(file);

      reader.onload = () => {
        const base64String = reader.result;
        this.speciality.bannerImage = base64String;
      };
    },
    clearFiles() {
      this.$refs["banner-image"].reset();
      this.validFile = null;
      this.previewImage = null;
      this.area.bannerImage = null;
    },
    closeModal() {
      this.$bvModal.hide("modal-save-speciality");
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
  mounted() {
    this.getAreas();
  },
});
</script>
