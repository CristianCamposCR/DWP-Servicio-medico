<template>
  <div class="sticky-top">
    <b-navbar
      toggleable="lg"
      type="light"
      variant="light"
      class="shadow-sm px-md-4"
    >
      <b-navbar-brand to="/">
        <img src="@/assets/image/doctor_3584957.png" alt="CIMI" height="40" />
      </b-navbar-brand>
      <b-navbar-brand to="/"><b>CIMI</b></b-navbar-brand>

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
          <b-nav-item
            :to="{ name: 'login' }"
            class="mr-4 custom-link custom-nav-font"
            >Iniciar Sesión</b-nav-item
          >

          <b-nav-form>
            <b-button
              :to="{ name: 'login', query: { action: 'register' } }"
              size="sm"
              class="my-2 my-sm-0 py-2 px-md-4 custom-link custom-nav-font"
              variant="primary"
              >Crear cuenta</b-button
            >
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <ProgressIndicator />
  </div>
</template>

<script>
import ProgressIndicator from "@/components/ProgressIndicator.vue";

export default {
  name: "Navbar",
  components: {
    ProgressIndicator,
  },
  data() {
    return {
      links: [
        { name: "Áreas", to: "areas" },
        { name: "Especialidades", to: "specialities" },
        { name: "Doctores", to: "doctors" },
      ],
    };
  },
};
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
