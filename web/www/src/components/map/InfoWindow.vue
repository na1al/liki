<template>
  <div>
    <slot></slot>
  </div>
</template>
<script>
export default {
  props: {
    google: {
      type: Object,
      required: true
    },
    map: {
      type: Object,
      required: true
    },
    show: Number,
    position: Object,
    content: String
  },
  data: function () {
    return {
      infoWindow: null,
    }
  },
  mounted() {
    this.infoWindow = new this.google.maps.InfoWindow({});
  },
  watch: {
    show: function (newVal, oldVal) {
      this.infoWindow.setContent(this.content);
      this.infoWindow.setPosition(this.position);
      this.infoWindow.open(this.map);
    }
  }
}
</script>
