<template>
  <div class="mb-4">
    <label class="me-2">Катагорія:</label><span class="badge rounded-pill bg-primary"
                                                v-if="category">{{ category.name }}</span>
    <hr/>
    <label class="me-2">Фільтри:</label>
    <span class="badge rounded-pill bg-success me-2" style="cursor: pointer;" v-for="tag in tags"
          v-on:click="onFilterRemoveClick(tag)">{{ tag.name }}<i class="btn-close ms-2"/></span>
  </div>
</template>

<script>

export default {
  name: "FilterHeaderWidget",
  props: {
    category: Object,
    tags: Array
  },
  data() {
    return {};
  },
  methods: {
    init: function () {
    },
    onFilterRemoveClick: function (tag) {
      let request = {name: 'category-catalog'};
      let keys = this._keys();
      let index = keys.indexOf(tag.id);
      if (index !== -1) keys.splice(index, 1);
      if (keys.length) request.query = {key: keys.join('-')};
      this.$router.push(request);
    },
    _keys: function () {
      return this.$route.query.key ? this.$route.query.key.split('-').map(v => parseInt(v)) : [];
    },
  }
}
</script>

<style scoped>

</style>