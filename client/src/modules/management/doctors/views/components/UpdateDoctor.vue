<template>
  <b-modal
    id="update-doctor"
    centered
    hide-footer
    scrollable
    :no-close-on-backdrop="true"
    title="Actualizar Doctor"
  >
    <b-form fluid>
      <b-row>
        <b-col cols="12" sm="6">
          <b-form-group>
            <label for="experience" class="detail-label"> Años de experiencia:</label>
            <b-form-input
              id="experience"
              placeholder="Años de experiencia"
              type="number"
              required
              v-model="v$.doctor.experience.$model"
              trim
              :state="
                v$.doctor.experience.$dirty
                  ? !v$.doctor.experience.$error
                  : null
              "
              @blur="v$.doctor.experience.$touch()"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-for="error in v$.doctor.experience.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>
        <b-col cols="12" sm="6">
          <b-form-group>
            <label for="speciality" class="detail-label">
              Selecciona una especialidad:</label
            >
            <multi-select
              id="speciality"
              :class="{
                'is-invalid': v$.doctor.speciality.$error,
                'is-valid': !v$.doctor.speciality.$invalid,
              }"
              v-model="v$.doctor.speciality.$model"
              placeholder="Selecciona una especialidad"
              label="name"
              :options="specialitiesOptions"
              track-by="name"
              :multiple="false"
              selectLabel="Presiona enter para seleccionar"
              deselectLabel="Presiona enter para eliminar"
              selectedLabel="Seleccionado"
              @close="v$.doctor.speciality.$touch()"
            >
              <template slot="noResult">No hay resultados</template>
              <template slot="noOptions"
                >No hay opciones</template
              ></multi-select
            >
            <b-form-invalid-feedback
              v-for="error in v$.doctor.speciality.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>
      </b-row>

      <b-row>
        <b-col cols="12" sm="6">
          <b-form-group>
            <label for="shift" class="detail-label">
              Selecciona un turno:</label
            >
            <multi-select
              id="shift"
              :class="{
                'is-invalid': v$.doctor.shift.$error,
                'is-valid': !v$.doctor.shift.$invalid,
              }"
              v-model="v$.doctor.shift.$model"
              placeholder="Selecciona un turno"
              label="name"
              :options="shiftOptions"
              track-by="name"
              :multiple="false"
              selectLabel="Presiona enter para seleccionar"
              deselectLabel="Presiona enter para eliminar"
              selectedLabel="Seleccionado"
              @close="v$.doctor.shift.$touch()"
            >
              <template slot="noResult">No hay resultados</template>
              <template slot="noOptions"
                >No hay opciones</template
              ></multi-select
            >
            <b-form-invalid-feedback
              v-for="error in v$.doctor.shift.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>
        <b-col cols="12" sm="6">
          <label>¿Es auxiliar?</label>
          <b-form-group>
            <b-form-checkbox
              v-for="opc in isAuxOptions"
              :key="opc.id"
              name="isAux"
              v-model="doctor.isAux"
              :state="v$.doctor.isAux.$dirty ? !v$.doctor.isAux.$error : null"
              :value="opc.value"
              inline
            >
              {{ opc.text }}
            </b-form-checkbox>
            <b-form-invalid-feedback
              v-for="error in v$.doctor.isAux.$errors"
              :key="error.$uid"
              :state="v$.doctor.isAux.$dirty ? !v$.doctor.isAux.$error : null"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" sm="12">
          <b-form-group label="Días disponibles:">
                <multi-select
                id="availableDays"
                :class="{
                  'is-invalid': v$.doctor.availableDays.$error,
                  'is-valid': !v$.doctor.availableDays.$invalid,
                }"
                v-model="v$.doctor.availableDays.$model"
                placeholder="Selecciona por lo menos un dia"
                label="name"
                :options="availableDaysOptions"
                track-by="name"
                :multiple="true"
                selectLabel="Presiona enter para seleccionar"
                deselectLabel="Presiona enter para eliminar"
                selectedLabel="Seleccionado"
                @close="v$.doctor.availableDays.$touch()"
                ><template slot="noResult">No hay resultados</template>
                <template slot="noOptions"
                  >No hay opciones</template
                ></multi-select
              >
              <b-form-invalid-feedback
                v-for="error in v$.doctor.availableDays.$errors"
                :key="error.$uid"
              >
                {{ error.$message }}
              </b-form-invalid-feedback>
            </b-form-group>
            <b-col cols="12" sm="6">
              <b-form-group label="Foto de perfil:">
                <b-input-group>
                  <b-form-file
                    @change="handleFileChange"
                    browse-text="Buscar"
                    placeholder="Selecciona una imagen"
                    drop-placeholder="Suelta el archivo aquí..."
                    accept="image/png, image/jpeg"
                    :state="validFile && validSizeFile"
                    ref="profile-photo"
                  ></b-form-file>
                  <b-form-invalid-feedback v-if="validFile == false">{{
                    errorMessages.validFile
                  }}</b-form-invalid-feedback>
                  <b-form-invalid-feedback v-else-if="validSizeFile == false">{{
                    errorMessages.validSizeFile
                  }}</b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </b-col>
        </b-col>
      </b-row>
      <div class="col-12 mt-4 px-5 d-flex justify-content-between">
        <b-button variant="danger" @click="onClose">Cancelar</b-button>
        <b-button variant="success" class="ml-2" @click="updateDoctor">
          Actualizar
        </b-button>
      </div>
    </b-form>
  </b-modal>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength } from "@vuelidate/validators";
