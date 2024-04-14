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
              @keyup.enter="getAllAppointments"
              class="custom-placeholder"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block @click="getAllAppointments"
                >Buscar</b-button
              >
            </b-input-group-append>
          </b-input-group>
        </b-col>
      </b-row>
    </section>

    <section class="mx-2 px-5">
      <b-table
        bordered
        outlined
        hover
        :items="appointments"
        :fields="fields"
        class="mt-4 shadow appointments-table"
      >
        <template #cell(actions)="data">
          <b-button @click="viewAppointment(data.item)" variant="primary"
            >Ver</b-button
          >
          <b-button @click="updateAppointment(data.item)" variant="info"
            >Actualizar</b-button
          >
          <b-button @click="deactivateAppointment(data.item)" variant="danger"
            >Desactivar</b-button
          >
        </template>
      </b-table>
    </section>

    <section class="mt-4">
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
            @input="getAllAppointments"
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
import Vue, { defineAsyncComponent } from "vue";
import { EStatus } from "../../../../kernel/types";
import { encrypt } from "../../../../kernel/hashFunctions";
import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";
import appointmentsController from "../services/controller/appointments.controller";
export default Vue.extend({
  components: {
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),

    NoRegisters: defineAsyncComponent(() =>
      import("../../../../views/components/NoRegisters.vue")
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
      appointments: [],
      fields: [
        { key: "patient", label: "Paciente" },
        { key: "speciality", label: "Especialidad" },
        { key: "date", label: "Fecha" },
        { key: "doctor", label: "Doctor" },
        { key: "turno", label: "Turno" },
        { key: "tipo", label: "Tipo" },
        { key: "hora", label: "Hora" },
        { key: "status", label: "Estado" },
        { key: "actions", label: "Acciones" },
      ],
    };
  },
  methods: {
    async getAllAppointments() {
      try {
        this.isLoading = true;
        const response = await appointmentsController.getAllAppointments({
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
      // Lógica para ver la cita
    },
    async updateAppointment(appointment) {
      console.log("Actualizar cita:", appointment);
      // Lógica para actualizar la cita
    },
    async deactivateAppointment(appointment) {
      console.log("Desactivar cita:", appointment);
      // Lógica para desactivar la cita
    },
    async changeStatus(id) {},
  },
  mounted() {
    this.getAllAppointments();
  },
});
</script>

<style>
.appointments-table tbody tr {
  background-color: #f2f2f2; /* Color gris para las filas */
}
</style>
