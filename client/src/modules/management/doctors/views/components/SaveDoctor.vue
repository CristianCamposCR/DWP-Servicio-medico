<template>
  <b-modal
    id="modal-save-doctor"
    hide-footer
    scrollable
    :no-close-on-backdrop="true"
    centered
    @hidden="onClose"
    @close="onClose"
    title="Registrar Doctor"
    size="lg"
  >
    <b-form fluid>
      <b-tabs content-class="mt-3">
        <b-tab title="Información Personal">
          <b-row>
            <b-col cols="12" sm="6">
              <b-form-group label="Nombre:">
                <b-form-input
                  id="name"
                  placeholder="Nombre"
                  type="text"
                  required
                  v-model.trim="v$.doctor.name.$model"
                  trim
                  :state="v$.doctor.name.$dirty ? !v$.doctor.name.$error : null"
                  @blur="v$.doctor.name.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.name.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
            <b-col cols="12" sm="6">
              <b-form-group label="Apellido paterno:">
                <b-form-input
                  id="lastname"
                  placeholder="Apellido paterno"
                  type="text"
                  required
                  v-model.trim="v$.doctor.lastname.$model"
                  trim
                  :state="
                    v$.doctor.lastname.$dirty
                      ? !v$.doctor.lastname.$error
                      : null
                  "
                  @blur="v$.doctor.lastname.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.lastname.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col cols="12" sm="6">
              <b-form-group label="Apellido materno:">
                <b-form-input
                  id="surname"
                  placeholder="Apellido materno"
                  type="text"
                  required
                  v-model.trim="v$.doctor.surname.$model"
                  trim
                  :state="
                    v$.doctor.surname.$dirty ? !v$.doctor.surname.$error : null
                  "
                  @blur="v$.doctor.surname.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.surname.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>

            <b-col cols="12" sm="6">
              <b-form-group label="Correo Electrónico">
                <b-form-input
                  id="email"
                  placeholder="example@example.com"
                  type="email"
                  required
                  v-model.trim="v$.doctor.email.$model"
                  trim
                  :state="
                    v$.doctor.email.$dirty ? !v$.doctor.email.$error : null
                  "
                  @blur="v$.doctor.email.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.email.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col cols="12" sm="6">
              <b-form-group label="Genero:">
                <multi-select
                  id="gender"
                  :class="{
                    'is-invalid': v$.doctor.gender.$error,
                    'is-valid': !v$.doctor.gender.$invalid,
                  }"
                  v-model="v$.doctor.gender.$model"
                  placeholder="Selecciona un género"
                  label="name"
                  :options="gendersOptions"
                  track-by="name"
                  :multiple="false"
                  selectLabel="Presiona enter para seleccionar"
                  deselectLabel="Presiona enter para eliminar"
                  selectedLabel="Seleccionado"
                  @close="v$.doctor.gender.$touch()"
                >
                  <template slot="noResult">No hay resultados</template>
                  <template slot="noOptions"
                    >No hay opciones</template
                  ></multi-select
                >
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.gender.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
            <b-col cols="12" sm="6">
              <b-form-group label="Dirección">
                <b-form-input
                  id="address"
                  placeholder="Calle 0 col 3243"
                  type="text"
                  required
                  v-model.trim="v$.doctor.details.$model"
                  trim
                  :state="
                    v$.doctor.details.$dirty ? !v$.doctor.details.$error : null
                  "
                  @blur="v$.doctor.details.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.details.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>
          <b-row>
            <b-col cols="12" sm="6">
              <b-form-group label="Número de Teléfono">
                <b-form-input
                  id="phone"
                  placeholder="111-122-8347"
                  type="tel"
                  required
                  v-model.trim="v$.doctor.phoneNumber.$model"
                  trim
                  :state="
                    v$.doctor.phoneNumber.$dirty
                      ? !v$.doctor.phoneNumber.$error
                      : null
                  "
                  @blur="v$.doctor.phoneNumber.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.phoneNumber.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
            <b-col cols="12" sm="6">
              <b-form-group label="Fecha de nacimiento">
                <b-form-datepicker
                id="birthday"
                class="mb-2"
                placeholder="Selecciona una fecha"
                :label-help="null"
                v-model="v$.doctor.birthday.$model"
                :state="
                  v$.doctor.birthday.$dirty ? !v$.doctor.birthday.$error : null
                "
                @blur="v$.doctor.birthday.$touch()"
                label-current-month="Fecha máxima"
                hide-header
                :date-format-options="{
                  year: 'numeric',
                  month: 'numeric',
                  day: 'numeric',
                }"
                :max="maxDate"
                @hide="v$.doctor.birthday.$touch()"
              ></b-form-datepicker>
              <b-form-invalid-feedback
                v-for="error in v$.doctor.birthday.$errors"
                :key="error.$uid"
              >
                {{ error.$message }}
              </b-form-invalid-feedback>
            </b-form-group>
            </b-col>
          </b-row>
        </b-tab>
        <b-tab title="Información Profesional">
          <b-row>
            <b-col cols="12" sm="6">
              <b-form-group label="Experiencia:">
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
              <b-form-group label="Dias disponibles:">
                <multi-select
                  id="availableDays"
                  :class="{
                    'is-invalid': v$.doctor.availableDays.$error,
                    'is-valid': !v$.doctor.availableDays.$invalid,
                  }"
                  v-model="v$.doctor.availableDays.$model"
                  placeholder="Selecciona de 1 a 5 días"
                  :options="availableDaysOptions"
                  :multiple="true"
                  selectLabel="Presiona enter para seleccionar"
                  deselectLabel="Presiona enter para eliminar"
                  selectedLabel="Seleccionado"
                  @close="v$.doctor.availableDays.$touch()"
                >
                  <template slot="noResult">No hay resultados</template>
                  <template slot="noOptions">No hay opciones</template>
                </multi-select>

                <b-form-invalid-feedback
                  v-for="error in v$.doctor.availableDays.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>

          <b-row>
            <b-col cols="12" sm="6">
              <b-form-group label="Selecciona una turno:">
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
              <b-form-group label="¿Es auxiliar?">
                <b-form-checkbox
                  v-for="opc in isAuxOptions"
                  :key="opc.id"
                  name="isAux"
                  v-model="doctor.isAux"
                  :state="
                    v$.doctor.isAux.$dirty ? !v$.doctor.isAux.$error : null
                  "
                  :value="opc.value"
                  inline
                >
                  {{ opc.text }}
                </b-form-checkbox>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.isAux.$errors"
                  :key="error.$uid"
                  :state="
                    v$.doctor.isAux.$dirty ? !v$.doctor.isAux.$error : null
                  "
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
            <b-col cols="12" sm="6">
              <b-form-group label="Selecciona una especialidad:">
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
            <b-col cols="12" sm="6">
              <b-form-group label="Cedula profesional:">
                <b-form-input
                  id="professionalId"
                  placeholder="cedula profesional"
                  type="text"
                  required
                  v-model="v$.doctor.professionalId.$model"
                  trim
                  :state="
                    v$.doctor.professionalId.$dirty
                      ? !v$.doctor.professionalId.$error
                      : null
                  "
                  @blur="v$.doctor.professionalId.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.professionalId.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
          </b-row>
        </b-tab>
        <b-tab title="Información de cuenta">
          <b-row>
            <b-col cols="12" sm="6">
              <b-form-group label="Nombre de usuario:">
                <b-form-input
                  id="username"
                  placeholder="doctorMax"
                  type="text"
                  required
                  v-model="v$.doctor.username.$model"
                  trim
                  :state="
                    v$.doctor.username.$dirty
                      ? !v$.doctor.username.$error
                      : null
                  "
                  @blur="v$.doctor.username.$touch()"
                >
                </b-form-input>
                <b-form-invalid-feedback
                  v-for="error in v$.doctor.username.$errors"
                  :key="error.$uid"
                >
                  {{ error.$message }}
                </b-form-invalid-feedback>
              </b-form-group>
            </b-col>
            <b-col cols="12" sm="6">
              <b-form-group label="Contraseña">
                <b-input-group>
                  <b-form-input
                    id="password"
                    :type="showPasswordState ? 'text' : 'password'"
                    placeholder="Contraseña"
                    style="border-right: none !important"
                    required
                    v-model.trim="v$.doctor.password.$model"
                    trim
                    :state="
                      v$.doctor.password.$dirty
                        ? !v$.doctor.password.$error
                        : null
                    "
                    @blur="v$.doctor.password.$touch()"
                  >
                  </b-form-input>
                  <b-input-group-prepend>
                    <span
                      class="input-group-text"
                      style="
                        background-color: white;
                        border-left: none !important;
                      "
                      @click="showPassword"
                      role="button"
                    >
                      <b-icon
                        :icon="showPasswordState ? 'eye-slash' : 'eye'"
                      ></b-icon>
                    </span>
                  </b-input-group-prepend>
                  <b-form-invalid-feedback
                    v-for="error in v$.doctor.password.$errors"
                    :key="error.$uid"
                  >
                    {{ error.$message }}
                  </b-form-invalid-feedback>
                </b-input-group>
              </b-form-group>
            </b-col>
            <b-col cols="12" sm="6">
              <b-form-group label="Confirma contraseña">
                <b-input-group>
                  <b-form-input
                    id="confirmPassword"
                    :type="showConfirmPasswordState ? 'text' : 'password'"
                    placeholder="Confirmar contraseña"
                    style="border-right: none !important"
                  >
                  </b-form-input>
                  <b-input-group-prepend>
                    <span
                      class="input-group-text"
                      style="
                        background-color: white;
                        border-left: none !important;
                      "
                      @click="showConfirmPassword"
                      role="button"
                    >
                      <b-icon
                        :icon="showConfirmPasswordState ? 'eye-slash' : 'eye'"
                      ></b-icon>
                    </span>
                  </b-input-group-prepend>
                </b-input-group>
              </b-form-group>
            </b-col>
          </b-row>
        </b-tab>
        <div class="col-12 mt-4 px-5 d-flex justify-content-between">
          <b-button variant="danger" @click="onClose">Cancelar</b-button>
          <b-button
            variant="success"
            class="ml-2"
            :disabled="v$.doctor.$invalid"
            @click="saveDoctor"
          >
            Registrar
          </b-button>
        </div>
      </b-tabs>
    </b-form>
  </b-modal>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import SweetAlertCustom from "../../../../../kernel/SweetAlertCustom";
