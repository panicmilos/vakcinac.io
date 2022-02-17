import Router from 'vue-router';
import Login from '../../components/Login/Login'
import Test from './Test'

const baseRoutes = [
  {
    path: '/',
    name: 'login',
    component: Login,
  },
  {
    path: '/photos',
    name: 'photos',
    component: Test,
  },
  {
    path: '*',
    redirect: {
      name: 'login',
    },
  },
];

const routes = baseRoutes;
export default new Router({
  routes,
});