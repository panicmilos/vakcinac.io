<template>
<div>
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
      <v-list-item
        v-for="item in items"
        :key="item.title"
        link
        :to="item.href"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
  <v-app-bar>
    <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>

    <v-spacer></v-spacer>

    <v-toolbar-title>{{userFullName}}</v-toolbar-title>

    <v-btn icon>
      <v-icon>mdi-logout</v-icon>
    </v-btn>
  </v-app-bar>
</div>
</template>

<script>
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
      default: "Meetar Meereech"
    },
    items: {
      type: Array,
      default: () => []
    }
  },
  data: () => ({
    drawer: false
  })
}
</script>

<style>

</style>