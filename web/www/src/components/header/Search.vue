<template>

  <div class="container mt-5">

    <h1>Каталог ліків Украіни</h1>

    <form v-on:submit.prevent="live">
      <div class="mb-3">
        <label for="search" class="form-label">Пошук</label>
        <input class="form-control" id="search" aria-describedby="emailHelp" v-model="search" type="search"
               v-on:keyup="live" v-on:click="live">
        <div class="form-text">
          {{ searchStatus }}
        </div>
      </div>
    </form>


    <div class="position-relative">

      <div v-if="results" class="shadow-lg position-absolute mb-5 bg-white rounded" style="z-index: 1; width: 100%;">

        <div class="d-flex position-relative border-bottom mb-2 p-2" style="cursor: pointer;" v-for="item in results"
             v-on:click="view(item.alias)">
          <img v-if="item.image" v-bind:src="'/images/204x177/'+item.image" class=" flex-shrink-0 me-3" width="40">
          <img v-else src="/empty.png" class="img-thumbnail flex-shrink-0 me-3" width="40" height="20">
          <div>
            <h5 class="fw-light fs-6">{{ item.name }}</h5>
            <span class="ms-auto fw-light fs-6" v-if="item.price">від {{ item.price | formatPrice}}</span>
            <span class="ms-auto fw-light fs-6" v-else>ціну не знайдено</span>
          </div>
        </div>

      </div>


    </div>


  </div>

</template>

<script>
export default {
  name: "Search",
  data() {
    return {
      search: '',
      results: [],
      minSearchLen: 4,
      timer: null,
      searchStatus: 'Добре.. Давай щось пошукаємо...'
    }
  },
  methods: {
    view: function (alias) {
      this.$router.push({name: 'medicine', params: {alias: alias}})
      this.results = [];
    },
    live: function () {



      if (this.timer) {
        clearTimeout(this.timer);
        this.timer = null;
      }

      if (!this.search.length) {
        this.searchStatus = 'Добре.. Давай щось пошукаємо...';
        this.results = [];
        return;
      }

      this.timer = setTimeout(() => {

        if (this.search.length < this.minSearchLen) {
          this.searchStatus = 'Пошуковий запит має бути більше ' + this.minSearchLen + ' символів';
          return;
        }

        this.searchStatus = 'Шукаю...';
        fetch('/v1/live?s=' + this.search)
            .then(res => res.json())
            .then(res => {
              this.results = res.data;
              this.searchStatus = this.results.length === 0 ? 'Вибачте, але нічого не знайдено!' : 'Ось що я знайшов:';
            });

      }, 200);
    }
  }
}
</script>

<style scoped>

</style>
