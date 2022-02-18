<template>
  <v-app>
    <sidebar
      :items="items"
      title="vakcinac.io"
      subtitle="by PZLL"
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
import { isLoggedIn, ROLES } from "../../utils/auth";

export default defineComponent({
  name: "App",
  components: {
    Sidebar,
  },
  data: () => ({
    items: [
      { title: 'Login', icon: 'mdi-lock', href: '/' },
      { title: 'Početna', icon: 'mdi-view-dashboard', href: '/home', roles: [ROLES.DomaciGradjanin, ROLES.StraniGradjanin] },
      { title: 'Registracija Domaćeg', icon: 'mdi-account', href: '/register-domaci' },
      { title: 'Registracija Stranog', icon: 'mdi-account', href: '/register-strani' },
      { title: 'Izjava Interesovanja', icon: 'mdi-help-box', href: '/izjava-interesovanja', roles: [ROLES.DomaciGradjanin, ROLES.StraniGradjanin] },
      { title: 'Saglasnost', icon: 'mdi-help-box', href: '/saglasnost', roles: [ROLES.DomaciGradjanin, ROLES.StraniGradjanin] },
      { title: 'Zahtev za Digitalni Sertifikat', icon: 'mdi-help-box', href: '/zahtev-zeleni', roles: [ROLES.DomaciGradjanin, ROLES.StraniGradjanin] },
      { title: 'Moji Dokumenti', icon: 'mdi-help-box', href: '/dokumenti', roles: [ROLES.DomaciGradjanin, ROLES.StraniGradjanin] },
    ],
    showToolbar: isLoggedIn()
  }),
});
</script>

<style>
</style>
