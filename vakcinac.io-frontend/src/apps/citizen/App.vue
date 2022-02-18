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
import { isLoggedIn } from "../../utils/auth";

export default defineComponent({
  name: "App",
  components: {
    Sidebar,
  },
  data: () => ({
    items: [
      { title: 'Home', icon: 'mdi-view-dashboard', href: '/home' },
      { title: 'Register Domaci', icon: 'mdi-image', href: '/register-domaci' },
      { title: 'Register Strani', icon: 'mdi-image', href: '/register-strani' },
      { title: 'Izjava Interesovanja', icon: 'mdi-help-box', href: '/izjava-interesovanja' },
      { title: 'Saglasnost', icon: 'mdi-help-box', href: '/saglasnost' },
      { title: 'ZahtevZeleni', icon: 'mdi-help-box', href: '/zahtev-zeleni' },
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
