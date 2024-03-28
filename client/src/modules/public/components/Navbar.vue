<template>
  <div class="sticky-top">
    <b-navbar toggleable="lg" type="light" variant="light" class="shadow-sm px-md-4">
      <b-navbar-brand href="#">
        <img src="@/assets/image/doctor_3584957.png" alt="CIMI" height="40">
      </b-navbar-brand>
      <b-navbar-brand href="#"><b>CIMI</b></b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="#" class="custom-link">Inicio</b-nav-item>
        </b-navbar-nav>

        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">

          <b-nav-item href="#" class="mr-4 custom-link" >Iniciar Sesión</b-nav-item>

          <b-nav-form>
            <b-button size="sm" class="my-2 my-sm-0 py-2 px-md-4 custom-link" type="button" variant="success">Crear cuenta</b-button>
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <ProgressIndicator/>
  </div>
</template>


<script>
import ProgressIndicator from "@/components/ProgressIndicator.vue";

export default {
  name: 'Navbar',
  components: {
    ProgressIndicator
  }
}
</script>

<style scoped>
.custom-link{
  transition: all 0.3s;
}

.custom-link:hover{
  scale: 1.1;
}

</style>