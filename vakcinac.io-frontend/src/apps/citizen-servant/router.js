import Router from 'vue-router';
import Login from '../../components/Login/Login'
import Home from './Home'
import RegisterSluzbenik from './RegisterSluzbenik'
import RegisterZdravstveni from './RegisterZdravstveni'
import AddVakcine from './AddVakcine'
import Izvestaj from './Izvestaj'
import Zahtevi from './Zahtevi'
import DokumentiGradjana from './DokumentiGradjana'
import TextPretraga from './TextPretraga'
import MetaPretraga from './MetaPretraga'
import PojedinacniDokument from '../../components/Documents/PojedinacniDokument'
import VakcineStock from './VakcineStock'
import Potvrda from './Potvrda'

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
    path: '/vakcine/stock',
    name: 'VakcineStock',
    component: VakcineStock
  },
  {
    path: '/izvestaj',
    name: 'Izvestaj',
    component: Izvestaj
  },
  {
    path: '/zahtevi',
    name: 'Zahtevi',
    component: Zahtevi
  },
  {
    path: '/dokumenti/gradjana',
    name: 'DokumentiGradjana',
    component: DokumentiGradjana
  },
  {
    path: '/dokumenti/pretraga',
    name: 'Pretraga',
    component: TextPretraga
  },
  {
    path: '/dokumenti/:path1/:path2/:path3',
    name: 'PojedinacniDokument1',
    component: PojedinacniDokument
  },
  {
    path: '/dokumenti/:path1/:path2',
    name: 'PojedinacniDokument2',
    component: PojedinacniDokument
  },
  {
    path: '/meta-pretraga',
    name: 'MetaPretraga',
    component: MetaPretraga
  },
  {
    path: '/potvrda',
    name: 'Potvrda',
    component: Potvrda
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