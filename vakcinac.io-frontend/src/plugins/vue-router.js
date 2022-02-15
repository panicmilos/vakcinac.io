import Vue from 'vue'
import VueRouter from 'vue-router';
import CitizenRouter from '../apps/citizen/router';
import CitizenServantRouter from '../apps/citizen-servant/router';

Vue.use(VueRouter);

const router = 'CitizenServant';

const routers = {
  Citizen: CitizenRouter,
  CitizenServant: CitizenServantRouter
}

export default routers[router];