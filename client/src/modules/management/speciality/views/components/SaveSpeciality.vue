<template>
  <div>
    <b-modal
      id="modal-save-speciality"
      title="Registrar especialidad"
      centered
      hide-footer
      scrollable
      :no-close-on-backdrop="true"
      @hidden="cleanForm"
      @close="cleanForm"
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

        <label class="mt-2">
          Selecciona el área de la especialidad :&nbsp;
          <span class="text-danger">*</span>
        </label>
        <b-form-select v-model="speciality.area.id" :options="areasOptions">
          <template #first>
            <b-form-select-option :value="null">
              Selecciona un Área
            </b-form-select-option>
          </template>
        </b-form-select>

        <label class="mt-2">
          Costo :&nbsp;
          <span class="text-danger">*</span>
        </label>
        <b-form-input
          id="cost"
          type="number"
          v-model.trim="v$.speciality.cost.$model"
          :state="v$.speciality.cost.$dirty ? !v$.speciality.cost.$error : null"
          @blur="v$.speciality.cost.$touch()"
          required
          trim
          @keypress="onlynumbers"
          min="0"
        ></b-form-input>
        <b-form-invalid-feedback
          v-if="!v$.speciality.cost.required.$response"
          >{{ errorMessages.required }}</b-form-invalid-feedback
        >
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.cost.valid.$response"
        >
          {{ errorMessages.name.valid }}
        </b-form-invalid-feedback>
        <b-form-invalid-feedback
          v-else-if="!v$.speciality.cost.notScript.$response"
        >
          {{ errorMessages.name.noneScripts }}
        </b-form-invalid-feedback>

        <label class="mt-2">Descripción :&nbsp;</label>
        <b-form-textarea
          v-model="v$.speciality.description.$model"
          :state="
            v$.speciality.description.$dirty
              ? !v$.speciality.description.$error
              : null
          "
          @blur="v$.speciality.description.$touch()"
          required
          rows="3"
          max-rows="6"
          maxlenth="200"
          trim
        ></b-form-textarea>
        <b-form-invalid-feedback
          v-for="error in v$.speciality.description.$errors"
          :key="error.$uid"
        >
          {{ error.$message }}
        </b-form-invalid-feedback>
        <label class="mt-2">
          Imagen:
        </label>
        <b-form-file
          @change="handleFileChange"
          browse-text="Buscar"
          placeholder="Selecciona una imagen"
          drop-placeholder="Suelta el archivo aquí..."
          accept="image/png, image/jpeg"
          :state="validFile"
          ref="banner-image"
        ></b-form-file>
        <b-form-invalid-feedback v-if="validFile == false">{{
          errorMessages.bannerImage.validFile
        }}</b-form-invalid-feedback>

        <b-row class="mt-3" v-if="previewImage">
          <b-col cols="12" class="my-2">
            <div class="d-flex align-items-center justify-content-between">
              <div>
                <p class="mb-0">Previsualización:</p>
              </div>
              <div>
                <b-button
                  variant="primary"
                  size="sm"
                  @click="clearFiles"
                  class="ml-auto"
                  >Quitar imagen</b-button
                >
              </div>
            </div>
          </b-col>
          <b-col cols="12" class="d-flex justify-content-center">
            <b-form>
              <b-form-group>
                <b-img
                  v-if="previewImage"
                  :src="previewImage"
                  fluid
                  thumbnail
                  alt="Image"
                ></b-img>
              </b-form-group>
            </b-form>
          </b-col>
        </b-row>

        <div class="col-12 mt-4 px-5 d-flex justify-content-between">
          <b-button variant="danger" @click="closeModal">Cancelar</b-button>
          <b-button
            variant="success"
            class="ml-2"
            :disabled="v$.speciality.$invalid"
            @click="saveSpeciality"
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
import SweetAlertCustom from "../../../../../kernel/SweetAlertCustom";
import { signal } from "../../../../../kernel/functions";

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
        cost: 0,
        bannerImage: null,
        area: {
          id: null,
          name: null,
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
        description: {
          maxLength: "Máximo 200 caracteres",
        },
        bannerImage: {
          validFile: "El archivo no es una imagen PNG o JPEG",
        },
      },
      previewImage: null,
      validFile: null,
    };
  },
  methods: {
    onlynumbers(evt) {
      signal(evt);
    },
    async getAreas() {
      try {
        const areas = await specialityController.getAreas();
        this.areasOptions = areas.map((area) => ({
          value: area.id,
          text: area.name,
        }));
      } catch (error) {
        console.error("Error al obtener las áreas:", error);
      }
    },

    async saveSpeciality() {
      try {
        const result = await SweetAlertCustom.questionMessage();
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
          minLength: helpers.withMessage(
            this.errorMessages.name.minLength,
            minLength(0)
          ),
          maxLength: helpers.withMessage(
            this.errorMessages.description.maxLength,
            maxLength(200)
          ),
          notScript: helpers.withMessage(
            this.errorMessages.name.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(this.errorMessages.name.valid, (value) =>
            /^(?:[a-zA-Z0-9][a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*)?$/.test(
              value
            )
          ),
        },
        cost: {
          required: helpers.withMessage(this.errorMessages.required, required),
          notScript: helpers.withMessage(
            this.errorMessages.name.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(
            this.errorMessages.name.valid,
            (value) =>
              /^\d+(\.\d{1,2})?$/.test(value) &&
              !isNaN(parseFloat(value)) &&
              parseFloat(value) >= 0
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
