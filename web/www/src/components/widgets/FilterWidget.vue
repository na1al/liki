<template>
  <div v-if="filters">
    <div v-for="group in filtersGroups">

      <div v-if="group.shown">

        <label><strong>{{ group.vocabulary.name }}</strong></label>
        <div class="form-check" v-for="item in group.items">
          <input class="form-check-input" type="checkbox" v-bind:value="item.id"
                 v-bind:id="'filter-item-checkbox'+item.id" v-model="checked" v-on:change="onFilterClick">
          <label class="form-check-label" v-bind:for="'filter-item-checkbox'+item.id">
            {{ item.name }}({{ group.active && group._counts[item.id] !== item.count ? '' + item.count : item.count }})
          </label>
        </div>


      </div>

    </div>
  </div>
</template>

<script>

class FilterGroup {

  constructor(vocabulary, items) {
    this.active = false;
    this.shown = true;
    this.vocabulary = vocabulary;
    this.items = items.reduce(function (r, o) {
      (r[o.id] = r[o.id] || o);
      return r;
    }, {});
    this._counts = items.reduce(function (r, o) {
      (r[o.id] = r[o.id] || o.count);
      return r;
    }, {});

  }

  updateCounts(keys, counts) {

    // this.active = false;

    for (let index in this._counts) {
      if (this.items.hasOwnProperty(index)) {
        this.items[index].count = !keys ? this._counts[index] : 0;
      }
    }

    if (!keys) {
      return;
    }

    let keyIds = keys.map(v => v.id);

    for (let key of keys) {

      for (let count of counts) {

        let id = count.id;

        if (this.items.hasOwnProperty(id)) {
          if (keyIds.includes(id)) {
            this.items[id].count = count.count;
          } else if (this.items[id].tagVocabularyId === key.tagVocabularyId) {
            this.active = true;
            // this.items[id].count = this._counts[id] - count.count;
            this.items[id].count = count.count;
          } else {
            this.items[id].count = count.count;
          }
        }
      }

    }

  }

}

export default {
  name: "FilterWidget",
  props: {
    filters: Array,
    counts: null,
    tags: Array,
    exclude: Array,
  },
  data() {
    return {
      checked: null,
      filtersGroups: [],
    };
  },
  created() {
    this.init();
  },
  watch: {
    'counts': 'onUpdateCounts',
    '$route': ['onRouteChange']
  },
  methods: {
    init: function () {
      this.fetchInputParams();
      this.fetchVocabularies();
    },
    onRouteChange: function (n, o) {
      this.fetchInputParams();
    },
    onFilterClick: function (n, o) {
      if (o === null) return;
      let request = {name: 'category-catalog'};
      if (this.checked.length) request.query = {key: this.checked.join('-')};
      this.$router.push(request);
    },
    onUpdateCounts: function (n, o) {
      if (o === null) return;
      let grouped = this._group(n, 'tagVocabularyId');
      this.filtersGroups.forEach(g => g.updateCounts(this.tags, grouped[g.vocabulary.id] ? grouped[g.vocabulary.id] : []));
    },
    fetchInputParams: function () {
      this.checked = this._keys();
    },
    fetchVocabularies: function () {
      fetch("/v1/vocabulary")
          .then(res => res.json())
          .then(res => {
            let filters = this._group(this.filters, 'tagVocabularyId');
            let counts = this._group(this.counts, 'tagVocabularyId');
            this.filtersGroups = res.data.filter(v => !this.exclude.includes(v.id)).map(vocabulary => {
              let filter = new FilterGroup(vocabulary, filters[vocabulary.id] ? filters[vocabulary.id] : []);
              filter.updateCounts(this.tags, counts[vocabulary.id] ? counts[vocabulary.id] : []);
              return filter;
            });
          });
    },
    _keys: function () {
      return this.$route.query.key ? this.$route.query.key.split('-').map(v => parseInt(v)) : [];
    },
    _group: function (data, name) {
      return data.reduce(function (r, o) {
        (r[o[name]] = r[o[name]] || []).push(o);
        return r;
      }, {});
    },
  }
}
</script>

<style scoped>

</style>