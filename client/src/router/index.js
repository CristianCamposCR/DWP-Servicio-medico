import Vue from "vue";
import VueRouter from "vue-router";
import managementRoute from "./management-route";
import { jwtDecode } from "jwt-decode";

Vue.use(VueRouter);
const DEFAULT_TITLE = "CIMI";

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
            import("../modules/public/doctors/views/DoctorsView.vue"),
          meta: {
            title: "Doctores",
            requireAuth: false,
          },
        },
        {
          path: "areas",
          name: "public areas",
          component: () => import("../modules/public/views/AreasView.vue"),
          meta: {
            title: "Areas",
            requireAuth: false,
          },
        },
        {
          path: "specialities",
          name: "public specialities",
          component: () =>
            import("../modules/public/views/SpecialitiesView.vue"),
          meta: {
            title: "Especialidades",
            requireAuth: false,
          },
        },
        ...managementRoute,
      ],
    },
  ],
});

router.beforeEach((to, from, next) => {
  let hasPermission = false;
  if (localStorage.token) {
    const rl = jwtDecode(localStorage.token);
    const roles = rl.roles;
    const role = roles[0].authority;
    if (role) {
      if (to.matched.some((route) => route.meta.requireAuth)) {
        const allowedRoles = to.meta.role;
        allowedRoles.includes(role)
          ? (hasPermission = true)
          : (hasPermission = false);
        hasPermission ? next() : next("/login");
      } else {
        next();
      }
    }
  } else if (!to.matched.some((noAuth) => noAuth.meta.requireAuth)) {
    next();
  } else {
    next("/login");
  }
});

router.afterEach((to, from) => {
  Vue.nextTick(() => {
    document.title = to.meta?.title || DEFAULT_TITLE;
  });
});

export default router;
