<template></template>
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
    id: Number,
    position: Object,
    onClick: Function
  },
  data: function () {
    return {
      marker: null
    }
  },
  mounted() {
    this.marker = new this.google.maps.Marker({
      position: this.position,
      map: this.map,
      //  icon: POINT_MARKER_ICON_CONFIG
    });

    if (this.onClick) {
      this.marker.addListener("click", () => {
        this.onClick(this)
      });
    }
  },
}
</script>
