<template>
  <div>
    <div class="d-flex justify-content-center">
      <b-img
        src="/src/assets/image/giphy.gif"
        style="object-fit: cover; object-position: center; margin-bottom: 0"
        width="350px"
        height="300px"
      >
      </b-img>
    </div>
    <div class="d-flex justify-content-center">
      <div v-if="message" class="text-center not-registers-font mt-0">
        Upss! Al parecer no encontramos {{ message }}, por favor intenta más
        tarde.
      </div>
      <div v-else class="text-center not-registers-font mt-0">
        Sin registros
      </div>
    </div>
  </div>
</template>
<script>
import Vue from "vue";

export default Vue.extend({
  name: "NoRegisters",
  props: {
    message: {
      type: String,
    },
  },
});
</script>
