<template>
  <div class="d-flex justify-content-center align-items-center vh-100">
    <div class="mx-3">
      <b-card no-body class="overflow-hidden shadow">
        <b-tabs
          content-class="mt-3"
          fill
          active-nav-item-class="font-weight-bold"
        >
          <b-tab active>
            <template #title>
              <b-icon icon="person-fill"></b-icon>
              Iniciar sesión
            </template>
            <login-form />
          </b-tab>
          <b-tab>
            <template #title>
              <b-icon icon="person-lines-fill"></b-icon>
              Crear cuenta
            </template>
            <create-account />
          </b-tab>
        </b-tabs>
      </b-card>
    </div>
  </div>
</template>

<script>
import Vue, { defineAsyncComponent } from "vue";

export default Vue.extend({
  name: "LoginView",
  components: {
    CreateAccount: defineAsyncComponent(() =>
      import("@/modules/auth/views/CreateAccount.vue")
    ),
    LoginForm: defineAsyncComponent(() =>
      import("@/modules/auth/views/LoginForm.vue")
    ),
  },
});
</script>

<style>
.nav-link {
  color: grey;
  border-color: transparent !important;
  border-bottom: solid #ffffff !important;
}
.nav-link.active {
  color: #10b981 !important;
  border-bottom: 3px solid #10b981 !important;
}

/* Estilos para los enlaces al pasar el cursor */
.nav-link:hover {
  color: #10b981 !important;
}
</style>
