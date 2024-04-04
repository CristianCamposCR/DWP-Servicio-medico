<template>
  <div>
    <public-navbar/>
    <router-view></router-view>
  </div>
</template>
<script>
import Vue, { defineAsyncComponent } from "vue";
export default Vue.extend({
  name: "PublicView",
  components: {
    PublicNavbar: defineAsyncComponent(() =>
      import("../views/components/PublicNavbar.vue")
    ),
  },
});
</script>
