<template>
  <nav aria-label="Page navigation ">
    <ul class="pagination mt-5 mb-5">
      <li class="page-item" v-if="currentPage > 0">
        <a class="page-link" v-bind:href="'/#/catalog?page='+(currentPage-1)" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li class="page-item disabled" v-else>
        <span aria-hidden="true" class="page-link">&laquo;</span>
      </li>
      <li class="page-item" v-for="n in pages()" v-bind:class="{ active: n === currentPage }">
        <a class="page-link" v-bind:href="'/#/catalog?page='+n" v-if="n !== currentPage">{{ n+1 }}</a>
        <span class="page-link" v-else>{{ n+1 }}</span>
      </li>
      <li class="page-item" v-if="currentPage < totalPages">
        <a class="page-link" v-bind:href="'/#/catalog?page='+(currentPage+1)" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>
      </li>
      <li class="page-item disabled" v-else>
        <span aria-hidden="true" class="page-link">&raquo;</span>
      </li>
    </ul>
    {{ totalPages }}
    {{ currentPage }}
  </nav>
</template>

<script>
export default {
  props: {
    currentPage: {
      type: Number,
      default: 0
    },
    totalPages: {
      type: Number,
      required: true
    },
    pagerLen: {
      type: Number,
      default: 10
    }
  },
  data() {
    return {}
  },
  methods: {
    pages: function () {

      let pager = [];
      let start = Math.max(this.currentPage - 5, 0);
      let end = Math.min(start + this.pagerLen, this.totalPages);

      for (let i = start; i < end; i++) {
        pager.push(i);
      }

      console.log(pager)

      return pager;
    }
  }
}
</script>

<style scoped>
.page-link:focus {
  z-index: 3;
  background-color: transparent;
  outline: none;
  box-shadow: none;
}
</style>