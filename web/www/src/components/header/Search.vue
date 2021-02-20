<template>

  <div class=" mt-5">

    <div class="w-25 mb-3" style="height: 50px">
      <a href="/">Каталог ліків</a>
    </div>


    <form v-on:submit.prevent="live">
      <div class="mb-3">
        <input class="form-control" id="search" aria-describedby="emailHelp" v-model="search" type="search"
               v-on:keyup="live" v-on:change="live">
        <div class="form-text">
          {{ searchStatus }}
        </div>
      </div>
    </form>


    <div class="position-relative">

      <div v-if="results" class="shadow-lg position-absolute mb-5 p-3 bg-white rounded"
           style="z-index: 1; width: 100%;">


        <div class="row row-cols-xxl-2">


          <div class="col mb-3" v-for="group in results">

            <a class="fs-4  text-body d-block mb-2" v-if="group.alias"
               v-bind:href="'#/catalog/'+group.alias">{{ group.name }}</a>
            <div class="fs-4 text-decoration-none mb-2" v-else>Без категоріі</div>

            <div class="d-flex position-relative  mb-2 p-2" style="cursor: pointer;"
                 v-for="item in group.items" v-on:click="view(item.alias)">

              <img v-if="item.image" v-bind:src="'/images/204x177/'+item.image" class=" flex-shrink-0 me-3" width="40">
              <img v-else src="/img/empty.png" class="img-thumbnail flex-shrink-0 me-3" width="40" height="20">
              <div>
                <h5 class="fw-light fs-6">{{ item.name }}</h5>
                <span class="ms-auto fw-light fs-6" v-if="item.price">від {{ item.price | formatPrice }}</span>
                <span class="ms-auto fw-light fs-6" v-else>ціну не знайдено</span>
              </div>

            </div>


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
      results: null,
      minSearchLen: 4,
      timer: null,
      searchStatus: 'Добре.. Давай щось пошукаємо...'
    }
  },
  methods: {
    view: function (alias) {
      this.$router.push({name: 'medicine', params: {alias: alias}})
      this.results = null;
    },
    live: function () {

      if (this.timer) {
        clearTimeout(this.timer);
        this.timer = null;
      }

      if (!this.search.length) {
        this.searchStatus = 'Добре.. Давай щось пошукаємо...';
        this.results = null;
        return;
      }

      this.timer = setTimeout(() => {

        if (this.search.length < this.minSearchLen) {
          this.searchStatus = 'Пошуковий запит має бути більше ' + this.minSearchLen + ' символів';
          return;
        }

        this.searchStatus = 'Шукаю...';
        fetch('/v1/live?s=' + this._clean(this.search))
            .then(res => res.json())
            .then(res => {
              this.results = this._group(res.data);
              this.searchStatus = this.results.length === 0 ? 'Вибачте, але нічого не знайдено!' : 'Ось що я знайшов:';
            });

      }, 200);
    },
    _group: function (data) {
      return data.reduce(function (r, o) {
        let k = o.tags ? o.tags[0].alias : 'all';
        r[k] = r[k] || {};
        r[k].name = o.tags ? o.tags[0].name : null;
        r[k].alias = o.tags ? o.tags[0].alias : null;
        (r[k].items = r[k].items || []).push(o);
        return r;
      }, {});
    },
    _clean: function (s) {
      let re = /[^а-я\w\s]/gi;
      return s.replace(re, '');
    }
  }
}
</script>

<style scoped>

</style>
