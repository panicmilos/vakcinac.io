import { createRouter, createWebHistory } from "vue-router";

export const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "Page",
      component: () => import("./pages/Page.jsx"),
    },
  ],
});

router.beforeEach((to, from, next) => {
  console.log({to, from});
  next();
});