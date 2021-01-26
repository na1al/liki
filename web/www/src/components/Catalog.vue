<template>


  <div class="container-fluid">
    <Search/>
    <Breadcrumb :items="breadcrumbs()"/>


    <div class="content">


      <div class="row">
        <div class="d-none d-md-block col-md-3 col-xxl-2">
          <CategoryWidget v-if="categories && !filter" :categories="categories"/>
          <FilterWidget v-if="filter" :data="filter"/>
        </div>
        <div class="col-md-9 col-xxl-10">

          <div class="row row-cols-xxl-5 row-cols-xl-4 row-cols-lg-3 row-cols-md-2 row-cols-sm-2 row-cols-xs-1" v-if="medicines">
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
      filter: null,
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
    fetchMedicine: function () {

      let link = this.$route.params.alias ? "/v1/catalog/category/" + this.$route.params.alias : "/v1/catalog";
      let url = new URL(window.location.protocol + "//" + window.location.host + link);
      this.category = this.$route.params.alias ? this.category : null;
      this.filter = this.$route.params.alias ? this.filter : null;

      Object.keys(this.$route.query).forEach(key => url.searchParams.append(key, this.$route.query[key]))

      if (this.$route.params.alias) {
        fetch("/v1/catalog/filter/" + this.$route.params.alias)
            .then(res => res.json())
            .then(res => {
              this.category = res.data.category;
              this.filter = res.data;
            });
      }

      if (!this.categories.length) {
        fetch("/v1/catalog/categories")
            .then(res => res.json())
            .then(res => {
              this.categories = res.data;
            });
      }

      fetch(url.toString())
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


