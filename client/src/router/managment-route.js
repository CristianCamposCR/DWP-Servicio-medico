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
    ],
  },
];
