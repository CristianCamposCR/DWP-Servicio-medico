const rolesAllowed = ["DOCTOR"];

export default [
    {
      path: "/doctor",
      component: () => import("../views/DoctorView.vue"),
      redirect: {name: "profile-doctor"},
      name: "doctor",
      children: [
        {
          path: "profile-doctor",
          name: "profile-doctor",
          component: () =>
            import("../modules/doctor/profile/views/Profile.vue"),
          meta: {
            title: "Perfil",
            requireAuth: true,
            role: rolesAllowed
          },
          
        },
        {
          path: "appointments-active-doctor",
          name: "appointments-active-doctor",
          component: () =>
            import("../modules/doctor/appointments/views/AppointmentActiveByDoctorView.vue"),
          meta: {
            title: "Citas Asignadas",
            requireAuth: true,
            role: rolesAllowed,
          },
        },
 
        {
          path: "appointments-history-doctor",
          name: "appointments-history-doctor",
          component: () =>
            import("../modules/doctor/appointments/views/AppointmentHistoryByDoctorView.vue"),
          meta: {
            title: "Historial de Citas",
            requireAuth: true,
            role: rolesAllowed,
          },
        },
        
      ],
    },
  ];
  