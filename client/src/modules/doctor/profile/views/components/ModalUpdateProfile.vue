<template>
  <div>
    <b-modal hide-footer hide-header centered id="modal-update-profile">
      <header class="text-center border-bottom" style="margin-bottom: 20px">
        <h4 style="font-family: 'Arial', sans-serif; color: #333">
          Actualizar Perfil
        </h4>
      </header>
      <main>
        <b-tabs content-class="mt-3">
          <b-tab title="Información Personal">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-6">
                  <b-form-group>
                    <label for="name" class="detail-label">Nombre:</label>
                    <b-form-input
                      id="name"
                      placeholder="Nombre"
                      type="text"
                      required
                      v-model.trim="v$.doctor.person.name.$model"
                      trim
                      :state="v$.doctor.person.name.$dirty ? !v$.doctor.person.name.$error : null"
                      @blur="v$.doctor.person.name.$touch()"
                    >
                    </b-form-input>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.name.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                  <b-form-group>
                    <label for="surname" class="detail-label">
                      Apellido materno:</label
                    >
                    <b-form-input
                      id="surname"
                      placeholder="Apellido materno"
                      type="text"
                      required
                      v-model.trim="v$.doctor.person.surname.$model"
                      trim
                      :state="
                        v$.doctor.person.surname.$dirty ? !v$.doctor.person.surname.$error : null
                      "
                      @blur="v$.doctor.person.surname.$touch()"
                    >
                    </b-form-input>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.surname.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                  <b-form-group>
                    <label for="lastname" class="detail-label">
                      Apellido paterno:</label
                    >
                    <b-form-input
                      id="lastname"
                      placeholder="Apellido paterno"
                      type="text"
                      required
                      v-model.trim="v$.doctor.person.lastname.$model"
                      trim
                      :state="
                        v$.doctor.person.lastname.$dirty
                          ? !v$.doctor.person.lastname.$error
                          : null
                      "
                      @blur="v$.doctor.person.lastname.$touch()"
                    >
                    </b-form-input>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.lastname.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                </div>
                <div class="col-md-6">
                  <b-form-group>
                    <label for="gender" class="detail-label"> Genero:</label>
                    <multi-select
                      id="gender"
                      :class="{
                        'is-invalid': v$.doctor.person.gender.$error,
                        'is-valid': !v$.doctor.person.gender.$invalid,
                      }"
                      v-model="v$.doctor.person.gender.$model"
                      placeholder="Selecciona un género"
                      label="name"
                      :options="genders"
                      track-by="name"
                      :multiple="false"
                      selectLabel="Presiona enter para seleccionar"
                      deselectLabel="Presiona enter para eliminar"
                      selectedLabel="Seleccionado"
                      @close="v$.doctor.person.gender.$touch()"
                    >
                      <template slot="noResult">No hay resultados</template>
                      <template slot="noOptions"
                        >No hay opciones</template
                      ></multi-select
                    >
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.gender.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                  <b-form-group>
                    <label for="curp" class="detail-label"> CURP:</label>
                    <b-form-input
                      id="curp"
                      placeholder="SIIN012345MHNRDYA7"
                      type="text"
                      required
                      v-model.trim="v$.doctor.person.curp.$model"
                      trim
                      :state="v$.doctor.person.curp.$dirty ? !v$.doctor.person.curp.$error : null"
                      @blur="v$.doctor.person.curp.$touch()"
                    >
                    </b-form-input>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.curp.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                  <b-form-group  >
                    <label for="address" class="detail-label">
                      Dirección:</label
                    >
                    <b-form-input
                      id="address"
                      placeholder="Calle 0 col 3243"
                      type="text"
                      required
                      v-model.trim="v$.doctor.person.details.$model"
                      trim
                      :state="
                        v$.doctor.person.details.$dirty ? !v$.doctor.person.details.$error : null
                      "
                      @blur="v$.doctor.person.details.$touch()"
                    >
                    </b-form-input>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.details.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                </div>
              </div>
            </div>
          </b-tab>
          <b-tab title="Información Adicional">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-6">
                  <b-form-group>
                    <label for="birthdate" class="detail-label">
                      Fecha de Nacimiento:</label
                    >
                    <b-form-datepicker
                      id="birthdate"
                      placeholder="Selecciona una fecha"
                      :label-help="null"
                      v-model="v$.doctor.person.birthday.$model"
                      :state="
                        v$.doctor.person.birthday.$dirty
                          ? !v$.doctor.person.birthday.$error
                          : null
                      "
                      @blur="v$.doctor.person.birthday.$touch()"
                      label-current-month="Fecha máxima"
                      hide-header
                      :date-format-options="{
                        year: 'numeric',
                        month: 'numeric',
                        day: 'numeric',
                      }"
                      :max="maxDate"
                      @hide="v$.doctor.person.birthday.$touch()"
                    ></b-form-datepicker>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.birthday.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>

                  <b-form-group>
                    <label for="phone" class="detail-label"> Teléfono:</label>
                    <b-form-input
                      id="phone"
                      placeholder="111-122-8347"
                      type="tel"
                      required
                      v-model.trim="v$.doctor.person.phoneNumber.$model"
                      trim
                      :state="
                        v$.doctor.person.phoneNumber.$dirty ? !v$.doctor.person.phoneNumber.$error : null
                      "
                      @blur="v$.doctor.person.phoneNumber.$touch()"
                    >
                    </b-form-input>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.phoneNumber.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                </div>
                <div class="col-md-6">
                  <b-form-group>
                    <label for="email" class="detail-label">
                      Correo Electrónico:</label
                    >
                    <b-form-input
                      id="email"
                      placeholder="example@example.com"
                      type="email"
                      required
                      v-model.trim="v$.doctor.person.email.$model"
                      trim
                      :state="
                        v$.doctor.person.email.$dirty ? !v$.doctor.person.email.$error : null
                      "
                      @blur="v$.doctor.person.email.$touch()"
                    >
                    </b-form-input>
                    <b-form-invalid-feedback
                      v-for="error in v$.doctor.person.email.$errors"
                      :key="error.$uid"
                    >
                      {{ error.$message }}
                    </b-form-invalid-feedback>
                  </b-form-group>
                 
                </div>
              </div>
            </div>
          </b-tab>
        </b-tabs>
      </main>
      <footer class="text-center mt-5">
        <div class="btn-group">
          <b-button variant="danger" class="mr-4" @click="onClose"
            >Cerrar</b-button
          >
          <b-button variant="primary" @click="updateProfile">
            Guardar
          </b-button>
        </div>
      </footer>
    </b-modal>
  </div>
