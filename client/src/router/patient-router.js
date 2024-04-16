const rolesAllowed = ["PATIENT"];

export default [
  {
    path: "/patient",
    component: () => import("@/views/PatientView.vue"),
    redirect: { name: "areas-patient" },
    name: "patient",
    children: [
      {
        path: "appointment",
        name: "appointment-patient",
        redirect: { name: "appointment-active" },
        component: () => import("@/modules/patient/appointment/views/AppointmentView.vue"),
        children: [
          {
            path: "save",
            name: "appointment-save-patient",
            component: () => import("@/modules/patient/appointment/views/AppointmentSave.vue"),
            meta: {
              title: "Citas",
              requireAuth: true,
              role: rolesAllowed,
            },
          },
          {
            path: "active",
            name: "appointment-active-patient",
            component: () => import("@/modules/patient/appointment/views/AppointmentActiveView.vue"),
            meta: {
              title: "Citas Activas",
              requireAuth: true,
              role: rolesAllowed,
            },
          },
          {
            path: "pending",
            name: "appointment-pending-patient",
            component: () => import("@/modules/patient/appointment/views/AppointmentPendingView.vue"),
            meta: {
              title: "Citas Pendiente",
              requireAuth: true,
              role: rolesAllowed,
            },
          },
          {
            path: "history",
            name: "appointment-history-patient",
            component: () => import("@/modules/patient/appointment/views/AppointmentHistoryView.vue"),
            meta: {
              title: "Historial de Citas",
              requireAuth: true,
              role: rolesAllowed,
            },
          },
        ],

      },
      {
        path: "profile-patient",
        name: "profile-patient",
        component: () => import("../modules/patient/profile/views/Profile.vue"),
        meta: {
          title: "Perfil",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: "doctors",
        name: "doctors-patient",
        component: () =>
          import("../modules/public/doctors/views/DoctorsView.vue"),
        meta: {
          title: "Doctores",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: "areas",
        name: "areas-patient",
        component: () => import("../modules/public/areas/views/AreasView.vue"),
        meta: {
          title: "Áreas",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: "specialities",
        name: "specialities-patient",
        component: () =>
          import("../modules/public/speciality/views/SpecialitiesView.vue"),
        meta: {
          title: "Especialidades",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: "landing",
        name: "landing-patient",
        component: () => import("../modules/public/views/LandingPageView.vue"),
        meta: {
          title: "Inicio",
          requireAuth: true,
          role: rolesAllowed,
        },
      },
      {
        path: '*',
        component: () => import('@/views/NotFound404.vue'),
        meta: {
          title: '404',
          requireAuth: false,
        },
      }
    ],
  },
];
