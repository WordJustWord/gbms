import Vue from 'vue';
import VueRouter from 'vue-router';

import UserList from "../views/user/userlist";

Vue.use(VueRouter);


// component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
const routes = [
  {
    path: '/',
    name: 'home',
  },
  {
    path: '/about',
    name: 'about',
  },
  {
    path: '/user',
    name: 'user',
    redirect: { name: "userlist" }
  },
  {
    path: '/user/userlist',
    name: 'userlist',
    component: UserList
  }
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router;
