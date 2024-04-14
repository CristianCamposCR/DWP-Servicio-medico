<template>
  <b-overlay :show="busy" rounded="lg" :opacity="opacity" no-wrap no-fade>
    <template #overlay>
      <div class="d-flex align-items-center">
        <b-spinner small type="grow" class="color"></b-spinner>
        <b-spinner small type="grow" class="color"></b-spinner>
        <b-spinner small type="grow" class="color"></b-spinner>
        <!-- We add an SR only text for screen readers -->
        <span class="sr-only">Cargando...</span>
      </div>
    </template>
  </b-overlay>
</template>


<script>
export default {
  name: 'CustomLoadingSection',
  props: {
    busy: {
      type: Boolean,
      default: false
    },
    opacity: {
      type: Number,
      default: 0
    }
  },
}
</script>

<style scoped>
.color {
  background-color: #808080;
  color: #808080;
}
</style>