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
    opened: Boolean
  },
  data: function () {
    return {
      info: null
    }
  },
  mounted() {
    this.info = new this.google.maps.InfoWindow({
      content:"asdasdasdas"
    });
  },
  watch: {
    opened: function(newVal, oldVal) {
      console.log('Prop changed: ', newVal, ' | was: ', oldVal)
    }
  }
}
</script>
