import Vue from "vue";
import VueRouter from "vue-router";
import managmentRoute from "./managment-route";

Vue.use(VueRouter);

const router = new VueRouter({
  mode: "history",
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: "/",
      redirect: { name: "landing" },
    },
    {
      path: "/",
      component: { render: (c) => c("router-view") },
      children: [
        {
          path: "/landing",
          name: "landing",
          component: () =>
            import("../modules/public/views/LandingPageView.vue"),
        },
        {
          path: "/login",
          name: "login",
          component: () => import("../modules/auth/views/LoginView.vue"),
          meta: {
            title: "Iniciar sesión",
            requireAuth: false,
          },
        },
        ...managmentRoute,
        {
          path: "/profile",
          name: "profile",
          component: () => import("../modules/profile/views/Profile.vue"),
        },
      ],
    },
  ],
});

export default router;
