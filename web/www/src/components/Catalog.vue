<template>

  <div>


    <div class="view">

      <div class="container">
        <Search/>
        <Breadcrumb :items="breadcrumbs()"/>

        <div class="medicine border rounded mb-2 p-2" style="cursor: pointer" v-for="medicine in medicines"
             v-on:click="view(medicine.alias)">


          <div class="d-flex position-relative">

            <div style="width: 160px;" class="overflow-hidden me-3">
              <img v-if="medicine.media" v-bind:src="'/images/204x177/'+medicine.media.name" class=" me-3"
                   style="max-height: 100px;">
              <img v-else src="/empty.png" class="me-3" style="max-height: 100px;">
            </div>

            <div>
              <h4>{{ medicine.name }}</h4>
              <p class="fw-light fst-italic" style="font-size: 14px;" v-if="medicine.registrations.length">р/н: <span
                  v-for="item in medicine.registrations">{{ item.code }}</span></p>
              <p class="fw-light fst-italic" style="font-size: 14px;" v-else>р/н: -</p>

              <span class="fw-bold fst-italic" v-for="price in medicine.prices">від {{
                  price.price | formatPrice
                }}</span>

            </div>
          </div>


        </div> <!-- class medicine -->

        <Pager v-if="totalPages"
               :current-page="currentPage"
               :totalPages="totalPages"
        />

      </div>

    </div>


    <div class="map-container">
      <Addresses/>
    </div>

  </div>


</template>

<script>

import Search from './header/Search'
import Pager from './Pager'
import Addresses from './map/AddressMap'
import Breadcrumb from './Breadcrumb'

export default {
  data() {
    return {
      loading: true,
      medicines: [],
      currentPage: 1,
      totalPages: null
    }
  },
  components: {Search, Addresses, Pager, Breadcrumb},
  created() {
    this.loading = true;
    this.fetchMedicine();
  },
  watch: {
    '$route': 'fetchMedicine'
  },
  methods: {
    breadcrumbs: function () {
      return [
        {
          title: "Головна",
          url: "/",
        },
        {
          title: "Каталог"
        }
      ];
    },
    view: function (alias) {
      this.$router.push({name: 'medicine', params: {alias: alias}})
    },
    fetchMedicine: function () {

      let url = new URL(window.location.protocol + "//" + window.location.host + "/v1/medicine");
      Object.keys(this.$route.query).forEach(key => url.searchParams.append(key, this.$route.query[key]))

      fetch(url)
          .then(res => res.json())
          .then(res => {
            this.medicines = res.data.content;
            this.totalPages = res.data.totalPages;
            this.currentPage = res.data.number ? res.data.number : 0;
            window.scrollTo(0, 0);
          });
    }
  }
}
</script>


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

.accordion-button::after {
  display: none;
}

.accordion-button:not(.collapsed) {
  color: #000;
  background-color: #fff;
}

</style>


