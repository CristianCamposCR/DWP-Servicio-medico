<template>
  <div>
    <div class="container-fluid mt-4">
      <loading-custom :isLoading="isLoading" />
      <section class="mx-2 px-5">
        <b-row>
          <b-col>
            <h1 class="title-views">Doctores</h1>
          </b-col>
        </b-row>
        <b-row>
          <b-col cols="12" md="6">
            <b-input-group>
              <b-form-input
                placeholder="Escribe el nombre del doctor"
                v-model="pagination.data.name"
                @keyup.enter="getAllDoctors"
                class="custom-placeholder"
              ></b-form-input>

              <b-input-group-append>
                <b-button variant="primary" block>Buscar</b-button>
              </b-input-group-append>
            </b-input-group>
          </b-col>
          <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
            <div class="d-flex align-items-center mt-2 mt-md-0">
              <span class="mr-1 area-indicator">Agregar nuevo doctor</span>
              &nbsp;
              <b-button variant="primary" v-b-modal.modal-save-doctor>
                <b-icon icon="plus" />
              </b-button>
            </div>
          </b-col>
        </b-row>
      </section>
      <save-doctor @reloadRegisters="getAllDoctors" />
      <section class="mt-5 px-5" v-if="doctors.length > 0">
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
                      doctor.person.profilePhoto
                        ? doctor.person.profilePhoto
                        : '/src/assets/image/default-area.avif'
                    "
                    alt="Image"
                    class="rounded-0"
                    height="160"
                  ></b-card-img>
                </b-col>
                <b-col md="12">
                  <b-card-body>
                    <b-card-title class="card-title mb-0"
                      >{{ doctor.person.name }} {{ doctor.person.surname }}
                      {{ doctor.person.lastname }}</b-card-title
                    >
                    <span class="area-indicator"> Doctor</span>

                    <b-card-text>
                      <div class="mt-3">
                        <b-icon icon="phone" class="mr-1"></b-icon>
                        <span class="card-description">{{
                          doctor.person.phoneNumber
                        }}</span>
                      </div>
                      <div class="mt-2">
                        <b-icon icon="envelope" class="mr-1"></b-icon>
                        <span class="card-description">{{
                          doctor.person.email
                        }}</span>
                      </div>
                    </b-card-text>
                  </b-card-body>
                </b-col>
              </b-row>
              <template #footer>
                <div>
                  <b-button
                    v-if="doctor.person.user.status.name === EStatus.ACTIVE"
                    @click="changeStatus(doctor.person.user.id)"
                    v-b-tooltip.hover.v-info
                    title="Desactivar"
                    variant="outline-primary"
                  >
                    <b-icon icon="toggle-on"></b-icon>
                  </b-button>
                  <b-button
                    v-else-if="
                      doctor.person.user.status.name === EStatus.INACTIVE
                    "
                    @click="changeStatus(doctor.person.user.id)"
                    v-b-tooltip.hover.v-info
                    title="Activar"
                    variant="outline-danger"
                  >
                    <b-icon icon="toggle-off"></b-icon>
                  </b-button>
                  <b-button
                    class="ml-3"
                    variant="outline-secondary"
                    v-b-tooltip.hover.v-info
                    title="Editar"
                    @click="updateDoctor(doctor.id)"
                    ><b-icon icon="pencil"></b-icon
                  ></b-button>
                  <b-button
                  class="ml-3"
                  variant="outline-secondary"
                  v-b-tooltip.hover.v-info
                  title="Ver detalles"
                  @click="getOneDoctor(doctor.id)"
                  ><b-icon icon="eye"></b-icon
                ></b-button>
                </div>
              </template>
            </b-card>
          </b-col>
        </b-row>
      </section>
      <section class="mt-4" v-if="doctors.length > 0">
        <b-row
          class="m-0 py-3 py-sm-2 py-lg-1 mb-2 d-flex justify-content-center"
        >
          <b-col
            cols="6"
            md="6"
            class="d-flex align-items-end align-items-md-center justify-content-center"
          >
            <b-pagination
              align="center"
              size="md"
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

      <section class="mt-1" v-if="doctors.length === 0 && isLoading === false">
      <no-registers :message="'áreas'" />
    </section>
    </div>
    <update-doctor
      :doctors="doctorSelected"
      @realoadUpdateDoctor="getAllDoctors"
    />
    <doctor-modal-details :doctor="doctorSelected" />
  </div>
</template>
<script>
import Vue, { defineAsyncComponent } from "vue";
import { EStatus } from "../../../../kernel/types";
import { encrypt } from "../../../../kernel/hashFunctions";
import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";
import doctorController from "../services/controller/doctor.controller";
import boundary from "../boundary";

export default Vue.extend({
  name: "DoctorsPublicView",
  components: {
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),
    SaveDoctor: defineAsyncComponent(() =>
      import("./components/SaveDoctor.vue")
    ),
    UpdateDoctor: defineAsyncComponent(() =>
      import("./components/UpdateDoctor.vue")
    ),
    DoctorModalDetails: defineAsyncComponent(() => import("./components/DetailsDoctor.vue")), 
    NoRegisters: defineAsyncComponent(() =>
      import("../../../../views/components/NoRegisters.vue")
    ),
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
    async updateDoctor(id) {
      try {
        this.isLoading = true;
        const cipherId = await encrypt(id);
        const resp = await doctorController.getOne(cipherId);
        const englishToSpanish = {
          MONDAY: "Lunes",
          TUESDAY: "Martes",
          WEDNESDAY: "Miercoles",
          THURSDAY: "Jueves",
          FRIDAY: "Viernes",
        };

        const { error } = resp;
        if (!error) {
          const daysArray = resp.availableDays
            .replace("[", "")
            .replace("]", "")
            .split(", ")
            .map((day) => englishToSpanish[day]);
          const resultArray = daysArray.map((day) => ({
            name: day,
            id: Object.keys(englishToSpanish).find(
              (key) => englishToSpanish[key] === day
            ),
          }));
          resp.availableDays = resultArray;
          this.doctorSelected = resp;
          this.$bvModal.show("update-doctor");
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    async getOneDoctor(id) {
      try {
        this.isLoading = true;
        const cipherId = await encrypt(id);
        const resp = await doctorController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          this.doctorSelected = resp;
          this.$bvModal.show("doctor-modal-details");
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
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
    async changeStatus(id) {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          const cipherId = await encrypt(id);
          const resp = await boundary.userController.changeStatus(cipherId);
          const { error } = resp;
          if (!error) {
            this.getAllDoctors();
            setTimeout(() => {
              SweetAlertCustom.successMessage();
            }, 100);
            return;
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
  mounted() {
    this.getAllDoctors();
  },
});
</script>
