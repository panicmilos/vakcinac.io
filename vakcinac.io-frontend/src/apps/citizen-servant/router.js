import Router from 'vue-router';
import Login from '../../components/Login/Login'
import Home from './Home'
import RegisterSluzbenik from './RegisterSluzbenik'
import RegisterZdravstveni from './RegisterZdravstveni'
import AddVakcine from './AddVakcine'
import Izvestaj from './Izvestaj'

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
    path: '/add-vakcine',
    name: 'AddVakcine',
    component: AddVakcine
  },
  {
    path: '/izvestaj',
    name: 'Izvestaj',
    component: Izvestaj
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