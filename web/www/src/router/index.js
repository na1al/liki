import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import Medicine from '@/components/Medicine'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Index
    },
    {
      path: '/m/:alias',
      name: 'medicine',
      component: Medicine
    }
  ]
})
