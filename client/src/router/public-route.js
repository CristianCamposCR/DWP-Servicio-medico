export default [
    {
      path: "/public",
      component: () => import("../views/PublicView.vue"),
      redirect: {name: "doctors-public"},
      name: "public",
      children: [
        {
          path: "doctors-public",
          name: "doctors-public",
          component: () =>
            import("../modules/public/views/DoctorsView.vue"),
          meta: {
            title: "Doctores",
            requireAuth: false
          },
          
        },
        
      ],
    },
  ];
  