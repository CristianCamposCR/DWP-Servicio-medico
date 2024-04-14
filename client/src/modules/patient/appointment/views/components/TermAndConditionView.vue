<template>
  <b-modal id="modal-scrollable" scrollable title="Términos y Condiciones" v-model="isVisible"
           @close="close" @hide="close" @hidden="close" @ok="ok" @cancel="()=>{this.close() }"
           no-close-on-backdrop
           no-close-on-esc
            ok-title="Aceptar" cancel-title="Cerrar" centered
  >

    <div class="text-justify text-black-50" style="font-size: 13px">
      <p>
        Estos términos y condiciones regulan el uso de los servicios de citas médicas de la clínica CIMI, en adelante
        denominada "la Clínica". Al utilizar nuestros servicios, usted acepta cumplir con estos términos y condiciones
        en
        su totalidad. Lea detenidamente los siguientes términos antes de programar una cita.
      </p>

      <p class="font-weight-bold m-0">Reserva de Citas:</p>

      <div>
        <p class="m-0">Todas las citas deben ser programadas con anticipación a través de nuestro sistema de reservas en
          línea.</p>
        <p class="m-0">La disponibilidad de citas está sujeta a la agenda de los médicos y la disponibilidad de las
          instalaciones de
          la Clínica.</p>
      </div>

      <p class="font-weight-bold m-0 mt-3">Confirmación de Citas:</p>
      <p class="m-0">Se enviará una confirmación de la cita a la dirección de correo electrónico proporcionada al
        momento que se le
        sea asignado un médico.</p>
      <p class="m-0">Es responsabilidad del paciente verificar la fecha y hora de la cita programada.</p>

      <p class="font-weight-bold m-0 mt-3">Cancelación y Reembolso:</p>
      <p class="m-0">Si el paciente cancela la cita con anticipación, se reembolsará el <span class="border-bottom border-dark">50%</span> del pago realizado.</p>
      <p class="m-0">En el caso de que la Clínica cancele una cita programada, se reembolsará el <span class="border-bottom border-dark">100%</span> del pago
        realizado.</p>

      <p class="font-weight-bold m-0 mt-3">Ausencia del Paciente:</p>
      <p class="m-0">Si el paciente no se presenta a la cita sin previo aviso, <span class="border-bottom border-dark">no se realizará ningún reembolso</span> y se
        considerará como
        una consulta realizada.</p>

      <p class="font-weight-bold m-0 mt-3">Reagendamiento:</p>
      <p class="m-0">El paciente tiene permitido reagendar <span class="border-bottom border-dark">una cita una vez sin costo adicional</span>, siempre que se realice
        con anticipación.</p>
      <p class="m-0">Los reagendamientos adicionales estarán sujetos a disponibilidad y podrían estar sujetos a cargos
        adicionales.</p>

      <p class="font-weight-bold m-0 mt-3">Cambios en las Políticas:</p>
      <p class="my-1">La Clínica se reserva el derecho de modificar estos términos y condiciones en cualquier momento sin
        previo aviso. Se recomienda revisar regularmente estos términos para estar al tanto de cualquier cambio.</p>
      <p class="m-0">Al utilizar nuestros servicios de citas médicas, usted acepta estos términos y condiciones en su
        totalidad. Si no está de acuerdo con alguno de estos términos, le recomendamos que no programe una cita en
        nuestra Clínica.</p>

      <p class=" m-0 mt-3">
        <span class="font-weight-bold">Fecha de entrada en vigencia:</span> <span>12 de enero de 2024</span>
      </p></div>
  </b-modal>
</template>

<script>
export default {
  name: 'TermAndConditionView',
  props: {
    show: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      isVisible: this.show
    }
  },
  watch: {
    show(val) {
      this.isVisible = val
    }
  },
  methods: {
    close() {
      this.$emit('close')
    },
    ok() {
      this.$emit('ok')
      this.close()
    }
  }
}

</script>

<style scoped>

</style>