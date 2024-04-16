<template>
  <div>
    <div class="d-flex justify-content-center">
      <b-img
          src="https://media.tenor.com/VS3AnQ7MnwgAAAAi/face-with-head-bandage-joypixels.gif"
          style="object-fit: cover; object-position: center; margin-bottom: 0"
          width="350px"
          height="300px"
      >
      </b-img>
    </div>
    <div class="d-flex justify-content-center ">
      <div class="text-center not-registers-font mt-0">
        <div class="d-flex justify-content-center text-center not-registers-font mt-0">
          <p class="m-0 w-75 my-4">Ay no! Al parecer no encontramos la página que buscas. No te preocupes, puedes regresar a
            la página anterior
            y seguir navegando.</p>
        </div>
        <button @click="goBack" class="btn btn-primary">Regresar</button>
      </div>

    </div>
  </div>
</template>
<script>
import Vue from "vue";

export default Vue.extend({
  name: "NotFound404",
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
});
</script>
