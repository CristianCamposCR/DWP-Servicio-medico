<template>
  <div>
    <b-navbar type="light " variant="white" class="shadow-sm">
      <b-navbar-brand to="/" class="custom-nav-font">
        <b-img src="https://picsum.photos/50/50/?image=41"></b-img>
        CIMI</b-navbar-brand
      >
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
    </b-navbar>
  </div>
</template>
<script>
import Vue from "vue";
export default Vue.extend({
  name: "ManagmentNavbar",
  data() {
    return {
      links: [
        { name: "Áreas", to: "areas-managment" },
        { name: "Especialidades", to: "especialities-managment" },
      ],
    };
  },
});
</script>
