<template>
  <div>
    <b-card-img
      src="/src/assets/Hospital.jpg"
      width="100px"
      height="140px"
    ></b-card-img>
    <div v-if="step === 0">
      <b-row class="mx-4 mt-5">
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
              v-for="error in v$.newAccount.name.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
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
              v-for="error in v$.newAccount.surname.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
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
            >
            </b-form-input>
          </b-form-group>
        </b-col>
        <b-col cols="12" sm="12" md="6">
          <b-form-group>
            <b-form-input
              id="phone"
              placeholder="Teléfono"
              type="text"
              required
              v-model.trim="v$.newAccount.phoneNumber.$model"
              trim
              :state="
                v$.newAccount.phoneNumber.$dirty
                  ? !v$.newAccount.phoneNumber.$error
                  : null
              "
              @blur="v$.newAccount.phoneNumber.$touch()"
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
              v-for="error in v$.newAccount.username.$errors"
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
                v-for="error in v$.newAccount.password.$errors"
                :key="error.$uid"
              >
                {{ error.$message }}
              </b-form-invalid-feedback>
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
            </b-input-group>
          </b-form-group>
        </b-col>
      </b-row>
    </div>

    <div v-if="step !== 0">
      <b-row class="mx-4 mt-5">
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-form-datepicker
              id="example-datepicker"
              class="mb-2"
              placeholder="Fecha de nacimiento"
              v-model="personalNewAccount.birthday"
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
              v-for="error in v$.newAccount.name.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>
        <b-col cols="12" sm="12">
          <b-form-group>
            <b-form-select></b-form-select>
            <b-form-invalid-feedback
              v-for="error in v$.newAccount.surname.$errors"
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
              v-for="error in v$.personalNewAccount.curp.$errors"
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
        <b-col class="d-flex justify-content-center mb-3" v-if="step !== 0">
          <CaptchaFriendly @update="isValidFriendlyCaptcha = $event" />
        </b-col>
      </b-row>
    </div>
    <b-row
      class="d-flex justify-content-center mb-5 mx-4 mt-2"
      v-if="step === 0"
    >
      <b-col cols="12" sm="12" md="6">
        <b-button
          class="custom-button"
          block
          :disabled="v$.newAccount.$invalid"
          @click="handleNextStep"
        >
          Siguiente
        </b-button>
      </b-col>
    </b-row>
    <b-row
      class="d-flex justify-content-center mb-5 mx-4 mt-2"
      v-if="step !== 0"
    >
      <b-col cols="12" sm="12" md="6">
        <b-button class="custom-button" block @click="handlePreviousStep">
          Regresar
        </b-button>
        <b-button
          class="custom-button"
          block
          :disabled="
            v$.newAccount.$invalid ||
            !isValidFriendlyCaptcha ||
            v$.personalNewAccount.$invalid
          "
        >
          Crear cuenta
        </b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, email, helpers } from "@vuelidate/validators";
import CaptchaFriendly from "@/components/FriendlyCaptcha/CaptchaFriendly.vue";
import moment from "moment";
export default Vue.extend({
  name: "CreateAccount",
  components: { CaptchaFriendly },
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    return {
      step: 1,
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
        gender: {
          id: 0,
        },
      },
      confirmPassword: "",
      errorMessagges: {
        required: "Campo obligatorio",
        invalidEmail: "Correo inválido",
      },
    };
  },
  methods: {
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
  },
  validations() {
    return {
      newAccount: {
        name: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        surname: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        phoneNumber: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        password: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        username: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
      },
      personalNewAccount: {
        email: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          email: helpers.withMessage(this.errorMessagges.invalidEmail, email),
        },
        curp: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        birthday: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        gender: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
      },
      confirmPassword: {},
    };
  },
});
</script>
