<template>
  <div class="container-fluid mt-4">
    <loading-custom :isLoading="isLoading" />
    <section class="mx-2">
      <b-row>
        <b-col>
          <h1>Pacientes</h1>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="6">
          <b-input-group>
            <b-form-input
              placeholder="Escribe el nombre del paciente"
              v-model="pagination.data.name"
              @keyup.enter="getAllPatients"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block>Buscar</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-col>
      </b-row>
    </section>

    <section class="mt-5">
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
          >
            <b-row no-gutters>
              <b-col md="12">
                <b-card-img
                :src="getImageSrc(patient)"
                  alt="Image"
                  class="rounded-0"
                  height="160"
                ></b-card-img>
              </b-col>
              <b-col md="12">
                <b-card-body>
                  <b-card-title class="card-title"
                    >{{ patient.person.name }} {{ patient.person.surname }}
                    {{ patient.person.lastname }}</b-card-title
                  >
                  <b-card-text>
                    <div class="mb-3">Paciente</div>
                    <div class="mb-3">
                      <b>No. Telefono: {{ patient.person.phoneNumber }}</b>
                    </div>
                    <div>
                      <b>
                        Correo:
                        <span v-if="patient.person.email.length > 17">
                          {{
                            showFullDescriptionIndex === index
                              ? patient.person.email
                              : patient.person.email.substring(0, 10) + "..."
                          }}
                          <a href="#" @click="toggleDescription(index, $event)">
                            {{
                              showFullDescriptionIndex === index
                                ? "Ver menos"
                                : "Ver más"
                            }}
                          </a>
                        </span>
                        <span v-else>{{ patient.person.email }}</span>
                      </b>
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
                  variant="primary"
                >
                  Desactivar
                </b-button>
                <b-button
                  v-else-if="patient.person.user.status.name === EStatus.INACTIVE"
                  @click="changeStatus(patient.person.user.id)"
                  variant="danger"
                  >Activar</b-button
                >
                <b-button class="ml-1" variant="primary"
                  ><b-icon icon="eye" @click="getOne(patient.id)"></b-icon
                ></b-button>
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
            @input="getAllPatients"
          >
          </b-pagination>
        </b-col>
      </b-row>
    </section>
    <ModalPatientView :patient="patientSelected" />
  </div>
</template>

<script>
import Vue from "vue";
import { EStatus } from "../../../../kernel/types";
import ModalPatientView from "./components/ModalPatientView.vue";
import { encrypt } from "../../../../kernel/hashFunctions";
import boundary from "../boundary"
import patienController from "../services/controller/patient.controller";
import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";
export default Vue.extend({
  components: {
    ModalPatientView,
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),
  },
  name: "PatientView",
  data() {
    return {
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
      patientSelected: {},
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
    async getOne(id) {
      try {
        const cipherId = await encrypt(id);
        const resp = await patienController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          this.patientSelected = resp;
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
        const initials = (patient.person.name.charAt(0) + patient.person.surname.charAt(0).toUpperCase() + patient.person.lastname.charAt(0)).toUpperCase();
        const textColor = "ffffff";
        const backColor = "007bff";
        return `https://via.placeholder.com/270/${backColor}/${textColor}/?text=${initials}`;
    }
  },
  mounted() {
    this.getAllPatients();
  },
});
</script>
