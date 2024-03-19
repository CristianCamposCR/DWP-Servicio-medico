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
          <b-form-input id="name" placeholder="Nombre" type="text" required>
          </b-form-input>
        </b-form-group>
      </b-col>
      <b-col cols="12" sm="12" md="6">
        <b-form-group>
          <b-form-input
            id="surname"
            placeholder="Primer apellido"
            type="text"
            required
          >
          </b-form-input>
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
          <b-form-input id="phone" placeholder="Teléfono" type="text" required>
          </b-form-input>
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
          >
          </b-form-input>
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
    <b-row class="d-flex justify-content-center mb-5 mx-4">
      <b-col cols="12" sm="12" md="6">
        <b-button class="custom-button" block> Crear cuenta </b-button>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import Vue from "vue";
export default Vue.extend({
  name: "CreateAccount",
  data() {
    return {
      showPasswordState: false,
      showConfirmPasswordState: false,
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
});
</script>
