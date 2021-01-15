<template>
  <MapLoader
      :map-config="mapConfig"
      apiKey="AIzaSyCybOzOBA-Z0LRO7m-huOKGAl6sYjqeeuo"
  >
    <template slot-scope="{ google, map }">
      <div v-for="marker in markers">
        <PointMarker
            :position="marker"
            :google="google"
            :map="map"
            @click="showInfo"
        />
      </div>
      <InfoWindow :google="google"
                  :map="map"
                  :opened="showInfoProp"
      />
    </template>
  </MapLoader>
</template>

<script>
import MapLoader from "./MapLoader.vue"
import PointMarker from './PointMarker'
import InfoWindow from './InfoWindow'

export default {
  props: {
    markers: Array
  },
  data() {
    return {
      showInfoProp: true,
      info: {
        lat: 50.450277660594104,
        lng: 30.5217538863496
      },
      mapConfig: {
        zoom: 13,
        center: {lat: 50.450277660594104, lng: 30.5217538863496}
      }
    }
  },
  components: {
    MapLoader,
    PointMarker,
    InfoWindow
  },
  methods: {
    showInfo: function (state) {
      console.log("aaaaa");
      // this.showInfoProp = true;
    }
  }
}
</script>
