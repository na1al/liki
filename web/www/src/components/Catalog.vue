<template>


  <div class="container-fluid">
    <Search/>
    <Breadcrumb :items="breadcrumbs()"/>


    <div class="content">


      <div class="row">
        <div class="col-xxl-2">
          <CategoryWidget v-if="categories" :categories="categories"/>
          <FilterWidget v-if="filters" :filters="filters"/>
        </div>
        <div class="col-xxl-10">

          <div class="row  row-cols-xl-5 row-cols-lg-4  row-cols-md-3 row-cols-sm-2 row-cols-xs-1" v-if="medicines">
            <div class="col mb-3" style="cursor: pointer" v-for="medicine in medicines">
              <MedicineCard :medicine="medicine"/>
            </div>
          </div>

          <Pager v-if="totalPages"
                 :current-page="currentPage"
                 :totalPages="totalPages"
          />

        </div>
      </div>

    </div>

  </div>


</template>

<script>

import Search from './header/Search'
import Pager from './widgets/PagerWidget'
import Breadcrumb from './widgets/BreadcrumbWidget'
import MedicineCard from "./MedicineCard";
import CategoryWidget from "./widgets/CategoryWidget";
import FilterWidget from "./widgets/FilterWidget";

export default {
  data() {
    return {
      loading: true,
      category: null,
      categories: [],
      filters: [],
      vocabularies: [],
      medicines: [],
      currentPage: 1,
      totalPages: null
    }
  },
  components: {Search, CategoryWidget, FilterWidget, Pager, Breadcrumb, MedicineCard},
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

      let link = "/v1/catalog";

      if (this.$route.params.alias) {
        link += '/category/' + this.$route.params.alias;
      }

      let url = new URL(window.location.protocol + "//" + window.location.host + link);

      Object.keys(this.$route.query).forEach(key => url.searchParams.append(key, this.$route.query[key]))

      fetch(url.toString())
          .then(res => res.json())
          .then(res => {
            this.medicines = res.data.content;
            this.totalPages = res.data.totalPages;
            this.currentPage = res.data.number ? res.data.number : 0;
            window.scrollTo(0, 0);
          });

      if (!this.$route.params.alias) {
        fetch("/v1/catalog/categories")
            .then(res => res.json())
            .then(res => {
              this.categories = res.data;
            });
      } else {
        fetch("/v1/catalog/vocabularies")
            .then(res => res.json())
            .then(res => {
              this.vocabularies = res.data;
              fetch("/v1/catalog/filter/" + this.$route.params.alias)
                  .then(res => res.json())
                  .then(res => {
                    this.filters = res.data;
                  });
            });
      }
    }
  }
}
</script>


<style scoped>


</style>


