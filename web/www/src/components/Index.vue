<template>




  <div class="container catalog" >


    <Search/>

    <div class="d-flex justify-content-center mb-3">

      <a href="/#/catalog" class="btn btn-primary btn-lg">Каталог ліків</a>

    </div>



    <div class="row  row-cols-xl-5 row-cols-lg-4  row-cols-md-3 row-cols-sm-2 row-cols-xs-1" v-if="medicines">
      <div class="col mb-3" v-for="medicine in medicines">
        <MedicineCard :medicine="medicine"/>
      </div>
    </div>
  </div>


</template>

<script>
import Search from './header/Search'
import App from "../App";
import MedicineCard from "./MedicineCard";

export default {
  components: {App,Search, MedicineCard},
  data() {
    return {
      loading: true,
      medicines: []
    }
  },
  created() {
    this.loading = true;
    this.fetchCatalog();
  },
  methods: {
    fetchCatalog: function () {
      fetch('/v1/widget/top')
          .then(res => res.json())
          .then(res => {
            this.medicines = res.data;
          });
    }
  }

}
</script>
