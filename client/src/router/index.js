import Vue from "vue";
import VueRouter from "vue-router";
import managementRoute from "./management-route";

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
          props: true,
          name: "login",
          component: () => import("../modules/auth/views/LoginView.vue"),
          meta: {
            title: "Iniciar sesión",
            requireAuth: false,
          },
        },
        {
          path: "doctors",
          name: "doctors",
          component: () =>
            import("../modules/public/views/DoctorsView.vue"),
          meta: {
            title: "Doctores",
            requireAuth: false
          },
          
        },
        {
          path: "areas",
          name: "public areas",
          component: () =>
            import("../modules/public/views/AreasView.vue"),
          meta: {
            title: "Areas",
            requireAuth: false
          },
          
        },
        {
          path: "specialities",
          name: "public specialities",
          component: () =>
            import("../modules/public/views/SpecialitiesView.vue"),
          meta: {
            title: "Especialidades",
            requireAuth: false
          },
          
        },
        ...managementRoute,
      ],
    },
  ],
});


export default router;

