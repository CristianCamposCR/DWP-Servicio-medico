<template>
  <div>
    <b-card-img
      src="/src/assets/Hospital.jpg"
      width="100px"
      height="140px"
    ></b-card-img>
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
            v-model.trim="v$.newAccount.phone.$model"
            trim
            :state="
              v$.newAccount.phone.$dirty ? !v$.newAccount.phone.$error : null
            "
            @blur="v$.newAccount.phone.$touch()"
          >
          </b-form-input>
          <b-form-invalid-feedback
            v-for="error in v$.newAccount.phone.$errors"
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
            placeholder="Correo electrónico"
            type="email"
            required
            v-model.trim="v$.newAccount.email.$model"
            trim
            :state="
              v$.newAccount.email.$dirty ? !v$.newAccount.email.$error : null
            "
            @blur="v$.newAccount.email.$touch()"
          >
          </b-form-input>
          <b-form-invalid-feedback
            v-for="error in v$.newAccount.email.$errors"
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
      <b-col class="d-flex justify-content-center mb-3">
        <CaptchaFriendly @update="isValidFriendlyCaptcha = $event" />
      </b-col>
    </b-row>
    <b-row class="d-flex justify-content-center mb-5 mx-4">
      <b-col cols="12" sm="12" md="6">
        <b-button class="custom-button" block
        :disabled="v$.newAccount.$invalid || !isValidFriendlyCaptcha"
        > Crear cuenta </b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, email, helpers } from "@vuelidate/validators";
import CaptchaFriendly from "@/components/FriendlyCaptcha/CaptchaFriendly.vue";
export default Vue.extend({
  name: "CreateAccount",
  components: {CaptchaFriendly},
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    return {
      isValidFriendlyCaptcha: false,
      showPasswordState: false,
      showConfirmPasswordState: false,
      newAccount: {
        name: "",
        surname: "",
        lastname: "",
        phone: "",
        email: "",
        password: "",
      },
      confirmPassword: "",
      errorMessagges: {
        required: "Campo obligatorio",
        invalidEmail: "Correo inválido",
      },
    };
  },
  methods: {
    showPassword() {
      this.showPasswordState = !this.showPasswordState;
    },
    showConfirmPassword() {
      this.showConfirmPasswordState = !this.showConfirmPasswordState;
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
        phone: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        email: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          email: helpers.withMessage(this.errorMessagges.invalidEmail, email),
        },
        password: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
      },
      confirmPassword: {},
    };
  },
});
</script>
