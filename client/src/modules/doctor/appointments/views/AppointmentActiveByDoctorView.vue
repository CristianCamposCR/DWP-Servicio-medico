<template>
  <div class="container-fluid mt-4">
    <loading-custom :isLoading="isLoading" />
    <section class="mx-2 px-5">
      <b-row>
        <b-col>
          <h1 class="title-views">Citas Asignadas</h1>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="6">
          <b-input-group>
            <b-form-input
              placeholder="Escribe la cita"
              v-model="pagination.data.name"
              @keyup.enter="getAllAppointmentsByDoctor"
              class="custom-placeholder"
            ></b-form-input>

            <b-input-group-append>
              <b-button
                variant="primary"
                block
                @click="getAllAppointmentsByDoctor"
                >Buscar</b-button
              >
            </b-input-group-append>
          </b-input-group>
        </b-col>
      </b-row>
    </section>

    <section class="mx-2 px-5" v-if="appointments.length > 0">
      <div class="mt-4 shadow appointments-table">
        <div class="table-responsive">
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
                <td>
                  {{ appointment.patient.person.name }}
                  {{ appointment.patient.person.surname }}
                  {{ appointment.patient.person.lastname }}
                </td>
                <td>{{ appointment.speciality.name }}</td>
                <td>{{ appointment.scheduledAt }}</td>
                <td>
                  {{ appointment.doctor.person.name }}
                  {{ appointment.doctor.person.surname }}
                  {{ appointment.doctor.person.lastname }}
                </td>
                <td>{{ appointment.preferentialShift.name }}</td>
                <td>{{ appointment.appointmentType.name }}</td>
                <td>{{ appointment.scheduledHour }}</td>
                <td>{{ appointment.status.name }}</td>
                <td>
                  <b-button
                    class="mr-2"
                    @click="cancelDueAbsence(appointment.id)"
                    v-b-tooltip.hover.v-info
                    title="Cancelar por ausencia del paciente"
                    variant="outline-danger"
                  >
                    <b-icon icon="person-x"></b-icon>
                  </b-button>
                  <b-button
                    class="mr-2"
                    @click="notifyNonAvailability(appointment.id)"
                    v-b-tooltip.hover.v-info
                    title="Notificar poca disponibilidad"
                    variant="outline-warning"
                  >
                    <b-icon icon="bell"></b-icon>
                  </b-button>
                  <b-button
                    variant="outline-secondary"
                    v-b-tooltip.hover.v-info
                    title="Ver detalles"
                    @click="viewAppointment(appointment)"
                    ><b-icon icon="eye"></b-icon
                  ></b-button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </section>

    <section class="mt-4" v-if="appointments.length > 0">
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
            size="sm"
            class="my-0"
            v-model="pagination.page"
            :total-rows="pagination.totalRows"
            :per-page="pagination.size"
            aria-controls="table-transition-example"
            @input="getAllAppointmentsByDoctor"
          >
          </b-pagination>
        </b-col>
      </b-row>
    </section>

    <section class="mt-1" v-if="appointments.length === 0">
      <no-registers :message="'citas'" />
    </section>
    <save-records
      :appointmentSelected="appointmentSelected"
      @realoadRegisters="getAllAppointmentsByDoctor"
    />
  </div>
</template>

<script>
import Vue, { defineAsyncComponent } from "vue";
import appointmentsController from "../services/controller/appointments.controller";
import { encrypt } from "../../../../kernel/hashFunctions";
import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";

export default Vue.extend({
  components: {
    LoadingCustom: defineAsyncComponent(() =>
      import("../../../../views/components/LoadingCustom.vue")
    ),
    NoRegisters: defineAsyncComponent(() =>
      import("../../../../views/components/NoRegisters.vue")
    ),
    SaveRecords: defineAsyncComponent(() =>
      import("../../records/views/components/SaveRecords.vue")
    ),
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
      appointmentSelected: 0,
      appointments: [],
    };
  },
  methods: {
    async getAllAppointmentsByDoctor() {
      try {
        this.isLoading = true;
        const response =
          await appointmentsController.getAllAppointmentsByDoctor({
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
      this.appointmentSelected = appointment.id;
      this.$bvModal.show("save-records");
    },
    async cancelDueNonAval(id) {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          this.isLoading = true;
          const cipherId = await encrypt(id);
          const resp = await appointmentsController.cancelAppointment(
            cipherId,
            {
              reason: "AUSENCIA",
            }
          );
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
    async notifyNonAvailability(id) {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          this.isLoading = true;
          const cipherId = await encrypt(id);
          const resp = await appointmentsController.notifyNonAvailability(
            cipherId
          );
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
    async changeStatus(id) {},
  },
  mounted() {
    this.getAllAppointmentsByDoctor();
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
