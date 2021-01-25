<template>
  <nav>
    <ul class="">
      <li v-for="category in categories">
        <a v-bind:href="'/#/catalog/'+category.alias">{{category.name}}</a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "Category",
  props: {
    categories: Array
  }
}
</script>

<style scoped>

</style>