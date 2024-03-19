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
            <b-input placeholder="Correo Electronico"></b-input>
          </b-input-group>
        </b-form-group>
        <b-form-group>
          <b-input-group>
            <b-form-input
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
export default Vue.extend({
  name: "LoginForm",
  data() {
    return {
      showPasswordState: false,
    };
  },
  methods: {
    showPassword() {
      this.showPasswordState = !this.showPasswordState;
    },
  },
});
</script>
