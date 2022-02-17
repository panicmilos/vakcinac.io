import Vue from "vue";
import CitizenApp from "./apps/citizen/App";
import CitizenServantApp from "./apps/citizen-servant/App";
import VueCompositionAPI from "@vue/composition-api";
import vuetify from "@/plugins/vuetify";
import router from "@/plugins/vue-router";
import axios from "axios";

Vue.config.productionTip = false;

Vue.use(VueCompositionAPI);

Vue.config.productionTip = false;

axios.interceptors.request.use(config => {
  const token = localStorage.getItem('jwt') || '';
  config.headers['Content-Type'] = 'text/xml';
  config.headers.Accept = 'text/xml';
  if(token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

const app = 'CitizenServant';

const apps = {
  Citizen: CitizenApp,
  CitizenServant: CitizenServantApp
}

new Vue({
  vuetify,
  router,
  render: (h) => h(apps[app])
}).$mount("#app");
