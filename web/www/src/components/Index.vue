<template>




  <div class="container catalog" >


    <Search/>

    <div class="d-flex justify-content-center mb-3">

      <a href="/#/catalog" class="btn btn-primary btn-lg">Каталог ліків</a>

    </div>



    <div class="row  row-cols-xl-5 row-cols-lg-4  row-cols-md-3 row-cols-sm-2 row-cols-xs-1" v-if="medicines">
      <div class="col mb-3" v-for="item in medicines">

        <div class="card" style="cursor: pointer;" v-on:click="view(item.alias)">

          <div class="card-body ">

            <div class="overflow-hidden text-center">
              <img v-if="item.media" v-bind:src="'/images/204x177/'+item.media.name" class="rounded" alt="..." height="160">
              <img v-else src="/empty.png" class="rounded"  alt="..." height="160">
            </div>


            <h5 class="fw-light card-title fs-6 mt-3" style="height: 90px;">{{item.name | truncate(60, '...')}}</h5>
            <p class="fw-bold fst-italic card-text p-1" v-if="item.prices.length"><span v-for="price in item.prices">від {{price.price | formatPrice}}</span></p>
            <p class="fw-light fst-italic card-text bg-light p-1" v-else>Ціна відсутня</p>
          </div>
        </div>


      </div>
    </div>
  </div>


</template>

<script>
import Search from './header/Search'
import App from "../App";
export default {
  components: {App,Search},
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
    view: function (alias) {
      this.$router.push({name: 'medicine', params: {alias: alias}})
    },
    fetchCatalog: function () {
      fetch('/v1/medicine/top')
          .then(res => res.json())
          .then(res => {
            this.medicines = res.data;
          });
    }
  }

}
</script>
