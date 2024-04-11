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
        
      ],
    },
  ];
  