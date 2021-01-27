<template>
  <div v-if="filters">
    <div v-for="group in filtersGroups">

      <div v-if="group.shown">

        <label><strong>{{ group.name }}</strong></label>
        <div class="form-check" v-for="item in group.items">
          <input class="form-check-input" type="checkbox" v-bind:value="item.id"
                 v-bind:id="'filter-item-checkbox'+item.id" v-model="checked">
          <label class="form-check-label" v-bind:for="'filter-item-checkbox'+item.id">
            {{ item.name }}({{ group.active ? '+' + item.count : item.count }})
          </label>
        </div>


      </div>

    </div>
  </div>
</template>

<script>

class FilterGroup {

  constructor(name, items) {
    this.active = false;
    this.shown = !!items.length
    this.name = name;
    this.items = items;
    this._ids = items.map(v => v.id);
  }

  setState(ids) {
    for (let id of ids) {
      if (this._ids.includes(id)) {
        this.active = true;
        return;
      }
    }
    this.active = false;
  }

}

export default {
  name: "FilterWidget",
  props: {
    filters: Array,
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
    'checked': 'actionFilterChange'
  },
  methods: {
    init: function () {
      this.fetchInputParams();
      this.fetchVocabularies();
    },
    fetchInputParams: function () {
      this.checked = this._keys();
    },
    actionFilterChange: function (n, o) {

      if (o === null) return;

      this.filtersGroups.forEach(g => g.setState(n));

      let request = {name: 'category-catalog'};
      if (this.checked.length) request.query = {key: this.checked.join('-')};

      this.$router.push(request);
    },
    fetchVocabularies: function () {
      fetch("/v1/vocabulary")
          .then(res => res.json())
          .then(res => {
            let grouped = this._group(this.filters, 'tagVocabularyId');
            this.filtersGroups = res.data.map(v => new FilterGroup(v.name, grouped[v.id] ? grouped[v.id] : []));
            this.filtersGroups.forEach(g => g.setState(this._keys()));
          });
    },
    _keys: function () {
      return this.$route.query.key ? this.$route.query.key.split('-').map(v => parseInt(v)) : [];
    },
    _group: function (data, name, exclude = []) {
      return data
          .filter(v => !exclude.includes(v[name]))
          .reduce(function (r, o) {
            (r[o[name]] = r[o[name]] || []).push(o);
            return r;
          }, {})
    }
  }
}
</script>

<style scoped>

</style>