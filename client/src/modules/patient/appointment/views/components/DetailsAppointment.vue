<template>
  <div>
    <b-modal
      id="details-appointment"
      :no-close-on-backdrop="true"
      centered
      hide-footer
      @hidden="onClose"
      @close="onClose"
      title="Detalles de la Cita"
      size="lg"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-6">
            <div class="info-section">
              <div class="info-header">
                <h5>Información del Paciente</h5>
              </div>
              <div class="info-content">
                <p>
                  <strong>Nombre:</strong>
                  {{ appointmentData.patient.person.name }}
                  {{ appointmentData.patient.person.surname }}
                  {{ appointmentData.patient.person.lastname }}
                </p>
                <p>
                  <strong>Código:</strong>
                  {{ appointmentData.patient.patientCode }}
                </p>
                <p>
                  <strong>Email:</strong>
                  {{ appointmentData.patient.person.email }}
                </p>
                <p>
                  <strong>Teléfono:</strong>
                  {{ appointmentData.patient.person.phoneNumber }}
                </p>
                <p>
                  <strong>Sexo:</strong>
                  {{ appointmentData.patient.person.gender.name }}
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="info-section">
              <div class="info-header">
                <h5>Información del Médico</h5>
              </div>
              <div class="info-content">
                <p v-if="appointmentData.doctor">
                  <strong>Nombre:</strong>
                  {{ appointmentData.doctor.person.name }}
                  {{ appointmentData.doctor.person.surname }}
                  {{ appointmentData.doctor.person.lastname }}
                </p>
                <p v-else>
                  <strong>No se ha asignado aún un doctor</strong>
                </p>
                <p v-if="appointmentData.doctor">
                  <strong>Email:</strong>
                  {{ appointmentData.doctor.person.email }}
                </p>
                <p v-if="appointmentData.doctor">
                  <strong>Teléfono:</strong>
                  {{ appointmentData.doctor.person.phoneNumber }}
                </p>
                <p v-if="appointmentData.doctor">
                  <strong>Sexo:</strong>
                  {{ appointmentData.doctor.person.gender.name }}
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-12">
            <div class="info-section">
              <div class="info-header">
                <h5>Detalles de la Cita</h5>
              </div>
              <div class="info-content">
                <p><strong>Folio:</strong> {{ appointmentData.folio }}</p>
                <p><strong>Duración:</strong> {{ appointmentData.duration }}</p>
                <p><strong>Fecha:</strong> {{ appointmentData.scheduledAt }}</p>
                <p v-if="appointmentData.scheduledHour">
                  <strong>Hora:</strong> {{ appointmentData.scheduledHour }}
                </p>
                <p>
                  <strong>Reservas Restantes:</strong>
                  {{ appointmentData.remainingReschedules }}
                </p>
                <p>
                  <strong>Estado:</strong> {{ appointmentData.status.name }}
                </p>
                <p>
                  <strong>Especialidad:</strong>
                  {{ appointmentData.speciality.name }}
                </p>
                <p>
                  <strong>Tipo de Cita:</strong>
                  {{ appointmentData.appointmentType.name }}
                </p>
                <p>
                  <strong>Turno Preferencial:</strong>
                  {{ appointmentData.preferentialShift.name }}
                </p>
                <p v-if="appointmentData.cancellationReason">
                  <strong>Razón de Cancelación:</strong>
                  {{ appointmentData.cancellationReason }}
                </p>
                <p v-if="appointmentData.records">
                  <strong>Registros:</strong> {{ appointmentData.records }}
                </p>
                <p>
                  <strong>Total:</strong> {{ appointmentData.payment.total }}
                </p>
                <p>
                  <strong>Total Pagado:</strong>
                  {{ appointmentData.payment.totalPaid }}
                </p>
                <p>
                  <strong>Descuento:</strong>
                  {{ appointmentData.payment.discount }}
                </p>
                <p v-if="appointmentData.payment.refundedAmount">
                  <strong>Monto Reembolsado:</strong>
                  {{ appointmentData.payment.refundedAmount }}
                </p>
                <p>
                  <strong>Estado del Pago:</strong>
                  {{ appointmentData.payment.status.name }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row mt-4 px-md-5 justify-content-center">
        <div class="col-md-4 mb-3 mb-md-0">
          <b-button variant="danger" @click="onClose" block>Cancelar</b-button>
        </div>
        <div class="col-md-4 mb-3 mb-md-0">
          <b-button variant="success" @click="onClose" block>Aceptar</b-button>
        </div>
      </div>
    </b-modal>
  </div>
</template>

<script>
export default {
  name: "DoctorModal",
  props: {
    appointmentData: {
      type: Object,
      required: true,
    },
  },
  methods: {
    onClose() {
      this.$bvModal.hide("details-appointment");
    },
  },
};
</script>

<style scoped>
.info-section {
  margin-bottom: 20px;
}

.info-header {
  background-color: #10B981;
  color: #fff;
  padding: 10px;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.info-header h5 {
  margin: 0;
}

.info-content p {
  margin-bottom: 5px;
}

@media (max-width: 767.98px) {
  .info-header {
    font-size: 1.2rem;
  }
}
</style>
