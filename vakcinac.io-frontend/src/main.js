import Vue from "vue";
import CitizenApp from "./apps/citizen/App";
import CitizenServantApp from "./apps/citizen-servant/App";
import VueCompositionAPI from "@vue/composition-api";
import vuetify from "@/plugins/vuetify";
import router from "@/plugins/vue-router";

Vue.config.productionTip = false;

Vue.use(VueCompositionAPI);

Vue.config.productionTip = false;

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
