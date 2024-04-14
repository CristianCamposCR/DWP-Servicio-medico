const rolesAllowed = ["ADMIN"];

export default [
  {
    path: "/management",
    component: () => import("../views/ManagementView.vue"),
    redirect: { name: "areas-management" },
    name: "management",
    children: [
      {
        path: "areas-management",
        name: "areas-management",
        component: () =>
          import("../modules/management/areas/views/AreasView.vue"),
        meta: {
          title: "Áreas",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: "specialties-management",
        name: "specialties-management",
        component: () =>
          import("../modules/management/speciality/views/SpecialtiesView.vue"),
        meta: {
          title: "Especialidades",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: "patient-management",
        name: "patient-management",
        component: () =>
          import("../modules/management/patient/views/PatientView.vue"),
        meta: {
          title: "Pacientes",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: "doctors-management",
        name: "doctors-management",
        component: () =>
          import("../modules/management/doctors/views/DoctorsView.vue"),
        meta: {
          title: "Doctores",
          requireAuth: true,
          role: rolesAllowed,
        },
      },

    ],
  },
];
