<template>
  <MapLoader
      :map-config="mapConfig"
      :api-key="apiKey"
  >
    <template slot-scope="{ google, map }">
      <div v-for="(marker, index) in markers">
        <PointMarker
            :google="google"
            :map="map"
            :id="index"
            :position="marker.position"
            :on-click="markerClick"
        />
      </div>
      <InfoWindow
          :google="google"
          :map="map"
          :show="infoId"
          :position="infoPosition"
          :content="infoContent"
      />
    </template>
  </MapLoader>
</template>

<script>
import MapLoader from "./MapLoader.vue"
import PointMarker from './PointMarker'
import InfoWindow from './InfoWindow'

export default {
  components: {
    MapLoader,
    PointMarker,
    InfoWindow
  },
  props: {
    markers: Array,
    showMarkerId: Number,
    apiKey: String
  },
  watch: {
    showMarkerId: function (newVal, oldVal) {
       this.infoId = newVal;
       this.infoPosition = this.markers[newVal].position;
       this.infoContent = this.markers[newVal].content;
    }
  },
  data() {
    return {
      mapConfig: {
        zoom: 13,
        center: {lat: 50.450277660594104, lng: 30.5217538863496}
      },
      infoId: null,
      infoPosition: null,
      infoContent: null
    }
  },
  methods: {
    markerClick: function (component) {
      this.infoId = component.id;
      this.infoPosition = this.markers[component.id].position;
      this.infoContent = this.markers[component.id].content;
    }
  }
}
</script>
