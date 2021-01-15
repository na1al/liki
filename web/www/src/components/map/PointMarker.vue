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
    position: Object,
    onClick: Function
  },
  mounted() {
    let marker = new this.google.maps.Marker({
      position: this.position,
      click: true,
      map: this.map,
      //  icon: POINT_MARKER_ICON_CONFIG
    });

    if (this.onClick) {
      marker.addListener("click", () => {
        this.onClick()
      });
    }
  },
}
</script>
