<template>
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item" v-for="(item, index) in items">
        <a href="#" v-if="index !== (items.length - 1)" v-bind:href="item.url">{{ item.title }}</a>
        <span v-else>{{ item.title }}</span>
      </li>
    </ol>
  </nav>
</template>

<script>
export default {
  props: {
    items: Array
  },
  data() {
    return {}
  },
}
</script>