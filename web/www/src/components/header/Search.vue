<template>

  <div class=" mt-5">

    <div class="w-25 mb-3">

      <a href="/">
        <svg class="logo__image" title="Сервис поиска врачей Doc.ua" viewBox="0 0 383 83" fill="none"
             xmlns="http://www.w3.org/2000/svg">
          <path
              d="M156.94 41.0636C156.94 59.9371 143.732 65.814 126.992 65.814H110.105V16.402H126.992C143.75 16.402 156.94 22.2611 156.94 41.0636ZM122.21 26.2205V56.0132H127.232C136.135 56.0132 144.174 54.1667 144.174 41.0813C144.174 28.067 136.116 26.2205 127.232 26.2205H122.21Z"
              fill="#0957C3"></path>
          <path
              d="M160.343 41.1344C160.343 25.7232 171.141 15.5674 186.906 15.5674C202.578 15.5674 213.468 25.7232 213.468 41.1344C213.468 56.5457 202.597 66.7015 186.906 66.7015C171.141 66.7015 160.343 56.5457 160.343 41.1344ZM200.665 41.1344C200.665 30.6413 194.374 25.1905 186.887 25.1905C179.4 25.1905 173.109 30.6413 173.109 41.1344C173.109 51.6276 179.4 57.0784 186.887 57.0784C194.374 57.0961 200.665 51.6453 200.665 41.1344Z"
              fill="#0957C3"></path>
          <path
              d="M242.536 66.7024C226.568 66.7024 216.892 56.8839 216.892 41.082C216.892 25.4754 226.918 15.5859 242.885 15.5859C255.743 15.5859 264.775 23.6289 266.541 34.8678H254.676C253.334 28.1209 248.183 25.1914 242.462 25.1914C235.251 25.1914 229.677 30.9795 229.677 41.082C229.677 51.5042 235.251 57.097 242.609 57.097C247.833 57.097 253.628 54.9131 255.044 47.4205H266.909C265.07 58.8547 255.467 66.7024 242.536 66.7024Z"
              fill="#0957C3"></path>
          <path d="M269.941 53.6873H283.075V65.8139H269.941V53.6873Z" fill="#0957C3"></path>
          <path
              d="M299.172 45.9107C299.172 53.137 300.938 57.1496 309.345 57.1496C317.825 57.1496 319.297 52.5155 319.297 45.2183V16.3844H331.364V44.2595C331.364 59.4577 325.294 66.6839 309.327 66.6839C294.003 66.6839 287.087 60.9669 287.087 44.6679V16.3844H299.172V45.9107Z"
              fill="#0957C3"></path>
          <path
              d="M363.648 16.402L383 65.814H369.866L366.757 57.0253H346.982L343.947 65.814H331.089L350.367 16.402H363.648ZM363.519 47.5441L356.952 28.7239L350.312 47.5441H363.519Z"
              fill="#0957C3"></path>
          <path d="M11.046 12.174L1.01135 28.949L74.5937 69.954L84.6283 53.1791L11.046 12.174Z" fill="#FE5000"></path>
          <path d="M52.8611 0.067627H32.7917V82.0776H52.8611V0.067627Z" fill="#0957C3"></path>
          <path d="M0.997777 53.173L11.0324 69.9479L84.6147 28.9429L74.5801 12.168L0.997777 53.173Z"
                fill="#47D7AC"></path>
        </svg>

      </a>

    </div>


    <form v-on:submit.prevent="live">
      <div class="mb-3">
        <input class="form-control" id="search" aria-describedby="emailHelp" v-model="search" type="search"
               v-on:keyup="live"  v-on:change="live">
        <div class="form-text">
          {{ searchStatus }}
        </div>
      </div>
    </form>


    <div class="position-relative">

      <div v-if="results" class="shadow-lg position-absolute mb-5 p-3 bg-white rounded" style="z-index: 1; width: 100%;">


        <div class="row row-cols-xxl-2">


          <div class="col mb-3" v-for="group in results">

            <a class="fs-4  text-body d-block mb-2" v-if="group.alias" v-bind:href="'#/catalog/'+group.alias">{{ group.name }}</a>
            <div class="fs-4 text-decoration-none mb-2" v-else>Без категоріі</div>

            <div class="d-flex position-relative  mb-2 p-2" style="cursor: pointer;"
                 v-for="item in group.items" v-on:click="view(item.alias)">

              <img v-if="item.image" v-bind:src="'/images/204x177/'+item.image" class=" flex-shrink-0 me-3" width="40">
              <img v-else src="/empty.png" class="img-thumbnail flex-shrink-0 me-3" width="40" height="20">
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
