<template>

  <div>


    <div class="view">

      <div class="container">
        <Search/>
        <div class="medicine" v-if="medicine">


          <div class="d-flex position-relative">

            <img v-if="medicine.media" v-bind:src="'/images/204x177/'+medicine.media.name"
                 class="img-thumbnail flex-shrink-0 me-3">
            <img v-else src="/empty.png" class="img-thumbnail flex-shrink-0 me-3" width="204">

            <div>
              <h1>{{ medicine.name }}</h1>
              <p class="fw-light fst-italic" style="font-size: 14px;" v-if="medicine.registrations.length">р/н: <span
                  v-for="item in medicine.registrations">{{ item.code }}</span></p>
              <p class="fw-light fst-italic" style="font-size: 14px;" v-else>р/н: -</p>

              <p class="fw-bold fst-italic" v-if="medicine.prices.length"><span
                  v-for="price in medicine.prices">від {{ price.price | formatPrice }}</span></p>
              <p class="fw-light fst-italic bg-light p-1" v-else>Ціна відсутня</p>

            </div>
          </div>


          <div class="text-center" v-if="loading">
            <div class="spinner-border" role="status">
              <span class="visually-hidden">Loading...</span>
            </div>
          </div>


          <div v-if="prices" class="mt-5">


            <div class="d-flex position-relative border p-2 mb-2 rounded" v-for="item in prices">
              <img src="/empty.png" v-bind:src="'/logos/'+item.pharmacy.id.integrationId+'.jpg'"
                   class="flex-shrink-0 rounded me-3" alt="..." width="40" height="40">
              <div>
                <h5 class="mt-0">{{ item.pharmacy.name }}</h5>
                <span class="fw-light">{{ item.pharmacy.address }} <span
                    v-if="item.pharmacy.phone">тел.: {{ item.pharmacy.phone | truncate(60, '...') }}</span></span>
              </div>
              <span class="ms-auto fs-6">{{ item.price | formatPrice }}</span>


            </div>

          </div>

        </div>

      </div>

    </div>


    <div class="map-container" v-if="markers.length">
      <Addresses :markers="markers"/>
    </div>


  </div>


</template>

<script>

import Search from './header/Search'
import Addresses from './map/AddressMap'


export default {
  data() {
    return {
      loading: true,
      alias: null,
      medicine: null,
      markers: [],
      prices: []
    }
  },
  components: {Search, Addresses},
  created() {
    this.loading = true;
    this.fetchMedicine();
  },
  watch: {
    '$route': 'fetchMedicine'
  },
  methods: {
    fetchMedicine: function () {
      let that = this;
      this.alias = this.$route.params.alias
      fetch('/v1/medicine/alias/' + this.alias)
          .then(res => res.json())
          .then(res => {
            this.medicine = res.data;
            this.fetchMedicinePrices();
          //  self.markersa = [{lat: 50.450277660594104, lng: 30.5217538863496}]
          });
    },
    fetchMedicinePrices: function () {
      fetch('/v1/price/medicine/' + this.medicine.id)
          .then(res => res.json())
          .then(res => {
            this.loading = false;
            this.prices = res.data;
            for (let i in res.data) {
              this.markers[i] = {
                lat: res.data[i].pharmacy.lat,
                lng: res.data[i].pharmacy.lng,
              };
            }
            console.log('Markers fetched');
          });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.view {
  width: 50%;
  float: left;
}

.map-container {
  height: 100%;
  width: 50%;
  float: right;
  position: fixed;
  right: 0px;
}
</style>


