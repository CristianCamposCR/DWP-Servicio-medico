import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: "/",
      redirect: "/login",
    },
    {
      path: "/login",
      name: "login",
      component: () => import("../modules/auth/views/LoginView.vue"),
    },
    {
      path: "/areas",
      name: "areas",
      component: () => import("../modules/areas/views/AreasView.vue"),
    },
  ],
});

export default router;
