export default [
  {
    path: "/cimi",
    component: () => import("../views/PublicView.vue"),
    redirect: { name: "landing" },
    name: "cimi",
    children: [
      {
        path: "landing",
        name: "landing",
        component: () => import("../modules/public/views/LandingPageView.vue"),
        meta: {
          title: "Inicio",
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
        component: () => import("../modules/public/areas/views/AreasView.vue"),
        meta: {
          title: "Areas",
          requireAuth: false,
        },
      },
      {
        path: "specialities",
        name: "public specialities",
        component: () =>
          import("../modules/public/speciality/views/SpecialitiesView.vue"),
        meta: {
          title: "Especialidades",
          requireAuth: false,
        },
      },
    ],
  },
];
