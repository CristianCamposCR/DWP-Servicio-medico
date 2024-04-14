<template>
  <div>
    <b-overlay :show="busy" rounded="lg" :opacity="opacity" no-wrap no-fade>
      <template #overlay>
        <div class="d-flex align-items-center">
          <b-spinner small type="grow" variant="secondary"></b-spinner>
          <b-spinner small type="grow" variant="dark"></b-spinner>
          <b-spinner small type="grow" variant="secondary"></b-spinner>
          <!-- We add an SR only text for screen readers -->
          <span class="sr-only">Cargando...</span>
        </div>
      </template>
    </b-overlay>
  </div>
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