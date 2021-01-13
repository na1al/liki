<template>
  <MapLoader
      :map-config="mapConfig"
      apiKey="AIzaSyCybOzOBA-Z0LRO7m-huOKGAl6sYjqeeuo"
  >
    <template v-for="marker in markers">
      <PointMarker :position="marker"/>
    </template>
  </MapLoader>
</template>

<script>
import MapLoader from "./MapLoader.vue"
import PointMarker from './PointMarker'

export default {
  props: {
    markers: Array
  },
  data() {
    return {
      mapConfig: {
        zoom: 13,
        center: {lat: 50.450277660594104, lng: 30.5217538863496}
      }
    }
  },
  components: {
    MapLoader,
    PointMarker
  }
}
</script>
