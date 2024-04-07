<template>
  <div>
  <navbar/>
  <div class="container-fluid mt-4">
    <loading-custom :isLoading="isLoading" />
    <section class="mx-2">
      <b-row>
        <b-col>
          <h1>Doctores</h1>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="6">
          <b-input-group>
            <b-form-input
              placeholder="Escribe el nombre del doctor"
              v-model="pagination.data.name"
              @keyup.enter="getAllDoctors"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block>Buscar</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-col>
        <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
        </b-col>
      </b-row>
    </section>

    <section class="mt-5">
      <b-row>
        <b-col
          v-for="(doctor, index) in doctors"
          :key="index"
          cols="12"
          sm="6"
          md="4"
          lg="3"
          class="d-flex justify-content-center"
        >
          <b-card
            no-body
            class="overflow-hidden mt-3 mx-2 shadow card-animation"
            style="max-width: 270px; max-height: 800px"
            footer-bg-variant="transparent"
            footer-border-variant="white"
          >
            <b-row no-gutters>
              <b-col md="12">
                <b-card-img
                  :src="
                    doctor.profile
                      ? doctor.profile
                      : '/src/assets/image/default-area.avif'
                  "
                  alt="Image"
                  class="rounded-0"
                  height="160"
                ></b-card-img>

              </b-col>
              <b-col md="12">
                <b-card-body>
                  <b-card-title class="card-title">{{
                    doctor.name
                  }}</b-card-title>
                  <b-card-text>
                    <div class="mb-3"> Doctor</div>
                  <div class="mb-3">
                    <b>No. Telefono: </b>{{ doctor.phone }}
                  </div>
                    <div>
                      <b>Correo: </b>
                      <span
                        v-if="doctor.email && doctor.email.length > 50"
                        class="card-description"
                      >
                        {{
                          showFullDescriptionIndex === index
                            ? doctor.email
                            : doctor.email.substring(0, 50) + "..."
                        }}
                        <a href="#" @click="toggleDescription(index, $event)">
                          {{
                            showFullDescriptionIndex === index
                              ? "Ver menos"
                              : "Ver más"
                          }}
                        </a>
                      </span>
                      <span
                        v-else-if="doctor.email"
                        class="card-description"
                        >{{ doctor.email }}</span
                      >
                    </div>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
            <template #footer>
              <div>
                <b-button class="ml-2" variant="primary"  @click="getOneDoctor">
                  Mas información
                </b-button>
              </div>
            </template>
          </b-card>
        </b-col>
      </b-row>
    </section>
    <section class="mt-4">
      <b-row class="bg-light m-0 py-3 py-sm-2 py-lg-1">
        <b-col
          cols="12"
          md="3"
          class="d-flex justify-content-center justify-content-md-start"
        >
          <b class="fw-bold"
            >Mostrando
            {{
              pagination.totalRows === 0
                ? 0
                : (pagination.page - 1) * pagination.size + 1
            }}
            a
            {{
              pagination.page * pagination.size > pagination.totalRows
                ? pagination.totalRows
                : pagination.page * pagination.size
            }}
            de {{ pagination.totalRows }} registros</b
          >
        </b-col>

        <b-col
          cols="6"
          md="6"
          class="d-flex align-items-end align-items-md-center justify-content-center"
        >
          <b-pagination
            align="center"
            size="sm"
            class="my-0"
            v-model="pagination.page"
            :total-rows="pagination.totalRows"
            :per-page="pagination.size"
            aria-controls="table-transition-example"
            @input="getAllDoctors"
          >
          </b-pagination>
        </b-col>
      </b-row>
    </section>
    <DoctorModal/>
  </div>
