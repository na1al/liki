<template>
  <div id="app">
    <Search/>
    <router-view/>
  </div>
</template>

<script>
import Search from './components/header/Search'

export default {
  name: 'App',
  components: {  Search },
}
</script>

