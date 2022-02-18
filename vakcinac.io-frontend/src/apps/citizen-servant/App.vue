<template>
  <v-app>
    <sidebar
      :items="items"
      title="vakcinac.io"
      subtitle="by PZLL"
      v-if="showToolbar"
      @logout="handleLoggedIn"
    />
    <v-main>
      <v-fade-transition mode="out-in">
        <router-view></router-view>
      </v-fade-transition>
    </v-main>
  </v-app>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import Sidebar from "../../components/Sidebar.vue";
import { isLoggedIn, ROLES } from "../../utils/auth"

export default defineComponent({
  name: "App",
  components: {
    Sidebar,
  },
  data: () => ({
    items: [
      { title: 'Početna', icon: 'mdi-view-dashboard', href: '/home', roles: [ROLES.Sluzbenik, ROLES.ZdravstveniRadnik] },
      { title: 'Registracija Službenika', icon: 'mdi-account', href: '/register-sluzbenik', roles: [ROLES.Sluzbenik] },
      { title: 'Registracija Zdravstvenog Radnika', icon: 'mdi-account', href: '/register-zdravstveni', roles: [ROLES.Sluzbenik] },
      { title: 'Kreiranje Vakcine', icon: 'mdi-needle', href: '/add-vakcine', roles: [ROLES.Sluzbenik] },
      { title: 'Dopuna Zaliha Vakcine', icon: 'mdi-needle', href: '/vakcine/stock', roles: [ROLES.Sluzbenik] },
      { title: 'Potvrda Vakcinacije', icon: 'mdi-candy', href: '/potvrda', roles: [ROLES.ZdravstveniRadnik] },
      { title: 'Zahtevi za Digitalni Sertifikat', icon: 'mdi-file-document', href: '/zahtevi', roles: [ROLES.Sluzbenik] },
      { title: 'Izveštaj', icon: 'mdi-calendar', href: '/izvestaj', roles: [ROLES.Sluzbenik] },
      { title: 'Dokumenti Građana', icon: 'mdi-file-document-multiple-outline', href: '/dokumenti/gradjana', roles: [ROLES.Sluzbenik] },
      { title: 'Tekstualna Pretraga', icon: 'mdi-map-search', href: '/dokumenti/pretraga', roles: [ROLES.Sluzbenik] },
      { title: 'Meta Pretraga', icon: 'mdi-magnify', href: '/meta-pretraga', roles: [ROLES.Sluzbenik] },
    ],
    showToolbar: isLoggedIn()
  }),
  watch: {
    $route() {
      this.handleLoggedIn();
    }
  },
  methods: {
    handleLoggedIn() {
      this.showToolbar = isLoggedIn();
    }
  }
});
</script>

<style>
</style>
