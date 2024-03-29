<template>
  <div class="container-fluid mt-4">
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
          xl="2"
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
                  src="https://via.placeholder.com/270"
                  alt="Image"
                  class="rounded-0"
                  height="160"
                ></b-card-img>
              </b-col>
              <b-col md="12">
                <b-card-body :title="patient.name">
                  <b-card-text>
                    <div class="mb-3">Paciente</div>
                    <div class="mb-3">
                      <b>No. Telefono: {{ patient.phone }}</b>
                    </div>
                    <div>
                      <b>
                        Correo:
                        <span v-if="patient.email.length > 17">
                          {{
                            showFullDescriptionIndex === index
                              ? patient.email
                              : patient.email.substring(0, 10) + "..."
                          }}
                          <a href="#" @click="toggleDescription(index, $event)">
                            {{
                              showFullDescriptionIndex === index
                                ? "Ver menos"
                                : "Ver más"
                            }}
                          </a>
                        </span>
                        <span v-else>{{ patient.email }}</span>
                      </b>
                    </div>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
            <template #footer>
              <div>
                <b-button
                  v-if="patient.status === EStatus.ACTIVE"
                  @click="patient.status = EStatus.INACTIVE"
                  variant="primary"
                >
                  Desactivar
                </b-button>
                <b-button
                  v-else-if="patient.status === EStatus.INACTIVE"
                  @click="patient.status = EStatus.ACTIVE"
                  variant="danger"
                  >Activar</b-button
                >
                <b-button v-b-modal.modal-patient-view class="ml-2" variant="primary">
      <b-icon icon="eye"></b-icon>
    </b-button>
              </div>
            </template>
          </b-card>
        </b-col>
      </b-row>
    </section>
    <section>
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
    <ModalPatientView />
  </div>
</template>

<script>
import Vue from "vue";
import { EStatus } from "../../../../kernel/types";
import ModalPatientView from "./ModalPatientView.vue";
export default Vue.extend({
  components: { ModalPatientView },
  name: "PatientView",
  data() {
    return {
      docState: "saved",
      showFullDescriptionIndex: -1,
      patients: [
        {
          id: 1,
          name: "Maycon Manuel Carmona",
          phone: "7772002582",
          email: "maycon@grupoeimsa.com.mx",
          status: "ACTIVO",
        },
        {
          id: 2,
          name: "Laura González",
          phone: "5551234567",
          email: "20213tn004@utez.edu.mx",
          status: "ACTIVO",
        },
        {
          id: 3,
          name: "Juan Pérez",
          phone: "3339876543",
          email: "juan@example.com",
          status: "ACTIVO",
        },
        {
          id: 4,
          name: "María Rodríguez",
          phone: "4442345678",
          email: "maria@example.com",
          status: "ACTIVO",
        },
        {
          id: 5,
          name: "Carlos García",
          phone: "6668765432",
          email: "carlos@example.com",
          status: "ACTIVO",
        },
        {
          id: 6,
          name: "Ana Martínez",
          phone: "7777654321",
          email: "ana@example.com",
          status: "ACTIVO",
        },
        {
          id: 7,
          name: "Pedro López",
          phone: "9991122334",
          email: "pedro@example.com",
          status: "ACTIVO",
        },
        {
          id: 8,
          name: "Sofía Hernández",
          phone: "8883344556",
          email: "sofia@example.com",
          status: "ACTIVO",
        },
        {
          id: 9,
          name: "Diego Díaz",
          phone: "1114445566",
          email: "diego@example.com",
          status: "ACTIVO",
        },
        {
          id: 10,
          name: "Luisa Sánchez",
          phone: "2227778888",
          email: "luisa@example.com",
          status: "ACTIVO",
        },
        {
          id: 11,
          name: "Martín Ramírez",
          phone: "3339990001",
          email: "martin@example.com",
          status: "ACTIVO",
        },
        {
          id: 12,
          name: "Elena Vázquez",
          phone: "7771112222",
          email: "elena@example.com",
          status: "ACTIVO",
        },
        {
          id: 13,
          name: "Héctor González",
          phone: "5554443333",
          email: "hector@example.com",
          status: "ACTIVO",
        },
        {
          id: 14,
          name: "Marta Fernández",
          phone: "7777777777",
          email: "marta@example.com",
          status: "ACTIVO",
        },
        {
          id: 15,
          name: "Javier Torres",
          phone: "9993332222",
          email: "javier@example.com",
          status: "ACTIVO",
        },
        {
          id: 16,
          name: "Natalia Gómez",
          phone: "7775551111",
          email: "natalia@example.com",
          status: "ACTIVO",
        },
        {
          id: 17,
          name: "Gabriel Ruiz",
          phone: "8887776666",
          email: "gabriel@example.com",
          status: "ACTIVO",
        },
        {
          id: 18,
          name: "Anaí Jiménez",
          phone: "7774449999",
          email: "anai@example.com",
          status: "ACTIVO",
        },
        {
          id: 19,
          name: "Roberto Mendoza",
          phone: "5557779999",
          email: "roberto@example.com",
          status: "ACTIVO",
        },
        {
          id: 20,
          name: "Sara Martínez",
          phone: "7773331111",
          email: "sara@example.com",
          status: "ACTIVO",
        },
      ],
      EStatus: EStatus,
      pagination: {
        page: 1,
        sort: "id",
        size: 10,
        direction: "desc",
        totalRows: 25,
      },
    };
  },
  methods: {
    enable(){
        
    },
    disable(){

    },
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
    getAllPatients() {
      console.log("wachando pacientes");
    },
  },
});
</script>
