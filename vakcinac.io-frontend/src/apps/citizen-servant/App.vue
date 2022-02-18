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
import FormExample from "./FormExample.vue";
import { isLoggedIn } from "../../utils/auth"

export default defineComponent({
  name: "App",
  components: {
    Sidebar,
    FormExample,
  },
  data: () => ({
    items: [
      { title: 'Home', icon: 'mdi-view-dashboard', href: '/home' },
      { title: 'Register Sluzbenik', icon: 'mdi-image', href: '/register-sluzbenik' },
      { title: 'Register Zdravstveni Radnik', icon: 'mdi-image', href: '/register-zdravstveni' },
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
