<template>

  <div v-if="data">

    <div v-for="(items, index) in getFilters()">
      <label><strong>{{ getVocabularyName(index) }}</strong></label>

      <div class="form-check" v-for="item in items">
        <input class="form-check-input" type="checkbox" v-bind:value="item.id"
               v-bind:id="'filter-item-checkbox'+item.id" v-model="activeFilterId">
        <label class="form-check-label" v-bind:for="'filter-item-checkbox'+item.id">
          {{ item.name }}
        </label>
      </div>

    </div>


  </div>


</template>

<script>
const TAG = {
  TYPE_CATEGORY: 1
}
export default {
  name: "Filer",
  props: {
    data: Object
  },
  data() {
    return {
      activeFilterId: []
    };
  },
  watch: {
    'activeFilterId': 'actionFilterChange'
  },
  methods: {
    actionFilterChange: function () {
      let request = {name: 'category-catalog'};

      if (this.activeFilterId.length) {
        request.query = {key: this.activeFilterId.join('-')};
      }

      this.$router.push(request);
    },
    getFilters: function () {
      return this.data.filter
          .filter(filter => filter.tagVocabularyId !== TAG.TYPE_CATEGORY)
          .reduce(function (r, o) {
            (r[o.tagVocabularyId] = r[o.tagVocabularyId] || []).push(o);
            return r;
          }, {})
    },
    getVocabularyName: function (id) {
      return this.data.vocabularies.find(v => v.id === parseInt(id)).name;
    },
  }
}
</script>

<style scoped>

</style>