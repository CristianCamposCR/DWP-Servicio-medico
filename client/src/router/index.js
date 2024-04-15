import Vue from "vue";
import VueRouter from "vue-router";
import managementRoute from "./management-route";
import patientRoute from "./patient-router";
import publicRoute from "./public-router";
import { jwtDecode } from "jwt-decode";
import doctorRoute from "./doctor-route";
import { ERoles } from "../kernel/types";

Vue.use(VueRouter);
const DEFAULT_TITLE = "CIMI";

const router = new VueRouter({
  mode: "history",
  base: import.meta.env.BASE_URL,
  routes: [
    {
      path: "/",
      redirect: "/cimi",
    },
    {
      path: "/",
      component: { render: (c) => c("router-view") },
      children: [
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
        ...managementRoute,
        ...doctorRoute,
        ...patientRoute,
        ...publicRoute,
      ],
    },
  ],
});

router.beforeEach((to, from, next) => {
  if (localStorage.token) {
    const rl = jwtDecode(localStorage.token);
    const roles = rl.roles;
    const role = roles[0].authority;
    if (
      (role === ERoles.ADMIN &&
        to.matched.some((route) => route.path === "/cimi")) ||
      (role === ERoles.ADMIN &&
        to.matched.some((route) => route.path === "/landing")) ||
      (role === ERoles.ADMIN &&
        to.matched.some((route) => route.path === "/login"))
    )
      next("/management");
    if (
      (role === ERoles.DOCTOR &&
        to.matched.some((route) => route.path === "/cimi")) ||
      (role === ERoles.DOCTOR &&
        to.matched.some((route) => route.path === "/landing")) ||
      (role === ERoles.DOCTOR &&
        to.matched.some((route) => route.path === "/login"))
    )
      next("/doctor");
    if (
      (role === ERoles.PATIENT &&
        to.matched.some((route) => route.path === "/cimi")) ||
      (role === ERoles.PATIENT &&
        to.matched.some((route) => route.path === "/login"))
    )
      next("/patient");
    if (role && to.matched.some((route) => route.meta.requireAuth)) {
      const allowedRoles = to.meta.role;
      if (allowedRoles.includes(role)) {
        next();
        return;
      }
      next("/login");
      return;
    }
    next();
    return;
  }
  if (!to.matched.some((noAuth) => noAuth.meta.requireAuth)) {
    next();
    return;
  }
  next("/login");
});

router.afterEach((to, from) => {
  Vue.nextTick(() => {
    document.title = to.meta?.title || DEFAULT_TITLE;
  });
});

export default router;
