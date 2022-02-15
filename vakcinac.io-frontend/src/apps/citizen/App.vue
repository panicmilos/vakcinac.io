<template>
  <div data-app>
    <sidebar
      :items="items"
      title="vakcinac.io"
      subtitle="by PZLL"
      userFullName="Meetar Meereech"
    />
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import Sidebar from "../../components/Sidebar.vue";

export default defineComponent({
  name: "App",
  components: {
    Sidebar,
  },
  data: () => ({
    items: [
      { title: 'Dashboard', icon: 'mdi-view-dashboard' },
      { title: 'Photos', icon: 'mdi-image' },
      { title: 'About', icon: 'mdi-help-box' },
    ],
  }),
});
</script>

<style>
.logo {
  flex: 0 1 auto;
}
</style>
