import Vue from 'vue'
import VueRouter from 'vue-router';
import CitizenRouter from '../apps/citizen/router';
import CitizenServantRouter from '../apps/citizen-servant/router';
import { app } from '../cfg';

Vue.use(VueRouter);

const routers = {
  Citizen: CitizenRouter,
  CitizenServant: CitizenServantRouter
}

export default routers[app];