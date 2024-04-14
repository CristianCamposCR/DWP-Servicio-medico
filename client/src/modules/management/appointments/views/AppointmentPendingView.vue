<template>
    <div class="container-fluid mt-4">
      <loading-custom :isLoading="isLoading" />
      <section class="mx-2 px-5">
        <b-row>
          <b-col>
            <h1 class="title-views">Citas</h1>
          </b-col>
        </b-row>
        <b-row>
          <b-col cols="12" md="6">
            <b-input-group>
              <b-form-input
                placeholder="Escribe la cita"
                v-model="pagination.data.name"
                @keyup.enter="getAllAppointmentsPending"
                class="custom-placeholder"
              ></b-form-input>
  
              <b-input-group-append>
                <b-button variant="primary" block @click="getAllAppointmentsPending">Buscar</b-button>
              </b-input-group-append>
            </b-input-group>
          </b-col>
        </b-row>
      </section>
  
      <section class="mx-2 px-5" v-if="appointments.length > 0">
        <div class="mt-4 shadow appointments-table">
          <table class="table">
            <thead>
              <tr>
                <th>Paciente</th>
                <th>Especialidad</th>
                <th>Fecha</th>
                <th>Doctor</th>
                <th>Turno</th>
                <th>Tipo</th>
                <th>Hora</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(appointment, index) in appointments" :key="index">
                <td>{{ appointment.patient.person.name }} {{ appointment.patient.person.surname }} {{ appointment.patient.person.lastname }}</td>
                <td>{{ appointment.speciality.name }}</td>
                <td>{{ appointment.scheduledAt }}</td>
                <td>{{ appointment.doctor }}</td>
                <td>{{ appointment.preferentialShift.name }}</td>
                <td>{{ appointment.appointmentType.name }}</td>
                <td>{{ appointment.scheduledHour }}</td>
                <td>{{ appointment.status.name }}</td>
                <td>
                  <b-button @click="viewAppointment(appointment)" variant="primary">Ver</b-button>
                  <b-button @click="updateAppointment(appointment)" variant="info">Actualizar</b-button>
                  <b-button @click="deactivateAppointment(appointment)" variant="danger">Desactivar</b-button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
  
      <section class="mt-4" v-if="appointments.length > 0">
        <b-row class="m-0 py-3 py-sm-2 py-lg-1 mb-2 d-flex justify-content-center">
          <b-col cols="6" md="6" class="d-flex align-items-end align-items-md-center justify-content-center">
            <b-pagination
              align="center"
              size="sm"
              class="my-0"
              v-model="pagination.page"
              :total-rows="pagination.totalRows"
              :per-page="pagination.size"
              aria-controls="table-transition-example"
              @input="getAllAppointmentsPending"
            >
            </b-pagination>
          </b-col>
        </b-row>
      </section>
  
      <section class="mt-1" v-if="appointments.length === 0">
        <no-registers :message="'citas'" />
      </section>
    </div>
  </template>
  
  <script>
  import Vue from "vue";
  import { defineAsyncComponent } from "vue";
  import { EStatus } from "../../../../kernel/types";
  import { encrypt } from "../../../../kernel/hashFunctions";
  import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";
  import appointmentsController from "../services/controller/appointments.controller";
  
  export default Vue.extend({
    components: {
      LoadingCustom: defineAsyncComponent(() => import("../../../../views/components/LoadingCustom.vue")),
      NoRegisters: defineAsyncComponent(() => import("../../../../views/components/NoRegisters.vue")),
    },
    name: "PatientView",
    data() {
      return {
        isLoading: false,
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
        appointments: [],
      };
    },
    methods: {
      async getAllAppointmentsPending() {
        try {
          this.isLoading = true;
          const response = await appointmentsController.getAllAppointmentsPending({
            page: this.pagination.page - 1,
            size: this.pagination.size,
            sort: this.pagination.sort,
            direction: this.pagination.direction,
            data: this.pagination.data,
          });
          this.appointments = response.content;
          this.pagination.totalRows = response.totalElements;
        } catch (error) {
          console.log(error);
        } finally {
          this.isLoading = false;
        }
      },
      async viewAppointment(appointment) {
        console.log("Ver cita:", appointment);
      },
      async updateAppointment(appointment) {
        console.log("Actualizar cita:", appointment);
      },
      async deactivateAppointment(appointment) {
        console.log("Desactivar cita:", appointment);
      },
      async changeStatus(id) {},
    },
    mounted() {
      this.getAllAppointmentsPending();
    },
  });
  </script>
  
  <style>
.appointments-table {
  border-radius: 10px; 
  overflow: hidden; 
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.08); 
}

.appointments-table table {
  width: 100%; 
  border-collapse: collapse; 
}

.appointments-table th,
.appointments-table td {
  padding: 10px; 
  text-align: center; 
  border: 1px solid #dee2e6; 
}

.appointments-table th {
  background-color: #f8f9fa;
}

</style>

  
