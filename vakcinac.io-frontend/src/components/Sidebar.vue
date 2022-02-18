<template>
<div class="sbar">
  <v-navigation-drawer
    v-model="drawer"
    absolute
    temporary
  >
    <v-list-item>
      <v-list-item-content>
        <v-list-item-title class="text-h6">
          {{title}}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{subtitle}}
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>

    <v-divider></v-divider>

    <v-list
      dense
      nav
    >
      <template
        v-for="item in items"
      >
        <v-list-item
          :key="item.title"
          link
          :to="item.href"
          v-if="authItem(item)"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
  </v-navigation-drawer>
  <v-app-bar>
    <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>

    <v-spacer></v-spacer>

    <v-toolbar-title>{{userFullName}}</v-toolbar-title>

    <v-btn v-if="role" icon @click.stop="logout">
      <v-icon>mdi-logout</v-icon>
    </v-btn>
  </v-app-bar>
</div>
</template>

<script>
import { getRole } from '../utils/auth';

export default {
  props: {
    title: {
      type: String,
      default: "vakcinac.io"
    },
    subtitle: {
      type: String,
      default: "by PZLL"
    },
    userFullName: {
      type: String,
      default: ""
    },
    items: {
      type: Array,
      default: () => []
    }
  },
  data: () => ({
    drawer: false,
    role: getRole(),
  }),
  watch: {
    $route() {
      this.role = getRole();
    },
  },
  mounted() {
    this.role = getRole();
  },
  methods: {
    logout() {
      localStorage.removeItem('jwt');
      this.$router.push('/');
      this.$emit('logout');
    },
    authItem(item) {
      return (!item.roles && !this.role) || item?.roles?.includes(this.role);
    }
  }
}
</script>

<style scoped>
.sbar { 
  margin-bottom: 5em;
}
</style>