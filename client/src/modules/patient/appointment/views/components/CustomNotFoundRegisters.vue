<template>
  <section v-if="show" class="d-flex align-items-center justify-content-center h-50">
    <div class="text-center">
      <p class="h4 text-black-50">
        <slot name="message">Upss! Al parecer no encontramos áreas, por favor intenta más tarde.</slot>
      </p>
      <img src="https://media.tenor.com/Zn-NfDoT0xQAAAAi/think-thinking.gif" alt="No hay registros" width="100px">
    </div>
  </section>
</template>

<script >
export default {
  name: 'CustomBreadCrumb',
  props: {
    show: {
      type: Boolean,
      required: true
    },
  }
}
</script>