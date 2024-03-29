export default [
  {
    path: "/managment",
    component: () => import("../views/ManagmentView.vue"),
    redirect: {name: "areas-managment"},
    name: "managment",
    children: [
      {
        path: "areas-managment",
        name: "areas-managment",
        component: () =>
          import("../modules/managment/areas/views/AreasView.vue"),
        meta: {
          title: "Áreas",
          requireAuth: false
        },
        
      },
      {
        path: "patient-managment",
        name: "patient-managment",
        component: () =>
          import("../modules/managment/patient/views/PatientView.vue"),
        meta: {
          title: "Pacientes",
          requireAuth: false
        },
        
      },
      {
        path: 'profile',
        name: 'profile',
        component: () => import('../modules/profile/views/Profile.vue'),
        meta: {
          title: "Áreas",
          requireAuth: false
        },
      },
    ],
  },
];
