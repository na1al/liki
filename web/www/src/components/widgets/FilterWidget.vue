<template>
  <div v-if="filters">
    <div v-for="group in filtersGroups">

      <div v-if="group.shown">

        <label><strong>{{ group.vocabulary.name }}</strong></label>
        <div class="form-check" v-for="item in group.items">
          <input class="form-check-input" type="checkbox" v-bind:value="item.id"
                 v-bind:id="'filter-item-checkbox'+item.id" v-model="checked" v-on:change="onFilterClick">
          <label class="form-check-label" v-bind:for="'filter-item-checkbox'+item.id">
            {{ item.name }}({{ group.active && group._counts[item.id] !== item.count ? '+' + item.count : item.count }})
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

    this.active = false;

    for (let index in this._counts) {
      if (this.items.hasOwnProperty(index)) {
        this.items[index].count = this._counts[index];
      }
    }

    if (!keys) {

      return;
    }

    let uniq = {};
    // let reduceKeys = keys.filter(function (item) {
    //   return uniq.hasOwnProperty(item.tagVocabularyId) ? false : (uniq[item.tagVocabularyId] = true);
    // });


    let reduceKeys = keys;

    for (let key of reduceKeys) {
      for (let id in this.items) {
        if (this.items.hasOwnProperty(id)) {
          if (this.items[id].tagVocabularyId !== key.tagVocabularyId) {
            this.items[id].count = 0;
          }
        }
      }
    }


    let keyIds = keys.map(v => v.id);

    for (let key of reduceKeys) {

      for (let count of counts) {

        let id = count.id;

        if (this.items.hasOwnProperty(id)) {
          if (keyIds.includes(id)) {
            this.items[id].count = count.count;
          } else if (this.items[id].tagVocabularyId === key.tagVocabularyId) {
            this.active = true;
            this.items[id].count = this._counts[id] - count.count;
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
    keys: Array
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


      let keys = [];

      if (this.keys) {
        let sorting = this._keys();
        keys = this.keys.map(function (item) {
          let n = sorting.indexOf(item.id);
          sorting[n] = '';
          return [n, item]
        }).sort().map(function (j) {
          return j[1]
        });
      }

      this.filtersGroups.forEach(g => g.updateCounts(keys, grouped[g.vocabulary.id] ? grouped[g.vocabulary.id] : []));
    },
    fetchInputParams: function () {
      this.checked = this._keys();
    },
    fetchVocabularies: function () {
      fetch("/v1/vocabulary")
          .then(res => res.json())
          .then(res => {
            let grouped = this._group(this.filters, 'tagVocabularyId');
            this.filtersGroups = res.data.map(vocabulary => new FilterGroup(vocabulary, grouped[vocabulary.id] ? grouped[vocabulary.id] : {}));
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