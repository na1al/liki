<template>

  <div>


    <div class="view">

      <div class="container">
        <Search/>
        <Breadcrumb :items="breadcrumbs()"/>


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
          <div class="alert alert-success mt-4" role="alert" v-else-if="!this.prices || !this.prices.size">
            <h4 class="alert-heading">{{ medicine.name }}</h4>
            <p>Данний препарат незнайдений в жодній із аптек.</p>
            <hr>
            <p class="mb-0" v-if="category">
              Ви можете перейти до категорії <a v-bind:href="'/#/catalog/'+category.alias">{{ category.name }}</a> для
              пошуку анологів чи скористатись пошуком.
            </p>
            <p class="mb-0" v-else>
              Ви можете скористатись пошуком. Можливо ми підберем для вас щось краще.
            </p>
          </div>


          <div v-if="this.prices && this.prices.size" class="mt-5">


            <div class="accordion" id="accordionExample">

              <div class="accordion-item" v-for="group in prices.values()">

                <h2 class="accordion-header" id="headingOne">
                  <button class="accordion-button" type="button" data-bs-toggle="collapse"
                          v-bind:data-bs-target="'#collapse'+group.pharmacy.id.integrationId" aria-expanded="true"
                          aria-controls="collapseOne">

                    <img src="/empty.png" v-bind:src="'/logos/'+group.pharmacy.id.integrationId+'.jpg'"
                         class="flex-shrink-0 rounded me-3" alt="..." width="40" height="40">
                    {{ group.pharmacy.name }} <span class="fw-bold ms-auto"> від {{
                      group.minPrice | formatPrice
                    }} </span>
                  </button>
                </h2>

                <div v-bind:id="'collapse'+group.pharmacy.id.integrationId" class="accordion-collapse collapse"
                     aria-labelledby="headingOne" data-bs-parent="#accordionExample">


                  <div class="d-flex position-relative border-bottom p-3 " style="cursor: pointer"
                       v-for="item in group.items" v-if="item.pharmacy.lat"
                       v-on:click="showInfoWindow(item.index)">
                    <img src="/empty.png" v-bind:src="'/logos/'+item.pharmacy.id.integrationId+'.jpg'"
                         class="flex-shrink-0 rounded me-3" alt="..." width="40" height="40">
                    <div>
                      <h5 class="mt-0">{{ item.pharmacy.name }}</h5>
                      <span class="fw-light">{{ item.pharmacy.address }} <span
                          v-if="item.pharmacy.phone">тел.: {{ item.pharmacy.phone | truncate(60, '...') }}</span></span>
                    </div>
                    <span class="ms-auto fs-6">від {{ item.price | formatPrice }}</span>
                  </div>


                </div>

              </div>


            </div>


          </div><!-- if prices -->


        </div>

      </div>

    </div>


    <div class="map-container" v-if="addressComponentVersion">
      <Addresses
          :key="addressComponentVersion"
          :markers="markers"
          :show-marker-id="showMarkerId"
      />
    </div>

  </div>


</template>

<script>

import Search from './header/Search'
import Addresses from './map/AddressMap'
import Breadcrumb from './widgets/BreadcrumbWidget'


const TAG = {
  TYPE_CATEGORY: 1
}

export default {
  data() {
    return {
      loading: true,
      alias: null,
      medicine: null,
      category: null,
      markers: [],
      showMarkerId: null,
      prices: null,
      addressComponentVersion: 0,
    }
  },
  components: {Search, Addresses, Breadcrumb},
  created() {
    this.loading = true;
    this.fetchMedicine();
  },
  watch: {
    '$route': 'fetchMedicine'
  },
  methods: {
    breadcrumbs: function () {

      let breadcrumbs = [
        {
          title: "Головна",
          url: "/",
        },
        {
          title: "Каталог",
          url: "/#/catalog",
        }
      ];

      if (this.category) {
        breadcrumbs[breadcrumbs.length] = {
          title: this.category.name,
          url: "/#/catalog/" + this.category.alias,
        };
      }

      breadcrumbs[breadcrumbs.length] = {
        title: this.medicine ? this.medicine.name : '',
      };

      return breadcrumbs;
    },
    showInfoWindow: function (index) {
      this.showMarkerId = parseInt(index);
    },
    fetchMedicine: function () {
      let that = this;
      this.alias = this.$route.params.alias
      fetch('/v1/medicine/alias/' + this.alias)
          .then(res => res.json())
          .then(res => {
            this.medicine = res.data;
            this.category = that._getCategoryFromMedicine(this.medicine);
            this.fetchMedicinePrices();
          });
    },
    fetchMedicinePrices: function () {
      let that = this;
      fetch('/v1/price/medicine/' + this.medicine.id)
          .then(res => res.json())
          .then(res => {
            this.loading = false;
            this.prices = that._getPrices(res.data)
            this.markers = that._getMarkers(res.data);
            this.addressComponentVersion++;
          });
    },
    _getPrices: function (data) {
      let prices = new Map();
      data.forEach((item, i) => {

        let groupId = item.pharmacy.id.integrationId;

        if (!prices.has(groupId)) {
          prices.set(groupId, {
            pharmacy: item.pharmacy,
            minPrice: 0,
            items: []
          })
        }

        let group = prices.get(groupId);
        item.index = i;
        group.items[group.items.length] = item;

        if (!group.minPrice || group.minPrice < item.price) {
          group.pharmacy = item.pharmacy;
          group.minPrice = item.price;
        }

      });

      return prices;
    },

    _getMarkers: function (data) {
      return data.map(item => {
        return {
          content: '<h5>' + item.pharmacy.name + '</h5><p>' + item.pharmacy.address + '</p><p>' + (item.pharmacy.phone ? item.pharmacy.phone : '') + '</p><div class="fw-bold fs-5">' + this.$options.filters.formatPrice(item.price) + '</div>',
          position: {
            lat: item.pharmacy.lat,
            lng: item.pharmacy.lng,
          }
        }
      });
    },

    _getCategoryFromMedicine: function (medicine) {
      return medicine.tag.find(v => v.vocabulary.id === TAG.TYPE_CATEGORY);
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