import boundary from "../../boundary";
import {
  required,
  helpers,
  minLength,
  maxLength,
  email,
} from "@vuelidate/validators";
import doctorController from "../../services/controller/doctor.controller";
import { encrypt } from "../../../../../kernel/hashFunctions";
import moment from "moment/moment";
export default Vue.extend({
  name: "SaveDoctor",
  setup() {
    return { v$: useVuelidate() };
  },
  data() {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const maxDate = new Date(today);
    maxDate.setFullYear(maxDate.getFullYear() - 20);

    return {
      maxDate: maxDate,
      showPasswordState: false,
      showConfirmPasswordState: false,
      confirmPassword: "",
      doctor: {
        professionalId: "",
        experience: "",
        isAux: null,
        availableDays: "",
        shift: null,
        speciality: null,
        name: "",
        surname: "",
        lastname: "",
        email: "",
        birthday: null,
        gender: null,
        phoneNumber: "",
        details: "",
        username: "",
        password: "",
      },
      gendersOptions: [],
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
        "Lunes",
        "Martes",
        "Miércoles",
        "Jueves",
        "Viernes",
      ],
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
        invalidIsAux: "selecciona si es auxiliar",
        invalidSpeciality: "selecciona una especialidad",
        invalidEmail: "Correo inválido",
        invalidGender: "Selecciona un género",
        invalidBirthday: "La edad minima son 20 años",
      },
    };
  },
  methods: {
    async saveDoctor() {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          const password = await encrypt(this.doctor.password);
          this.doctor.password = password;
          const professional = await encrypt(this.doctor.professionalId);
          this.doctor.professionalId = professional;
          const resp = await doctorController.saveDoctor(this.doctor);
          const { error } = resp;
          if (!error) {
            this.$emit("reloadRegisters");
            setTimeout(() => {
              SweetAlertCustom.successMessage();
            }, 1000);
            this.$nextTick(() => this.$bvModal.hide("save-doctor"));
            return;
          }
        }
      } catch (error) {
        console.log(error);
      }
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
    async getAllGender() {
      try {
        const genders = await boundary.genderController.getAllGender();
        this.gendersOptions = genders.map((gender) => ({
          id: gender.id,
          name: gender.name,
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
      this.doctor.professionalId = null;
      this.doctor.experience = null;
      this.doctor.isAux = null;
      this.doctor.availableDays = null;
      this.doctor.shift = null;
      this.doctor.speciality = null;
      this.doctor.name = null;
      this.doctor.surname = null;
      this.doctor.lastname = null;
      this.doctor.email = null;
      this.doctor.birthday = null;
      this.doctor.gender = null;
      this.doctor.phoneNumber = null;
      this.doctor.details = null;
      this.doctor.username = null;
      this.doctor.password = null;
      this.v$.doctor.$reset();
      this.$bvModal.hide("modal-save-doctor");
    },
    showPassword() {
      this.showPasswordState = !this.showPasswordState;
    },
    showConfirmPassword() {
      this.showConfirmPasswordState = !this.showConfirmPasswordState;
    },
  },
  mounted() {
    this.getAllSpecialities();
    this.getAllShift();
    this.getAllGender();
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
        password: {
          required: helpers.withMessage(this.errorMessages.required, required),
          valid: helpers.withMessage(
            "Formato incorrecto, la contraseñá Debe tener al menos 8 caracteres de longitud, debe contener al menos una letra mayúscula (A-Z), debe contener al menos una letra minúscula (a-z), debe contener al menos un número (0-9), debe contener al menos un caracter especial",
            helpers.regex(
              /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\-_=+\\\|\[\]{};:'",.<>/?]).{8,}$/
            )
          ),
          minLength: helpers.withMessage("Mínimo 8 caracteres", minLength(8)),
        },
        name: {
          required: helpers.withMessage(this.errorMessages.required, required),
          valid: helpers.withMessage(
            "Campo inválido, solo se aceptan letras y puntos",
            helpers.regex(/^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\. \s]+$/)
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
          maxLength: helpers.withMessage("Máximo 50 caracteres", maxLength(50)),
        },
        username: {
          required: helpers.withMessage(this.errorMessages.required, required),
          valid: helpers.withMessage(
            "Campo inválido, solo se aceptan letras y puntos",
            helpers.regex(/^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\. \s]+$/)
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
          maxLength: helpers.withMessage("Máximo 50 caracteres", maxLength(50)),
        },
        lastname: {
          required: helpers.withMessage(this.errorMessages.required, required),
          valid: helpers.withMessage(
            "Campo inválido, solo se aceptan letras y puntos",
            helpers.regex(/^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\. \s]+$/)
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
          maxLength: helpers.withMessage("Máximo 50 caracteres", maxLength(50)),
        },
        professionalId: {
          required: helpers.withMessage(this.errorMessages.required, required),
          valid: helpers.withMessage(
            "Campo inválido, solo se aceptan letras y puntos",
            helpers.regex(/^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\. \s]+$/)
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
          maxLength: helpers.withMessage("Máximo 20 caracteres", maxLength(20)),
        },
        surname: {
          valid: helpers.withMessage(
            "Campo inválido, solo se aceptan letras y puntos",
            (value) => {
              if (!value) return true;
              return /^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\. \s]+$/.test(value);
            }
          ),
        },
        curp: {
          required: helpers.withMessage(this.errorMessages.required, required),
          valid: helpers.withMessage(
            "Curp inválido",
            helpers.regex(/^[A-Z]{4}[0-9]{6}[HM][A-Z]{6}[0-9]{1}$/)
          ),
        },
        details: {
          required: helpers.withMessage(this.errorMessages.required, required),
          valid: helpers.withMessage(
            "Formato invalido, debe ser Calle Ejemplo #123, Colonia Centro, Ciudad de México",
            helpers.regex(/^[a-zA-Z0-9\s\#áéíóúÁÉÍÓÚ.,-]{1,100}$/)
          ),
        },
        birthday: {
          required: helpers.withMessage(this.errorMessages.required, required),
          maxValue: helpers.withMessage(
            this.errorMessages.invalidBirthday,
            (value) => {
              return moment(value).isSameOrBefore(
                new Date(
                  new Date().getFullYear() - 20,
                  new Date().getMonth(),
                  new Date().getDate()
                )
              );
            }
          ),
        },
        gender: {
          required: helpers.withMessage(this.errorMessages.required, required),
        },

        phoneNumber: {
          required: helpers.withMessage(this.errorMessages.required, required),
          validFormat: helpers.withMessage(
            "Teléfono inválido",
            helpers.regex(/(?:\d{1}\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})/)
          ),
        },
        email: {
          required: helpers.withMessage(this.errorMessages.required, required),
          email: helpers.withMessage(this.errorMessages.invalidEmail, email),
        },
      },
    };
  },
});
</script>

<style scoped>
</style>
