import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import Users from '@/views/Users.vue';
import Cars from '@/views/CarsView.vue';

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/users',
    name: 'Users',
    component: Users
  },
  {
    path: '/cars',
    name: 'Cars',
    component: Cars
  },
]

const router = new VueRouter({
  routes
})

export default router
