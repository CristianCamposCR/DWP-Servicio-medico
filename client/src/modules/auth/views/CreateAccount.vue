<template>
  <div>
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
    <div v-if="step === 0">
      <b-row class="mx-4 mt-3">
        <b-col cols="12" sm="12" md="6">
          <b-form-group>
            <b-form-input
              id="name"
              placeholder="Nombre"
              type="text"
              required
              v-model.trim="v$.newAccount.name.$model"
              trim
              :state="
                v$.newAccount.name.$dirty ? !v$.newAccount.name.$error : null
              "
              @blur="v$.newAccount.name.$touch()"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-if="!v$.newAccount.name.required.$response"
              >{{ errorMessagges.required }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.name.valid.$response"
              >{{ errorMessagges.valid }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.name.notScript.$response"
              >{{ errorMessagges.noneScripts }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.name.minLength.$response"
              >{{ errorMessagges.minLength }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.name.maxLength.$response"
              >{{ errorMessagges.maxLength }}</b-form-invalid-feedback
            >
          </b-form-group>
        </b-col>
        <b-col cols="12" sm="12" md="6">
          <b-form-group>
            <b-form-input
              id="surname"
              placeholder="Primer apellido"
              type="text"
              required
              v-model.trim="v$.newAccount.surname.$model"
              trim
              :state="
                v$.newAccount.surname.$dirty
                  ? !v$.newAccount.surname.$error
                  : null
              "
              @blur="v$.newAccount.surname.$touch()"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-if="!v$.newAccount.surname.required.$response"
              >{{ errorMessagges.required }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.surname.valid.$response"
              >{{ errorMessagges.valid }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.surname.notScript.$response"
              >{{ errorMessagges.noneScripts }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.surname.minLength.$response"
              >{{ errorMessagges.minLength }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.surname.maxLength.$response"
              >{{ errorMessagges.maxLength }}</b-form-invalid-feedback
            >
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mx-4">
        <b-col cols="12" sm="12" md="6">
          <b-form-group>
            <b-form-input
              id="lastname"
              placeholder="Segundo apellido"
              type="text"
              required
              v-model.trim="v$.newAccount.lastname.$model"
              trim
              :state="
                v$.newAccount.lastname.$dirty
                  ? !v$.newAccount.lastname.$error
                  : null
              "
              @blur="v$.newAccount.lastname.$touch()"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-if="!v$.newAccount.lastname.valid.$response"
              >{{ errorMessagges.valid }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.lastname.notScript.$response"
              >{{ errorMessagges.noneScripts }}</b-form-invalid-feedback
            >
          </b-form-group>
        </b-col>
        <b-col cols="12" sm="12" md="6">
          <b-form-group>
            <b-form-input
              id="phone"
              placeholder="Teléfono"
              type="text"
              required
              @keypress="onlynumbers"
              v-model="v$.newAccount.phoneNumber.$model"
              :state="
                v$.newAccount.phoneNumber.$dirty
                  ? !v$.newAccount.phoneNumber.$error
                  : null
              "
              @blur="v$.newAccount.phoneNumber.$touch()"
              maxlength="10"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-for="error in v$.newAccount.phoneNumber.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mx-4">
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-form-input
              id="email"
              placeholder="Nombre de usuario"
              type="text"
              required
              v-model.trim="v$.newAccount.username.$model"
              trim
              :state="
                v$.newAccount.username.$dirty
                  ? !v$.newAccount.username.$error
                  : null
              "
              @blur="v$.newAccount.username.$touch()"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-if="!v$.newAccount.username.required.$response"
              >{{ errorMessagges.required }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.username.valid.$response"
              >{{ errorMessagges.username.valid }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.username.notScript.$response"
              >{{ errorMessagges.noneScripts }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.newAccount.username.minLength.$response"
              >{{ errorMessagges.username.minLength }}</b-form-invalid-feedback
            >
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mx-4">
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-input-group>
              <b-form-input
                id="password"
                :type="showPasswordState ? 'text' : 'password'"
                placeholder="Contraseña"
                style="border-right: none !important"
                required
                v-model.trim="v$.newAccount.password.$model"
                trim
                :state="
                  v$.newAccount.password.$dirty
                    ? !v$.newAccount.password.$error
                    : null
                "
                @blur="v$.newAccount.password.$touch()"
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
                v-if="!v$.newAccount.password.required.$response"
                >{{ errorMessagges.required }}</b-form-invalid-feedback
              >
              <b-form-invalid-feedback
                v-else-if="!v$.newAccount.password.valid.$response"
                >{{ errorMessagges.password.valid }}</b-form-invalid-feedback
              >
            </b-input-group>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mx-4">
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-input-group>
              <b-form-input
                id="confirmPassword"
                :type="showConfirmPasswordState ? 'text' : 'password'"
                placeholder="Confirmar contraseña"
                style="border-right: none !important"
                v-model="v$.confirmPassword.$model"
                required
                :state="
                  v$.confirmPassword.$dirty ? !v$.confirmPassword.$error : null
                "
                @blur="v$.confirmPassword.$touch()"
              >
              </b-form-input>
              <b-input-group-prepend>
                <span
                  class="input-group-text"
                  style="background-color: white; border-left: none !important"
                  @click="showConfirmPassword"
                  role="button"
                >
                  <b-icon
                    :icon="showConfirmPasswordState ? 'eye-slash' : 'eye'"
                  ></b-icon>
                </span>
              </b-input-group-prepend>
              <b-form-invalid-feedback
                v-if="!v$.confirmPassword.required.$response"
              >
                {{ errorMessagges.required }}
              </b-form-invalid-feedback>
              <b-form-invalid-feedback
                v-else-if="!v$.confirmPassword.sameAsPassword.$response"
              >
                {{ errorMessagges.passwordMissmatch }}
              </b-form-invalid-feedback>
            </b-input-group>
          </b-form-group>
        </b-col>
      </b-row>
    </div>

    <div v-if="step !== 0">
      <b-row class="mx-4 mt-3">
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-form-datepicker
              id="example-datepicker"
              placeholder="Fecha de nacimiento"
              v-model="v$.personalNewAccount.birthday.$model"
              hide-header
              label-no-date-selected="Selecciona tu fecha de nacimiento"
              label-help=""
              :state="
                v$.personalNewAccount.birthday.$dirty
                  ? !v$.personalNewAccount.birthday.$error
                  : null
              "
              @hidden="v$.personalNewAccount.birthday.$touch()"
              :max="todayDate()"
            ></b-form-datepicker>
            <b-form-invalid-feedback
              v-if="!v$.personalNewAccount.birthday.required.$response"
              >{{ errorMessagges.required }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-else-if="!v$.personalNewAccount.birthday.maxValue.$response"
              >{{ errorMessagges.maxValue }}</b-form-invalid-feedback
            >
          </b-form-group>
        </b-col>
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-form-select
              v-model="v$.personalNewAccount.gender.$model"
              :options="genreOptions"
              :state="
                v$.personalNewAccount.gender.$dirty
                  ? !v$.personalNewAccount.gender.$error
                  : null
              "
              @touch="v$.personalNewAccount.gender.$touch()"
            ></b-form-select>
            <b-form-invalid-feedback
              v-if="!v$.personalNewAccount.gender.required.$response"
              >{{ errorMessagges.required }}</b-form-invalid-feedback
            >
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mx-4">
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-form-input
              id="curp"
              placeholder="CURP"
              type="text"
              required
              v-model.trim="v$.personalNewAccount.curp.$model"
              trim
              :state="
                v$.personalNewAccount.curp.$dirty
                  ? !v$.personalNewAccount.curp.$error
                  : null
              "
              @blur="v$.personalNewAccount.curp.$touch()"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-if="!v$.personalNewAccount.curp.required.$response"
              >{{ errorMessagges.required }}</b-form-invalid-feedback
            >
            <b-form-invalid-feedback
              v-if="!v$.personalNewAccount.curp.valid.$response"
              >{{ errorMessagges.curp.valid }}</b-form-invalid-feedback
            >
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mx-4">
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-form-input
              id="email"
              placeholder="Correo"
              type="email"
              required
              v-model.trim="v$.personalNewAccount.email.$model"
              trim
              :state="
                v$.personalNewAccount.email.$dirty
                  ? !v$.personalNewAccount.email.$error
                  : null
              "
              @blur="v$.personalNewAccount.email.$touch()"
            >
            </b-form-input>
            <b-form-invalid-feedback
              v-for="error in v$.personalNewAccount.email.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mx-4">
        <b-col class="d-flex justify-content-center mb-2" v-if="step !== 0">
          <CaptchaFriendly
            @update="isValidFriendlyCaptcha = $event"
            ref="captchaFriendly"
          />
        </b-col>
      </b-row>
    </div>
    <b-row
      class="d-flex justify-content-center mx-4 mb-4 mt-2"
      v-if="step === 0"
    >
      <b-col cols="12" sm="12" md="6">
        <b-button
          class="custom-button"
          block
          :disabled="v$.newAccount.$invalid || v$.confirmPassword.$invalid"
          @click="handleNextStep"
          variant="secondary"
        >
          Siguiente
        </b-button>
      </b-col>
    </b-row>
    <b-row class="d-flex justify-content-center mb-4 mt-2" v-if="step !== 0">
      <b-col
        cols="12"
        sm="12"
        md="6"
        class="d-flex justify-content-between mx-4"
      >
        <b-button
          class="custom-button mr-2"
          @click="handlePreviousStep"
          variant="secondary"
        >
          Anterior
        </b-button>
        <b-button
          class="custom-button"
          block
          :disabled="
            v$.newAccount.$invalid ||
            !isValidFriendlyCaptcha ||
            v$.personalNewAccount.$invalid
          "
          @click="signup"
          variant="primary"
        >
          Crear cuenta
        </b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Vue, { defineAsyncComponent } from "vue";
import { useVuelidate } from "@vuelidate/core";
import {
  required,
  email,
  helpers,
  minLength,
  maxLength,
  sameAs,
} from "@vuelidate/validators";
import CaptchaFriendly from "@/components/FriendlyCaptcha/CaptchaFriendly.vue";
import moment from "moment";
import { signal } from "../../../kernel/functions";
import SweetAlertCustom from "../../../kernel/SweetAlertCustom";
import authController from "../services/controller/auth.controller";
export default Vue.extend({
  name: "CreateAccount",
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
      step: 0,
      isValidFriendlyCaptcha: false,
      showPasswordState: false,
      showConfirmPasswordState: false,
      newAccount: {
        name: "",
        surname: "",
        lastname: "",
        phoneNumber: "",
        username: "",
        password: "",
      },
      personalNewAccount: {
        email: "",
        curp: "",
        birthday: null,
        gender: null,
      },
      confirmPassword: "",
      errorMessagges: {
        required: "Campo obligatorio",
        invalidEmail: "Correo inválido",
        minLength: "Mínimo 2 caracteres",
        maxLength: "Máximo 45 caracteres",
        noneScripts: "Campo inválido no se aceptan scripts",
        valid: "Campos inválido - caracteres inválidos",
        password: {
          valid:
            "La contraseña debe tener mínimo una mayúscula, un caracter especial (# . _) y un número (longitud de 3 a 16 car.)",
        },
        username: {
          valid:
            "Favor de ingresar un nombre de usuario válido (caracteres aceptados: . _)",
          minLength: "Mínimo 3 caracteres",
        },
        passwordMissmatch: "Las contraseñas no coinciden",
        birthday: {
          maxValue: "La fecha es inválida debes cumplir con la mayoría de edad",
        },
        curp: {
          valid: "CURP inválida",
        },
      },
      genreOptions: [
        { value: null, text: "Selecciona un género" },
        { value: { id: 1 }, text: "Masculino" },
        { value: { id: 2 }, text: "Femenino" },
        { value: { id: 3 }, text: "Otro" },
      ],
    };
  },
  methods: {
    onlynumbers(evt) {
      signal(evt);
    },
    async goBack() {
      await this.$router.push("/cimi");
    },
    todayDate() {
      return moment().subtract(18, "years").format("YYYY-MM-DD");
    },
    showPassword() {
      this.showPasswordState = !this.showPasswordState;
    },
    showConfirmPassword() {
      this.showConfirmPasswordState = !this.showConfirmPasswordState;
    },
    handleNextStep() {
      this.step = 1;
    },
    handlePreviousStep() {
      this.step = 0;
    },
    async signup() {
      try {
        if (
          this.v$.newAccount.$invalid ||
          this.v$.personalNewAccount.$invalid
        ) {
          SweetAlertCustom.invalidForm();
        } else {
          const result = await SweetAlertCustom.questionMessage();
          if (result.isConfirmed) {
            this.isLoading = true;
            const payload = {
              ...this.newAccount,
              ...this.personalNewAccount,
            };
            const response = await authController.signup(payload);
            if (!response.error) {
              this.$emit("reloadFromSignup");
              this.v$.newAccount.$reset();
              this.v$.personalNewAccount.$reset();
              this.v$.confirmPassword.$reset();
              this.newAccount = {
                name: "",
                surname: "",
                lastname: "",
                phoneNumber: "",
                username: "",
                password: "",
              };
              this.personalNewAccount = {
                email: "",
                curp: "",
                birthday: null,
                gender: null,
              };
              this.confirmPassword = "";
              this.step = 0;
            } else {
              this.isValidFriendlyCaptcha = false;
              this.$refs.captchaFriendly.$emit("reload-event");
            }
          }
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
  },
  validations() {
    return {
      newAccount: {
        name: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          minLength: helpers.withMessage(
            this.errorMessagges.minLength,
            minLength(2)
          ),
          maxLength: helpers.withMessage(
            this.errorMessagges.maxLength,
            maxLength(45)
          ),
          notScript: helpers.withMessage(
            this.errorMessagges.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(this.errorMessagges.valid, (value) =>
            /^[a-zA-Z0-9][a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*$/.test(value)
          ),
        },
        surname: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          minLength: helpers.withMessage(
            this.errorMessagges.minLength,
            minLength(2)
          ),
          maxLength: helpers.withMessage(
            this.errorMessagges.maxLength,
            maxLength(45)
          ),
          notScript: helpers.withMessage(
            this.errorMessagges.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          valid: helpers.withMessage(this.errorMessagges.valid, (value) =>
            /^[a-zA-Z0-9][a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*$/.test(value)
          ),
        },
        lastname: {
          valid: helpers.withMessage(this.errorMessagges.valid, (value) => {
            if (value === "" || value === null) return true;
            else
              return /^[a-zA-Z0-9][a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*$/.test(
                value
              );
          }),
          notScript: helpers.withMessage(
            this.errorMessagges.noneScripts,
            (value) => {
              if (value === "" || value === null) return true;
              else return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
        },
        phoneNumber: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        username: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          valid: helpers.withMessage(this.errorMessagges.valid, (value) =>
            /^[a-zA-Z0-9][a-zA-ZÁÉÍÓÚáéíóúñÑäëïöü0-9()\-_/,.#\s]*$/.test(value)
          ),
          notScript: helpers.withMessage(
            this.errorMessagges.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          minLength: helpers.withMessage(
            this.errorMessagges.minLength,
            minLength(2)
          ),
        },
        password: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          valid: (value) =>
            /^(?=.*[A-Z]+)(?=.*[._#]+)(?=.*[0-9]+)[a-zA-Z0-9._#]{3,16}$/g.test(
              value
            ),
          notScript: helpers.withMessage(
            this.errorMessagges.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
        },
      },
      personalNewAccount: {
        email: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          email: helpers.withMessage(this.errorMessagges.invalidEmail, email),
        },
        curp: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          valid: (value) => {
            if (!value) return true;
            else return /^[A-Z0-9]{18,18}$/g.test(value);
          },
        },
        birthday: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          maxValue: helpers.withMessage(
            "Sobrepasa la fecha máxima",
            (value) => {
              return moment(value).isSameOrBefore(
                new Date(
                  new Date().getFullYear() - 18,
                  new Date().getMonth(),
                  new Date().getDate()
                )
              );
            }
          ),
        },
        gender: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
      },
      confirmPassword: {
        required: helpers.withMessage(this.errorMessagges.required, required),
        sameAsPassword: sameAs(this.newAccount.password),
      },
    };
  },
});
</script>