</template>

<script>
import {
  required,
  email,
  helpers,
  minLength,
  maxLength,
} from "@vuelidate/validators";
import { useVuelidate } from "@vuelidate/core";
import SweetAlertCustom from "../../../../../kernel/SweetAlertCustom";
import boundary from "../../boundary"
import moment from "moment/moment";
import Vue from "vue";
export default Vue.extend({
  name: "ModalUpdateProfile",
  props: {
    doctors: {
      type: Object,
      required: true,
    },
  },
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    const maxDate = new Date(today);
    maxDate.setFullYear(maxDate.getFullYear()-18);
    return {
      maxDate: maxDate,
      genders: [
        { name: "Masculino", id: 1 },
        { name: "Femenino", id: 2 },
      ],
      doctor: {
        person: {
        id: this.doctors.person.id,
        name: this.doctors.person.name,
        lastname: this.doctors.person.lastname,
        surname: this.doctors.person.surname,
        curp: this.doctors.person.curp,
        gender: {
          id: this.doctors.person.gender.id
        },
        phoneNumber: this.doctors.person.phoneNumber,
        details: this.doctors.person.details,
        email: this.doctors.person.email,
        birthday: this.doctors.person.birthday,
      }
      },
      errorMessagges: {
        required: "Campo obligatorio",
        invalidEmail: "Correo inválido",
        invalidGender: "Selecciona un género",
        invalidBirthday: "La fecha debe ser al menos hace un mes anterior a este",
      },
    };
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-update-profile");
    },
    async updateProfile() {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          const resp = await boundary.personController.update(this.doctor.person);
          const { error } = resp;
          if (!error) {
            this.$emit("reloadProfile");
            setTimeout(() => {
              SweetAlertCustom.successMessage();
            }, 1000);
            this.$nextTick(() => this.$bvModal.hide("modal-update-profile"));
            return;
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
  validations() {
    return {
      doctor: {
        person:{
        name: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          valid: helpers.withMessage(
            "Campo inválido, solo se aceptan letras y puntos",
            helpers.regex(/^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\. \s]+$/)
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
          maxLength: helpers.withMessage("Máximo 50 caracteres", maxLength(50)),
        },
        lastname: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          valid: helpers.withMessage(
            "Campo inválido, solo se aceptan letras y puntos",
            helpers.regex(/^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\. \s]+$/)
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
          maxLength: helpers.withMessage("Máximo 50 caracteres", maxLength(50)),
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
          required: helpers.withMessage(this.errorMessagges.required, required),
          valid: helpers.withMessage(
            "Curp inválido",
            helpers.regex(/^[A-Z]{4}[0-9]{6}[HM][A-Z]{6}[0-9]{1}$/)
          ),
        },
        details: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          valid: helpers.withMessage(
            "Formato invalido, debe ser Calle Ejemplo #123, Colonia Centro, Ciudad de México",
            helpers.regex(/^[a-zA-Z0-9\s\#áéíóúÁÉÍÓÚ.,-]{1,100}$/)
          ),
        },
        birthday: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          maxValue: helpers.withMessage(
            this.errorMessagges.invalidBirthday,
            (value) => {
              return moment(value).isSameOrBefore(
                new Date(
                  new Date().getFullYear()-18,
                  new Date().getMonth(),
                  new Date().getDate()
                )
              );
            }
          ),
        },
        gender: {
          required: helpers.withMessage(
            this.errorMessagges.invalidGender,
            required
          ),
        },
        
        phoneNumber: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          validFormat: helpers.withMessage(
            "Teléfono inválido",
            helpers.regex(/(?:\d{1}\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})/)
          ),
        },
        email: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          email: helpers.withMessage(this.errorMessagges.invalidEmail, email),
        },
        }
      },
    };
  },
});
</script>

<style>
.is-invalid > .multiselect__tags {
  border-color: #dc3545;
}
.detail-label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #666;
}
.mt-custom {
  margin-top: 39px;
}
.is-valid > .multiselect__tags {
  border-color: #28a745;
}

</style>
