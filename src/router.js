import Vue from 'vue'
import Router from 'vue-router'
import Schedule from './components/schedule/Schedule.vue';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
        component: Schedule
    }
  ]
})
