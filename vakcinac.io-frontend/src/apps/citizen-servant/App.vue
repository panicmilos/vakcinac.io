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
import { isLoggedIn } from "../../utils/auth"

export default defineComponent({
  name: "App",
  components: {
    Sidebar,
  },
  data: () => ({
    items: [
      { title: 'Home', icon: 'mdi-view-dashboard', href: '/home' },
      { title: 'Register Sluzbenik', icon: 'mdi-account', href: '/register-sluzbenik' },
      { title: 'Register Zdravstveni Radnik', icon: 'mdi-account', href: '/register-zdravstveni' },
      { title: 'Add Vakcine', icon: 'mdi-needle', href: '/add-vakcine' },
      { title: 'Izvestaj', icon: 'mdi-calendar', href: '/izvestaj' },
      { title: 'Zahtevi', icon: 'mdi-calendar', href: '/zahtevi' },
      { title: 'Dokumenti Gradjana', icon: 'mdi-calendar', href: '/dokumenti/gradjana' },
      { title: 'Pretraga', icon: 'mdi-calendar', href: '/dokumenti/pretraga' }
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