</div>
</template>
<script>
import Vue from "vue";
import { EStatus } from "../../../kernel/types";
import DoctorModal from "./DoctorModal.vue";
export default Vue.extend({
  components: {
  LoadingCustom: () =>
        import("../../../views/components/LoadingCustom.vue"),
  DoctorModal,
  Navbar: () => import('@/modules/public/components/Navbar.vue'),

},
  name: "DoctorsPublicView",
  components: {
      LoadingCustom: () =>
        import("../../../views/components/LoadingCustom.vue"),
    },
  data() {
    return {
      isLoading: false,
      docState: "saved",
      showFullDescriptionIndex: -1,
      doctor: {
        id: null,
        name: "",
        phone: "",
        profile:'',
        email: "",
        status: "",
      },
      doctors: [
        {
          id: 1,
          name: "Maycon Manuel Carmona",
          phone: "7772002582",
          profile:'',
          email: "maycon@grupoeimsa.com.mx",
          status: "ACTIVO",
        },
        {
          id: 2,
          name: "Laura González",
          phone: "5551234567",
          profile:'',
          email: "20213tn004@utez.edu.mx",
          status: "ACTIVO",
        },
        {
          id: 3,
          name: "Juan Pérez",
          phone: "3339876543",
          profile:'',
          email: "juan@example.com",
          status: "ACTIVO",
        },
        {
          id: 4,
          name: "María Rodríguez",
          phone: "4442345678",
          profile:'',
          email: "maria@example.com",
          status: "ACTIVO",
        },
        {
          id: 5,
          name: "Carlos García",
          phone: "6668765432",
          profile:'',
          email: "carlos@example.com",
          status: "ACTIVO",
        },
        {
          id: 6,
          name: "Ana Martínez",
          phone: "7777654321",
          profile:'',
          email: "ana@example.com",
          status: "ACTIVO",
        },
        {
          id: 7,
          name: "Pedro López",
          phone: "9991122334",
          profile:'',
          email: "pedro@example.com",
          status: "ACTIVO",
        },
        {
          id: 8,
          name: "Sofía Hernández",
          phone: "8883344556",
          profile:'',
          email: "sofia@example.com",
          status: "ACTIVO",
        },
        {
          id: 9,
          name: "Diego Díaz",
          phone: "1114445566",
          profile:'',
          email: "diego@example.com",
          status: "ACTIVO",
        },
        {
          id: 10,
          name: "Luisa Sánchez",
          phone: "2227778888",
          profile:'',
          email: "luisa@example.com",
          status: "ACTIVO",
        },
        {
          id: 11,
          name: "Martín Ramírez",
          phone: "3339990001",
          profile:'',
          email: "martin@example.com",
          status: "ACTIVO",
        },
        {
          id: 12,
          name: "Elena Vázquez",
          phone: "7771112222",
          profile:'',
          email: "elena@example.com",
          status: "ACTIVO",
        },
        {
          id: 13,
          name: "Héctor González",
          phone: "5554443333",
          profile:'',
          email: "hector@example.com",
          status: "ACTIVO",
        },
        {
          id: 14,
          name: "Marta Fernández",
          phone: "7777777777",
          profile:'',
          email: "marta@example.com",
          status: "ACTIVO",
        },
        {
          id: 15,
          name: "Javier Torres",
          phone: "9993332222",
          profile:'',
          email: "javier@example.com",
          status: "ACTIVO",
        },
        {
          id: 16,
          name: "Natalia Gómez",
          phone: "7775551111",
          profile:'',
          email: "natalia@example.com",
          status: "ACTIVO",
        },
        {
          id: 17,
          name: "Gabriel Ruiz",
          phone: "8887776666",
          profile:'',
          email: "gabriel@example.com",
          status: "ACTIVO",
        },
        {
          id: 18,
          name: "Anaí Jiménez",
          phone: "7774449999",
          profile:'',
          email: "anai@example.com",
          status: "ACTIVO",
        },
        {
          id: 19,
          name: "Roberto Mendoza",
          phone: "5557779999",
          profile:'',
          email: "roberto@example.com",
          status: "ACTIVO",
        },
        {
          id: 20,
          name: "Sara Martínez",
          phone: "7773331111",
          profile:'',
          email: "sara@example.com",
          status: "ACTIVO",
        },
      ],
      EStatus: EStatus,
      pagination: {

          page: 1,
          sort: "id",
          size: 8,
          direction: "desc",
          totalRows: 0,
          data: {
            name: null,
          },
        },
    };
  },
  methods: {
    enable() {},
    disable() {},
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
    getOneDoctor(){
      this.$bvModal.show("modal-doctor");
    },
    async getAllDoctors() {
      console.log("wachando doctors");
      // try {
      //     this.isLoading = true;
      //     const response = await doctorsController.getAllDoctors({
      //       page: this.pagination.page - 1,
      //       size: this.pagination.size,
      //       sort: this.pagination.sort,
      //       direction: this.pagination.direction,
      //       data: this.pagination.data,
      //     });
      //     this.doctors = response.content;
      //     this.pagination.totalRows = response.totalElements;
      //   } catch (error) {
      //     console.log(error);
      //   } finally {
      //     this.isLoading = false;
      //   }
    },
  },
  mounted() {
      this.getAllDoctors();
    },

});
</script>
