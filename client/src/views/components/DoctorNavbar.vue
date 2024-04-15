<template>
  <div class="sticky-top">
    <b-navbar type="light " variant="white" class="shadow-sm">
      <b-navbar-brand to="/doctor" class="custom-nav-font">
        <b-img src="https://picsum.photos/50/50/?image=41"></b-img>
        CIMI</b-navbar-brand
      >

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item
            v-for="link in links"
            :key="link.name"
            :to="link.to"
            class="custom-nav-font custom-link"
            exact-active-class="active-link"
          >
            {{ link.name }}
          </b-nav-item>
        </b-navbar-nav>
        <!-- Right aligned nav items -->

        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown right>
            <!-- Using 'button-content' slot -->
            <template #button-content>
              <b-avatar
                variant="secondary"
                size="md"
                icon="person-fill"
              ></b-avatar>
            </template>
            <b-dropdown-item @click="profile()">Perfil</b-dropdown-item>
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
  name: "DoctorNavbar",
  data() {
    return {
      links: [],
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
    profile() {
      this.$router.push({ name: "profile-doctor" });
    },
  },
});
</script>
<style scoped>
.custom-link {
  transition: all 0.3s;
}
.active-link {
  color: #10b981 !important;
}

.custom-link:hover {
  scale: 1.1;
}

@media (max-width: 1000px) {
  .custom-link {
    &:hover {
      scale: 1.01;
    }
  }
}
</style>
