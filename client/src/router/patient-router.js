const rolesAllowed = ["PATIENT"];

export default [
    {
        path: "/patient",
        component: () => import("@/views/PatientView.vue"),
        redirect: {name: "appointment-patient"},
        name: "patient",
        children: [
            {
                path: "appointment-patient",
                name: "appointment-patient",
                component: () =>
                    import("@/modules/patient/appointment/views/AppointmentViews.vue"),
                meta: {
                    title: "Citas",
                    requireAuth: true,
                    role: rolesAllowed,
                },

            },
            {
                path: 'profile-patient',
                name: 'profile-patient',
                component: () => import('../modules/patient/profile/views/Profile.vue'),
                meta: {
                  title: "Perfil",
                  requireAuth: true,
                  role: rolesAllowed,
                },
              },

        ],
    },
];
