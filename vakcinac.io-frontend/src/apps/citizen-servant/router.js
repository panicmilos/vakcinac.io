import Router from 'vue-router';
import FormExample from './FormExample'
import Test from './Test'

const baseRoutes = [
  {
    path: '/home',
    name: 'home',
    component: FormExample,
  },
  {
    path: '/photos',
    name: 'photos',
    component: Test,
  },
  {
    path: '*',
    redirect: {
      name: 'home',
    },
  },
];

const routes = baseRoutes;
export default new Router({
  routes,
});