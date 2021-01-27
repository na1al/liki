<template>


  <div class="container-fluid">
    <Search/>
    <Breadcrumb :items="breadcrumbs()"/>


    <div class="content">


      <div class="row">
        <div class="d-none d-md-block col-md-3 col-xxl-2">
          <CategoryWidget v-if="categories && !filters" :categories="categories"/>
          <FilterWidget v-if="filters"
                        :filters="filters"
          />
        </div>
        <div class="col-md-9 col-xxl-10">

          <div class="row row-cols-xxl-5 row-cols-xl-4 row-cols-lg-3 row-cols-md-2 row-cols-sm-2 row-cols-xs-1"
               v-if="medicines">
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
      filters: null,
      medicines: [],
      currentPage: 1,
      totalPages: null
    }
  },
  components: {Search, CategoryWidget, FilterWidget, Pager, Breadcrumb, MedicineCard},
  created() {
    this.loading = true;
    this.init();
  },
  watch: {
    '$route': ['fetchMedicine', 'fetchFilter', 'fetchCategories']
  },
  methods: {
    init() {
      this.fetchMedicine();
      this.fetchCategories();
      this.fetchFilter();
    },
    getUrl: function (link) {
      let url = new URL(window.location.protocol + "//" + window.location.host + link);
      Object.keys(this.$route.query).forEach(key => url.searchParams.append(key, this.$route.query[key]));
      return url;
    },
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

      return breadcrumbs;
    },
    fetchFilter: function () {

      if (!this.$route.params.alias) {
        this.filters = null;
        this.category = null;
        return;
      }

      let path = "/v1/catalog/filter/" + this.$route.params.alias;

      fetch(this.getUrl(path).toString())
          .then(res => res.json())
          .then(res => {
            this.category = res.data.category;
            this.filters = res.data.filters;
          });
    },
    fetchMedicine: function () {
      let path = this.$route.params.alias ? "/v1/catalog/category/" + this.$route.params.alias : "/v1/catalog"
      fetch(this.getUrl(path).toString())
          .then(res => res.json())
          .then(res => {
            this.medicines = res.data.content;
            this.totalPages = res.data.totalPages;
            this.currentPage = res.data.number ? res.data.number : 0;
            window.scrollTo(0, 0);
          });
    },
    fetchCategories: function () {

      if (this.$route.params.alias || this.categories.length) return;

      fetch("/v1/catalog/categories")
          .then(res => res.json())
          .then(res => {
            this.categories = res.data;
          });
    }
  }
}
</script>


<style scoped>


</style>


