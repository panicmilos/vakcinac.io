import Router from 'vue-router';
import Login from '../../components/Login/Login'
import Home from './Home'
import RegisterDomaci from './RegisterDomaci'
import RegisterStrani from './RegisterStrani'

const baseRoutes = [
  {
    path: '/',
    name: 'login',
    component: Login,
  },
  {
    path: '/home',
    name: 'Home',
    component: Home,
  },
  {
    path: '/register-domaci',
    name: 'RegisterDomaci',
    component: RegisterDomaci,
  },
  {
    path: '/register-strani',
    name: 'RegisterStrani',
    component: RegisterStrani
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