<template>
    <ul class="">
      <li v-for="filter in filters">
        <a v-bind:href="'/#/catalog/'+filter.alias">{{filter.name}}</a>
      </li>
    </ul>
</template>

<script>
const TAG = {
  TYPE_CATEGORY: "CATEGORY"
}
export default {
  name: "Filer",
  props: {
    filters: Array
  }
}
</script>

<style scoped>

</style>