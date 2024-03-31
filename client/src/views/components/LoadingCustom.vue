<template>
  <loading-overlay :active="isLoading" color="#808080" loader="dots">
    <template v-slot:after><br />Cargando...</template>
  </loading-overlay>
</template>

<script>
import Vue from "vue";
export default Vue.extend({
  name: "LoadingCustom",
  props: {
    isLoading: {
      type: Boolean,
      default: false,
    },
  },
});
</script>
