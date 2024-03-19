<template>
  <div>
    <b-card-img
      src="/src/assets/Hospital.jpg"
      width="100px"
      height="140px"
    ></b-card-img>
    <div class="mt-5 mx-4">
      <b-col sm="12">
        <b-form-group>
          <b-input-group class="mb-3">
            <b-input
              placeholder="Correo Electronico"
              type="email"
              required
              v-model.trim="v$.signin.email.$model"
              trim
              :state="v$.signin.email.$dirty ? !v$.signin.email.$error : null"
              @blur="v$.signin.email.$touch()"
            ></b-input>
            <b-form-invalid-feedback
              v-for="error in v$.signin.email.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-input-group>
        </b-form-group>
        <b-form-group>
          <b-input-group>
            <b-form-input
              :type="showPasswordState ? 'text' : 'password'"
              placeholder="Contraseña"
              style="border-right: none !important"
              required
              v-model.trim="v$.signin.password.$model"
              trim
              :state="
                v$.signin.password.$dirty ? !v$.signin.password.$error : null
              "
              @blur="v$.signin.password.$touch()"
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
              v-for="error in v$.signin.password.$errors"
              :key="error.$uid"
            >
              {{ error.$message }}
            </b-form-invalid-feedback>
          </b-input-group>
        </b-form-group>
      </b-col>
    </div>
    <div class="d-flex justify-content-center mt-3 mb-5 mx-4">
      <b-col cols="12" sm="6">
        <b-button block class="custom-button"> Iniciar sesión </b-button>
      </b-col>
    </div>
  </div>
</template>
<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, email, helpers } from "@vuelidate/validators";
export default Vue.extend({
  name: "LoginForm",
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  data() {
    return {
      showPasswordState: false,
      signin: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    showPassword() {
      this.showPasswordState = !this.showPasswordState;
    },
  },
  validations() {
    return {
      signin: {
        email: {
          required: helpers.withMessage("Campo obligatorio", required),
          email: helpers.withMessage("Correo inválido", email),
        },
        password: {
          required: helpers.withMessage("Campo obligatorio", required),
        },
      },
    };
  },
});
</script>
