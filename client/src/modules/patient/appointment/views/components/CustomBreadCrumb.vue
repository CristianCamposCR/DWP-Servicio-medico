<template>
  <b-breadcrumb class="m-0 bg-transparent custom-iteam">

    <b-breadcrumb-item v-for="(link) in links"
                       :to="link.to ? link.to : null"
                       :key="link.text"
                       :active="active === link.step"
                       v-if="link.step <= active"
    >
      <b-icon :icon="link.icon" scale="1.25" shift-v="1.25" aria-hidden="true"></b-icon>
      {{ link.text }}
    </b-breadcrumb-item>

  </b-breadcrumb>
</template>

<script >
export default {
  name: 'CustomBreadCrumb',
  props: {
    links: {
      type: Array,
      required: true
    },
    active: {
      type: Number,
      required: true
    }
  }
}


</script>

<style scoped>
.custom-iteam .breadcrumb-item:hover > a{
  text-decoration: none;
  cursor: default;
  color: var(--primary);
}
</style>