import SweetAlertCustom from "../../../../../kernel/SweetAlertCustom";
import doctorController from "../../services/controller/doctor.controller";
import boundary from "../../boundary";

export default Vue.extend({
  name: "UpdateDoctor",
  setup() {
    return { v$: useVuelidate() };
  },
  props: {
    doctors: {
      required: true,
      type: Object,
    },
  },
  data() {
    return {
      doctor: {},
      shiftOptions: [],
      isAuxOptions: [
        {
          text: "SI",
          value: true,
        },
        {
          text: "NO",
          value: false,
        },
      ],
      availableDaysOptions: [
        { name: "Lunes", id: "MONDAY" },
        { name: "Martes", id: "TUESDAY" },
        { name: "Miercoles", id: "WEDNESDAY" },
        { name: "Jueves", id: "THURSDAY" },
        { name: "Viernes", id: "FRIDAY" },
      ],
      validFile: null,
      validSizeFile: null,
      specialitiesOptions: [],
      errorMessages: {
        required: "Campo obligatorio",
        experience: {
          minLength: "Mínimo 1 digito",
          noneScripts: "Campo inválido no se aceptan scripts",
          valid: "Campos inválido - caracteres inválidos",
        },
        invalidAvailableDays: "selecciona un dia",
        invalidShift: "selecciona un turno",
        validFile: "El archivo seleccionado no es una imagen PNG o JPEG.",
        validSizeFile: "La imagen supera los 5 mb permitidos",
        invalidIsAux: "selecciona si es auxiliar",
        invalidSpeciality: "selecciona una especialidad",
      },
    };
  },
  watch: {
    doctors() {
      this.doctor = { ...this.doctors };
    },
  },
  methods: {
    async updateDoctor() {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          const englishDaysString = '[' + this.doctor.availableDays.map(day => day.id).join(', ') + ']';
          const resp = await doctorController.update({...this.doctor, availableDays: englishDaysString});
          const { error } = resp;
          if (!error) {
            this.$emit("reloadUpdateDoctor");
            setTimeout(() => {
              SweetAlertCustom.successMessage();
            }, 100);
            this.$nextTick(() => this.$bvModal.hide("update-doctor"));
            return;
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    handleFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        if (!this.isValidImage(file)) {
          this.validFile = false;
          event.target.value = null;
          this.doctor.profilePhoto = null;
          return;
        }
        if (this.isInvalidSizeImage(file)) {
          this.validFile = true;
          this.validSizeFile = false;
          event.target.value = null;
          this.doctor.profilePhoto = null;
          return;
        }
        this.validFile = true;
        this.validSizeFile = true;
        this.convertFileToBase64(file);
      } else {
        this.validFile = null;
        this.validSizeFile = null;
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
        this.doctor.profilePhoto = base64String;
      };
    },
    async getAllShift() {
      try {
        const shifts = await boundary.shiftController.getAllShift();
        this.shiftOptions = shifts.map((shift) => ({
          id: shift.id,
          name: shift.name,
        }));
      } catch (error) {
        console.error("Error al obtener los turnos:", error);
      }
    },
    async getAllSpecialities() {
      try {
        const specialities =
          await boundary.specialityController.getSpecialties();
        this.specialitiesOptions = specialities.map((speciality) => ({
          id: speciality.id,
          name: speciality.name,
        }));
      } catch (error) {
        console.error("Error al obtener las especialidades:", error);
      }
    },
    onClose() {
      this.$bvModal.hide("update-doctor");
    },
  },
  mounted() {
    this.getAllSpecialities();
    this.getAllShift();
  },
  validations() {
    return {
      doctor: {
        experience: {
          required: helpers.withMessage(this.errorMessages.required, required),
          minLength: helpers.withMessage(
            this.errorMessages.experience.minLength,
            minLength(1)
          ),
          notScript: helpers.withMessage(
            this.errorMessages.experience.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(
            this.errorMessages.experience.valid,
            (value) =>
              /^\d+(\.\d{1,2})?$/.test(value) &&
              !isNaN(parseFloat(value)) &&
              parseFloat(value) > 0
          ),
        },
        isAux: {
          required: helpers.withMessage(
            this.errorMessages.invalidIsAux,
            required
          ),
        },
        availableDays: {
          required: helpers.withMessage(
            this.errorMessages.invalidAvailableDays,
            required
          ),
        },
        shift: {
          required: helpers.withMessage(
            this.errorMessages.invalidShift,
            required
          ),
        },
        speciality: {
          required: helpers.withMessage(
            this.errorMessages.invalidSpeciality,
            required
          ),
        },
      },
    };
  },
});
</script>
<style>
.is-invalid > .multiselect__tags {
  border-color: #dc3545;
}
.is-valid > .multiselect__tags {
  border-color: #28a745;
}
</style>
