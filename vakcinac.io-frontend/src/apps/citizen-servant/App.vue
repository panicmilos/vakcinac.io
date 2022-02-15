<template>
  <v-app>
    <sidebar
      :items="items"
      title="vakcinac.io"
      subtitle="by PZLL"
      userFullName="Meetar Meereech"
    />
    <v-content>
      <v-fade-transition mode="out-in">
        <router-view></router-view>
      </v-fade-transition>
    </v-content>
  </v-app>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import Sidebar from "../../components/Sidebar.vue";
import FormExample from "./FormExample.vue";

export default defineComponent({
  name: "App",
  components: {
    Sidebar,
    FormExample,
  },
  data: () => ({
    items: [
      { title: 'Dashboard', icon: 'mdi-view-dashboard', href: '/home' },
      { title: 'Photos', icon: 'mdi-image', href: '/photos' },
      { title: 'About', icon: 'mdi-help-box', href: '/about' },
    ],
  }),
});
</script>

<style>
.logo {
  flex: 0 1 auto;
}
</style>
