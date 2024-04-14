<template>
<div>
  <div class="progress-indicator-wrapper">
    <div class="progress-indicator" :style="{ width: progress }"></div>
  </div>
</div>
</template>

<script>
export default {
  name: "ProgressIndicator",
  data() {
    return {
      progress: "0%"
    };
  },
  methods: {
    updateProgressIndicator() {
      const { documentElement, body } = document;
      let windowScroll = body.scrollTop || documentElement.scrollTop;
      let height = documentElement.scrollHeight - documentElement.clientHeight;
      this.progress = (windowScroll / height) * 100 + "%";
    }
  },
  mounted() {
    window.addEventListener("scroll", this.updateProgressIndicator);
  }
};
</script>

<style scoped >
.progress-indicator-wrapper{

  height: 5px;
  background-color: #eee;
  width: 100%;
  top: 60px;
  .progress-indicator{
    height: 5px;
    background: #10b981;
  }
}

</style>