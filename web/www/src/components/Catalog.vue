<template>


  <div class="container-fluid">
    <Search/>
    <Breadcrumb :items="breadcrumbs()"/>


    <div class="content">


      <div class="row">
        <div class="col-xxl-2">
          One of three columns
        </div>
        <div class="col-xxl-10">

          <div class="row  row-cols-xl-5 row-cols-lg-4  row-cols-md-3 row-cols-sm-2 row-cols-xs-1" v-if="medicines">
            <div class="col mb-3" style="cursor: pointer" v-for="medicine in medicines">
              <MedicineCard :medicine="medicine"/>
            </div>
          </div>

        </div>
      </div>

    </div>

    <Pager v-if="totalPages"
           :current-page="currentPage"
           :totalPages="totalPages"
    />

  </div>


</template>

<script>

import Search from './header/Search'
import Pager from './Pager'
import Addresses from './map/AddressMap'
import Breadcrumb from './Breadcrumb'
import MedicineCard from "./MedicineCard";

export default {
  data() {
    return {
      loading: true,
      category: null,
      medicines: [],
      currentPage: 1,
      totalPages: null
    }
  },
  components: {Search, Addresses, Pager, Breadcrumb, MedicineCard},
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
    fetchMedicine: function () {

      let link = window.location.protocol + "//" + window.location.host + "/v1/catalog";

      if (this.$route.params.alias) {
        link += '/category/' + this.$route.params.alias;
      }

      let url = new URL(link);

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


</style>


