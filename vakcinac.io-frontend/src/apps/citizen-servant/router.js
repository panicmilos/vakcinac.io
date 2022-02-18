import Router from 'vue-router';
import Login from '../../components/Login/Login'
import Home from './Home'
import RegisterSluzbenik from './RegisterSluzbenik'
import RegisterZdravstveni from './RegisterZdravstveni'

const baseRoutes = [
  {
    path: '/',
    name: 'login',
    component: Login,
  },
  {
    path: '/Home',
    name: 'Home',
    component: Home,
  },
  {
    path: '/register-sluzbenik',
    name: 'RegisterSluzbenik',
    component: RegisterSluzbenik,
  },
  {
    path: '/register-zdravstveni',
    name: 'RegisterZdravstveni',
    component: RegisterZdravstveni
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