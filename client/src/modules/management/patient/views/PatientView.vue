<template>
  <div class="container-fluid mt-4">
    <loading-custom :isLoading="isLoading" />
    <section class="mx-2 px-5">
      <b-row>
        <b-col>
          <h1 class="title-views">Pacientes</h1>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="6">
          <b-input-group>
            <b-form-input
              placeholder="Escribe el nombre del paciente"
              v-model="pagination.data.name"
              @keyup.enter="getAllPatients"
              class="custom-placeholder"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block>Buscar</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-col>
      </b-row>
    </section>

    <section class="mt-4 px-5">
      <b-row>
        <b-col
          v-for="(patient, index) in patients"
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
            rounded
          >
            <b-row no-gutters>
              <b-col md="12">
                <b-card-img
                  :src="getImageSrc(patient)"
                  alt="Image"
                  class="rounded-0"
                  height="160"
                  style="object-fit: cover; object-position: center"
                ></b-card-img>
              </b-col>
              <b-col md="12">
                <b-card-body>
                  <b-card-title class="card-title mb-0"
                    >{{ patient.person.name }} {{ patient.person.surname }}
                    {{ patient.person.lastname }}</b-card-title
                  >
                  <span class="area-indicator"> Paciente</span>
                  <b-card-text>
                    <div class="mt-3">
                      <b-icon icon="phone" class="mr-1"></b-icon>
                      <span class="card-description">{{
                        patient.person.phoneNumber
                      }}</span>
                    </div>
                    <div class="mt-2">
                      <b-icon icon="envelope" class="mr-1"></b-icon>
                      <span class="card-description">{{
                        patient.person.email
                      }}</span>
                    </div>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
            <template #footer>
              <div>
                <b-button
                  v-if="patient.person.user.status.name === EStatus.ACTIVE"
                  @click="changeStatus(patient.person.user.id)"
                  v-b-tooltip.hover.v-info
                  title="Desactivar"
                  variant="outline-primary"
                >
                  <b-icon icon="toggle-on"></b-icon>
                </b-button>
                <b-button
                  v-else-if="
                    patient.person.user.status.name === EStatus.INACTIVE
                  "
                  @click="changeStatus(patient.person.user.id)"
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
                  title="Ver detalles"
                  @click="getOne(patient)"
                  ><b-icon icon="eye"></b-icon
                ></b-button>
              </div>
            </template>
          </b-card>
        </b-col>
      </b-row>
    </section>
    <section class="mt-4">
      <b-row class="bg-light m-0 py-3 py-sm-2 py-lg-1 mb-2 d-flex justify-content-center">
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
            @input="getAllPatients"
          >
          </b-pagination>
        </b-col>
      </b-row>
    </section>
    <ModalPatientView
      :patient="patientSelected"
      :srcImageSelected="srcImageSelected"
    />
  </div>
</template>

<script>
import Vue, { defineAsyncComponent } from "vue";
import { EStatus } from "../../../../kernel/types";
import { encrypt } from "../../../../kernel/hashFunctions";
import boundary from "../boundary";
import patienController from "../services/controller/patient.controller";
import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";
export default Vue.extend({
  components: {
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),
    ModalPatientView: defineAsyncComponent(() =>
      import("./components/ModalPatientView.vue")
    ),
  },
  name: "PatientView",
  data() {
    return {
      isVisible: false,
      isLoading: false,
      docState: "saved",
      showFullDescriptionIndex: -1,
      patients: [],
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
      srcImageSelected: "",
      patientSelected: {
        person: {
          birthday: "",
          curp: "",
          email: "",
          gender: "",
          id: 0,
          lastname: "",
          name: "",
          surname: "",
          phoneNumber: "",
        },
        user: {
          status: {
            id: 0,
            name: "",
            statusType: "",
          },
        },
      },
    };
  },
  methods: {
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
    async getAllPatients() {
      try {
        this.isLoading = true;
        const response = await patienController.getAllPatients({
          page: this.pagination.page - 1,
          size: this.pagination.size,
          sort: this.pagination.sort,
          direction: this.pagination.direction,
          data: this.pagination.data,
        });
        this.patients = response.content;
        this.pagination.totalRows = response.totalElements;
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    async getOne(patient) {
      try {
        const cipherId = await encrypt(patient.id);
        const resp = await patienController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          this.patientSelected = resp;
          this.srcImageSelected = this.getImageSrc(patient);
          this.$bvModal.show("modal-patient-view");
        }
      } catch (error) {
        console.log(error);
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
            this.getAllPatients();
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
    getImageSrc(patient) {
      if (patient.imageUrl) {
        return patient.imageUrl;
      } else {
        const initials = (
          patient?.person?.name.charAt(0) +
          patient?.person?.surname.charAt(0).toUpperCase() +
          patient?.person?.lastname.charAt(0)
        ).toUpperCase();

        const colors = [
          "b3e0ff",
          "a2c4c9",
          "a2d2ff",
          "aed6f1",
          "add8e6",
          "b2d8b2",
          "c7ea46",
          "d4d4dc",
        ];

        const randomColor = colors[Math.floor(Math.random() * colors.length)];
        const textColor = "ffffff";
        const imageUrl = `https://via.placeholder.com/270/${randomColor}/${textColor}/?text=${initials}`;
        patient.imageUrl = imageUrl;

        return imageUrl;
      }
    },
  },
  mounted() {
    this.getAllPatients();
  },
});
</script>
