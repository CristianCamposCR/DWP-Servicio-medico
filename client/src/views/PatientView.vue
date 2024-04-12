<template>
  <div>
    <patient-navbar />
    <router-view></router-view>
  </div>
</template>
<script>
import Vue, { defineAsyncComponent } from "vue";
export default Vue.extend({
  name: "PatientView",
  components: {
    PatientNavbar: defineAsyncComponent(() => import("../views/components/PatientNavbar.vue")),
  },
});
</script>
