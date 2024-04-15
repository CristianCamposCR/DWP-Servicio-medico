<template>
  <div>
    <navbar/>
    <router-view></router-view>
  </div>
</template>
<script>
import Vue, { defineAsyncComponent } from "vue";
export default Vue.extend({
  name: "PublicView",
  components: {
    Navbar: defineAsyncComponent(() =>
      import("../modules/public/components/Navbar.vue")
    ),
  },
});
</script>
