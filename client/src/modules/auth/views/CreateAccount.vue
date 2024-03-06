<template>
  <div class="d-flex justify-content-center align-items-center vh-100">
    <b-tabs content-class="mt-3">
      <b-tab @click="navigateLogin" title="Iniciar Sesión"></b-tab>
      <b-tab title="Crear Cuenta" active>
        <b-card :img-src="require('@/assets/Hospital.jpg')" img-alt="Card image" img-top>
          <div class="px-5 mx-5">
            <b-form-group>
              <b-row>
                <b-col>
                  <b-input-group class="mb-3">
                    <b-input placeholder="Nombre"></b-input>
                  </b-input-group>
                </b-col>
                <b-col>
                  <b-input-group class="mb-3">
                    <b-input placeholder="Apellidos"></b-input>
                  </b-input-group>
                </b-col>
              </b-row>
              <b-input-group class="mb-3">
                <b-input placeholder="Correo Electronico"></b-input>
              </b-input-group>
              <b-input-group class="mb-3">
                <b-input placeholder="Teléfono" type="number"></b-input>
              </b-input-group>
              <b-input-group class="mb-3">
                <b-input placeholder="Password" type="password"></b-input>
              </b-input-group>
              <b-input-group class="mb-3">
                <b-input placeholder="Confirmar Password"></b-input>
              </b-input-group>
            </b-form-group>
            <b-row class="custom-row">
              <b-col cols="12" sm="12" md="4">
                <b-button class="custom-button">Crear Cuenta</b-button>
              </b-col>
            </b-row>
          </div>
        </b-card>
      </b-tab>
      <router-view></router-view>
    </b-tabs>
  </div>
</template>

<script>
export default {
  methods: {
    navigateLogin() {
      this.$router.push({
        name: "login",
      });
    },
  },
};
</script>

<style>
.custom-row {
  text-align: center;
  align-items: center;
  justify-content: center;
}

.custom-button {
  background-color: #10b981;
  border-color: #10b981;
  color: white;
  font-size: 20px;
  padding: 10px 20px;
}
</style>
