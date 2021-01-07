<template>

  <div class="container mt-5">

    <h1>Каталог ліків Украіни</h1>

    <form v-on:submit.prevent="live">
      <div class="mb-3">
        <label for="search" class="form-label">Пошук</label>
        <input class="form-control" id="search" aria-describedby="emailHelp" v-model="search" type="search"
               v-on:keyup="live">
        <div class="form-text">
          {{ searchStatus }}
        </div>
      </div>
    </form>

    <div v-for="result in results" class="result mb-2">
      <img v-if="result.image" v-bind:src="'/images/204x177/'+result.image" class="img-thumbnail mr-2" width="80">
      <img v-else src="/empty.png" class="img-thumbnail mr-2" width="80">
      {{ result.name }}<br/>
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
  methods() {
    return {
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
}
</script>

<style scoped>

</style>
