<template>
  <div>
    <b-modal
      id="modal-update-area"
      centered
      hide-footer
      scrollable
      title="Actualizar área"
      :no-close-on-backdrop="true"
      @hidden="cleanForm"
      @close="cleanForm"
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
        <b-form-valid-feedback v-else-if="area.name?.length === 45">{{
          errorMessages.name.allowedMax
        }}</b-form-valid-feedback>

        <label class="mt-2">Descripción :</label>
        <b-form-textarea
          v-model="v$.area.description.$model"
          :state="
            v$.area.description.$dirty ? !v$.area.description.$error : null
          "
          @blur="v$.area.description.$touch()"
          required
          rows="3"
          max-rows="6"
          maxlength="200"
          trim
        ></b-form-textarea>
        <b-form-invalid-feedback
          v-for="error in v$.area.description.$errors"
          :key="error.$uid"
        >
          {{ error.$message }}
        </b-form-invalid-feedback>
        <b-form-valid-feedback v-if="area.description?.length === 200">{{
          errorMessages.description.allowedMax
        }}</b-form-valid-feedback>

        <label class="mt-2"> Selecciona una imagen :</label>
        <b-form-file
          @change="handleFileChange"
          browse-text="Buscar"
          placeholder="Selecciona una imagen"
          drop-placeholder="Suelta el archivo aquí..."
          accept="image/png, image/jpeg"
          :state="validFile && validSizeFile"
          ref="banner-image"
        ></b-form-file>
        <b-form-invalid-feedback v-if="validFile == false">{{
          errorMessages.bannerImage.validFile
        }}</b-form-invalid-feedback>
        <b-form-invalid-feedback v-else-if="validSizeFile == false">{{
          errorMessages.bannerImage.validSizeFile
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
            variant="primary"
            class="ml-2"
            :disabled="v$.area.$invalid"
            @click="updateArea"
          >
            Actualizar
          </b-button>
        </div>
      </b-form>
    </b-modal>
  </div>
</template>
<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength, maxLength } from "@vuelidate/validators";
import SweetAlertCustom from "../../../../../kernel/SweetAlertCustom";
import areaController from "../../services/controller/area.controller";
export default Vue.extend({
  name: "UpdateArea",
  setup() {
    return { v$: useVuelidate() };
  },
  props: {
    areaSelected: {
      required: true,
      type: Object,
    },
  },
  data() {
    return {
      area: {},
      errorMessages: {
        required: "Campo obligatorio",
        name: {
          minLength: "Mínimo 2 caracteres",
          maxLength: "Máximo 45 caracteres",
          noneScripts: "Campo inválido no se aceptan scripts",
          valid: "Campos inválidos - caracteres inválidos",
          allowedMax: "Se ha alcanzado el máximo de 45 caracteres",
        },
        description: {
          maxLength: "Máximo 200 caracteres",
          allowedMax: "Se ha alcanzado el máximo de 200 caracteres",
        },
        bannerImage: {
          validFile: "El archivo seleccionado no es una imagen PNG o JPEG.",
          validSizeFile: "La imagen supera los 5 mb permitidos",
        },
      },
      previewImage: null,
      validFile: null,
      validSizeFile: null,
    };
  },
  watch: {
    areaSelected() {
      this.area = { ...this.areaSelected };
      this.previewImage = this.area.bannerImage;
    },
  },
  methods: {
    async updateArea() {
      try {
        if (this.v$.area.$invalid) {
          SweetAlertCustom.invalidForm();
        } else {
          const result = await SweetAlertCustom.questionMessage();
          if (result.isConfirmed) {
            const resp = await areaController.update(this.area);
            const { error } = resp;
            if (!error) {
              this.$emit("reloadRegisters2");
              setTimeout(() => {
                SweetAlertCustom.successMessage();
              }, 100);
              this.$nextTick(() => this.$bvModal.hide("modal-update-area"));
              this.cleanForm();
              return;
            }
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    cleanForm() {
      this.area = {
        name: "",
        description: "",
        bannerImage: null,
      };
      this.previewImage = null;
      this.validFile = null;
      this.validSizeFile = null;
      this.v$.area.$reset();
    },
    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        if (!this.isValidImage(file)) {
          this.validFile = false;
          event.target.value = null;
          this.previewImage = null;
          this.area.bannerImage = null;
          return;
        }
        if (this.isInvalidSizeImage(file)) {
          this.validFile = true;
          this.validSizeFile = false;
          event.target.value = null;
          this.previewImage = null;
          this.area.bannerImage = null;
          return;
        }
        this.validFile = true;
        this.validSizeFile = true;
        this.previewImage = URL.createObjectURL(file);
        this.convertFileToBase64(file);
      } else {
        this.validFile = null;
        this.validSizeFile = null;
        this.previewImage = null;
      }
    },
    isValidImage(file) {
      return file.type === "image/png" || file.type === "image/jpeg";
    },
    isInvalidSizeImage(file) {
      return file.size > 5 * 1024 * 1024;
    },
    convertFileToBase64(file) {
      const reader = new FileReader();

      reader.readAsDataURL(file);

      reader.onload = () => {
        const base64String = reader.result;
        this.area.bannerImage = base64String;
      };
    },
    clearFiles() {
      this.$refs["banner-image"].reset();
      this.validFile = null;
      this.previewImage = null;
      this.area.bannerImage = null;
    },
    closeModal() {
      this.$bvModal.hide("modal-update-area");
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
            /^[a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*$/.test(value)
          ),
        },
      },
    };
  },
});
</script>
