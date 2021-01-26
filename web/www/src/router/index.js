import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import Medicine from '@/components/Medicine'
import Catalog from '@/components/Catalog'

Vue.use(Router)

export default new Router({
 // mode: 'history',
  routes: [
    {
      path: '/',
      component: Index
    },
    {
      path: '/catalog',
      name: 'catalog',
      component: Catalog
    },
    {
      path: '/catalog/:alias',
      name: 'category-catalog',
      component: Catalog
    },
    {
      path: '/m/:alias',
      name: 'medicine',
      component: Medicine
    }
  ]
})
