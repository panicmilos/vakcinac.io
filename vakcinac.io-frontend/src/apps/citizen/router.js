import Router from 'vue-router';
import Login from '../../components/Login/Login'
import Home from './Home'
import RegisterDomaci from './RegisterDomaci'
import RegisterStrani from './RegisterStrani'
import IzjavaInteresovanja from './IzjavaInteresovanja'
import Saglasnost from './Saglasnost'
import ZahtevZeleni from './ZahtevZeleni'
import DokumentiGradjana from './DokumentiGradjana'
import DokumentDownloader from './DokumentDownloader'
import PojedinacniDokument from '../../components/Documents/PojedinacniDokument'

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
    path: '/izjava-interesovanja',
    name: 'IzjavaInteresovanja',
    component: IzjavaInteresovanja
  },
  {
    path: '/saglasnost',
    name: 'Saglasnost',
    component: Saglasnost
  },
  {
    path: '/zahtev-zeleni',
    name: 'ZahtevZeleni',
    component: ZahtevZeleni
  },
  {
    path: '/dokumenti',
    name: 'Dokumenti',
    component: DokumentiGradjana
  },
  {
    path: '/dokumenti/:path1/:path2',
    name: 'PojedinacniDokument1',
    component: PojedinacniDokument
  },
  {
    path: '/dokumenti/:path1/:path2/:path3',
    name: 'PojedinacniDokument2',
    component: PojedinacniDokument
  },
  {
    path: '/dokumenti/:path1/:path2/download',
    name: 'DokumentDownloader1',
    component: DokumentDownloader
  },
  {
    path: '/dokumenti/:path1/:path2/:path3/download',
    name: 'DokumentDownloader2',
    component: DokumentDownloader
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