export default [
  {
    path: "/management",
    component: () => import("../views/ManagementView.vue"),
    redirect: {name: "areas-management"},
    name: "management",
    children: [
      {
        path: "areas-management",
        name: "areas-management",
        component: () =>
          import("../modules/management/areas/views/AreasView.vue"),
        meta: {
          title: "Áreas",
          requireAuth: false
        },
        
      },
      {
        path: "specialties-management",
        name: "specialties-management",
        component: () =>
          import("../modules/management/speciality/views/SpecialtiesView.vue"),
        meta: {
          title: "Specialties",
          requireAuth: false
        }
      },
      {
        path: "patient-management",
        name: "patient-management",
        component: () =>
          import("../modules/management/patient/views/PatientView.vue"),
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
