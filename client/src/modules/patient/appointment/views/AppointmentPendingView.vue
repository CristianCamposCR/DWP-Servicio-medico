<template>
    <div class="container-fluid mt-4">
      <loading-custom :isLoading="isLoading" />
      <section class="mx-2 px-5">
        <b-row>
          <b-col>
            <h1 class="title-views">Citas Pendientes</h1>
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
                <td>{{ appointment.dotor ? `${appointment.doctor.person.name} ${appointment.doctor.person.surname}
                ${appointment.doctor.person.lastname}` :
                `Sin doctor asignado` }}</td>
                <td>{{ appointment.preferentialShift.name }}</td>
                <td>{{ appointment.appointmentType.name }}</td>
                <td>{{ appointment.scheduledHour ? appointment.scheduledHour : 'Sin hora asignada' }}</td>
                <td>{{ appointment.status.name }}</td>
                <td>
                  <b-button class="mr-2" @click="cancelVoluntarily(appointment.id)" v-b-tooltip.hover.v-info
                  title="Cancelar" variant="outline-danger">
                  <b-icon icon="calendar-x"></b-icon>
                </b-button>
                <b-button class="mr-2" @click="reescheduleAppointment(appointment.id)" v-b-tooltip.hover.v-info title="Solicitar reagenda de cita"
                  variant="outline-success">
                  <b-icon icon="calendar-event"></b-icon>
                </b-button>
                  <b-button variant="outline-secondary" v-b-tooltip.hover.v-info title="Ver detalles"
                  @click="viewAppointment(appointment.id)"><b-icon icon="eye"></b-icon></b-button>
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
      <details-appointment :appointmentData="appointmentSelected"/>

      <reeschedule :appointmentSelected="appointmentSelected" @reloadRegisters="getAllAppointmentsPending"
    @showLoading="showLoading" @hideLoading="hideLoading">
    </reeschedule>
    </div>
  </template>
  
  <script>
  import Vue, { defineAsyncComponent }from "vue";
  import appointmentsController from "../services/controller/appointment.controller";
  import { encrypt } from "../../../../kernel/hashFunctions";
  import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";
  import boundary from "../boundary"
  export default Vue.extend({
    components: {
      LoadingCustom: defineAsyncComponent(() => import("../../../../views/components/LoadingCustom.vue")),
      NoRegisters: defineAsyncComponent(() => import("../../../../views/components/NoRegisters.vue")),
      DetailsAppointment: defineAsyncComponent(()=> import("./components/DetailsAppointment.vue")),
      Reeschedule: defineAsyncComponent(() => import("./components/Reeschedule.vue"))
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
        appointmentSelected:{},
        appointments: [],
        appointmentSelected: {},
      };
    },
    methods: {
      showLoading(){
      this.isLoading = true
      },
      hideLoading(){
        this.isLoading = false
      },
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
      async viewAppointment(id) {
      try {
        this.isLoading = true;
        const cipherId = await encrypt(id);
        const resp = await boundary.appointmentsController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          this.appointmentSelected = resp;
        this.$bvModal.show("details-appointment");
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
  
      },
      async reescheduleAppointment(id) {
        try {
        this.isLoading = true;
        const cipherId = await encrypt(id);
        const resp = await appointmentsController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          this.appointmentSelected = resp;
          this.$bvModal.show("modal-reeschedule-appointment");
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
      },
      async cancelVoluntarily(id) {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          this.isLoading = true;
          const cipherId = await encrypt(id);
          const resp = await appointmentsController.cancelAppointment(cipherId, {
            reason: "VOLUNTARIA"
          });
          const { error } = resp;
          if (!error) {
            this.getAllAppointmentsPending();
            setTimeout(() => {
              SweetAlertCustom.successMessage();
            }, 900);
            return;
          }
        }
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
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

  
