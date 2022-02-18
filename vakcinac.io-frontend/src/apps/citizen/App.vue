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
      { title: 'About', icon: 'mdi-help-box', href: '/about' },
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
.logo {
  flex: 0 1 auto;
}
</style>
