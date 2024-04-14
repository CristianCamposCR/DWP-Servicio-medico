<template>
  <div class="sticky-top">
    <b-navbar toggleable="md" type="light" variant="white" class="shadow-sm">
      <b-navbar-brand to="/" class="custom-nav-font">
        <b-img src="https://cdn-icons-png.flaticon.com/512/1055/1055685.png" style="height: 40px"></b-img>
        &nbsp; CIMI
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item
            v-for="link in links"
            :key="link.name"
            :to="link.to"
            class="custom-nav-font"
          >
            {{ link.name }}
          </b-nav-item>
        </b-navbar-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <div class="mt-3 custom-role-nav">Administrador</div>

          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <b-avatar variant="secondary" size="md" icon="person-fill"></b-avatar>
            </template>
            <b-dropdown-item disabled>Rol: Administrador</b-dropdown-item>
            <b-dropdown-item @click="logout()">Cerrar sesión</b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>
<script>
import Vue from "vue";
export default Vue.extend({
  name: "ManagementNavbar",
  data() {
    return {
      links: [
        { name: "Áreas", to: "areas-management" },
        { name: "Especialidades", to: "specialties-management" },
        { name: "Pacientes", to: "patient-management" },
        { name: "Doctores", to: "doctors-management" },
      ],
    };
  },
  methods: {
    logout() {
      this.$swal
        .fire({
          text: "¿Seguro que desea realizar la acción?",
          icon: "question",
          showDenyButton: true,
          denyButtonText: "Cancelar",
          confirmButtonColor: "#0d8e66",
          confirmButtonText: "Aceptar",
          reverseButtons: true,
        })
        .then((result) => {
          if (result.isConfirmed) {
            localStorage.clear();

            this.$router.replace({ name: "login" });
          }
        });
    },
  },
});
</script>
