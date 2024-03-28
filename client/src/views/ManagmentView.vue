<template>
  <div>
    <managment-navbar/>
    <router-view></router-view>
  </div>
</template>
<script>
import Vue, { defineAsyncComponent } from "vue";
export default Vue.extend({
  name: "ManagmentView",
  components: {
    ManagmentNavbar: defineAsyncComponent(() =>
      import("../views/components/ManagmentNavbar.vue")
    ),
  },
});
</script>
