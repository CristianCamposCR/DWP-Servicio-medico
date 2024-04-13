<template>
  <div>
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
            <div class="d-flex align-items-center mt-2 mt-md-0">
              <span class="mr-1">Agregar nuevo doctor</span> &nbsp;
              <b-button variant="primary" v-b-modal.modal-save-doctor>
                <b-icon icon="plus" />
              </b-button>
            </div>
          </b-col>
        </b-row>
      </section>
      <save-doctor @reloadRegisters="getAllDoctors"/>
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
                    <b-card-title class="card-title"
                      >{{ doctor.person.name }} {{ doctor.person.surname }}
                      {{ doctor.person.lastname }}</b-card-title
                    >
                    <b-card-text>
                      <div class="mb-3">Doctor</div>
                      <div class="mb-3">
                        <b>No. Telefono: </b>{{ doctor.person.phoneNumber }}
                      </div>
                      <div>
                        <b>Correo: </b>
                        <span
                          v-if="
                            doctor.person.email &&
                            doctor.person.email.length > 50
                          "
                          class="card-description"
                        >
                          {{
                            showFullDescriptionIndex === index
                              ? doctor.person.email
                              : doctor.person.email.substring(0, 50) + "..."
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
                          v-else-if="doctor.person.email"
                          class="card-description"
                          >{{ doctor.person.email }}</span
                        >
                      </div>
                    </b-card-text>
                  </b-card-body>
                </b-col>
              </b-row>
              <template #footer>
                <div>
                  <b-button
                    class="ml-2"
                    variant="primary"
                    @click="getOneDoctor(doctor.id)"
                  >
                    Editar doctor
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
    </div>
    <update-doctor :doctors="doctorSelected" @realoadUpdateDoctor="getAllDoctors" />
  </div>
</template>
<script>
import Vue, { defineAsyncComponent } from "vue";
import { EStatus } from "../../../../kernel/types";
import { encrypt } from "../../../../kernel/hashFunctions";
import doctorController from "../services/controller/doctor.controller";

export default Vue.extend({
  name: "DoctorsPublicView",
  components: {
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),
    SaveDoctor: defineAsyncComponent(()=> import("./components/SaveDoctor.vue")),
    UpdateDoctor: defineAsyncComponent(()=> import("./components/UpdateDoctor.vue"))
  },
  data() {
    return {
      isLoading: false,
      showFullDescriptionIndex: -1,
      doctors: [],
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
      doctorSelected: {},
    };
  },
  methods: {
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
    async getOneDoctor(id) {
      try {
        const cipherId = await encrypt(id);
        const resp = await doctorController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          resp.availableDays = resp.availableDays.split(',').map(day => day.trim());
          this.doctorSelected = resp;
          console.log("DATAONE", resp);
          this.$bvModal.show("update-doctor");
        }
      } catch (error) {
        console.log(error);
      }
    },
    async getAllDoctors() {
      try {
        this.isLoading = true;
        const response = await doctorController.getAllDoctors({
          page: this.pagination.page - 1,
          size: this.pagination.size,
          sort: this.pagination.sort,
          direction: this.pagination.direction,
          data: this.pagination.data,
        });
        this.doctors = response.content;
        this.pagination.totalRows = response.totalElements;
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
  },
  mounted() {
    this.getAllDoctors();
  },
});
</script>
