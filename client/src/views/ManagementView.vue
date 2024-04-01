<template>
  <div>
    <management-navbar/>
    <router-view></router-view>
  </div>
</template>
<script>
import Vue, { defineAsyncComponent } from "vue";
export default Vue.extend({
  name: "ManagementView",
  components: {
    ManagementNavbar: defineAsyncComponent(() =>
      import("../views/components/ManagementNavbar.vue")
    ),
  },
});
</script>
