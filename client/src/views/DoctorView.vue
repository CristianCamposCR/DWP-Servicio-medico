<template>
    <div>
      <doctor-navbar/>
      <router-view></router-view>
    </div>
  </template>
  <script>
  import Vue, { defineAsyncComponent } from "vue";
  export default Vue.extend({
    name: "DoctorView",
    components: {
      DoctorNavbar: defineAsyncComponent(() =>
        import("../views/components/DoctorNavbar.vue")
      ),
    },
  });
  </script>
  