<template>
    <div>
      <b-modal hide-footer hide-header centered id="modal-patient-view">
        <header class="text-center border-bottom" style="margin-bottom: 20px;">
          <h4 style="font-family: 'Arial', sans-serif; color: #333;">Detalles del Paciente</h4>
        </header>
        <main>
          <div class="container-fluid">
            <div class="row">
              <div class="col-md-6">
                <div class="detail-item">
                  <p class="detail-label">Nombre:</p>
                  <p class="detail-value"> paciente.nombre </p>
                </div>
                <div class="detail-item">
                  <p class="detail-label">Estado:</p>
                  <p class="detail-value"> paciente.estado </p>
                </div>
                <div class="detail-item">
                  <p class="detail-label">Género:</p>
                  <p class="detail-value"> paciente.genero </p>
                </div>
                <div class="detail-item">
                  <p class="detail-label">Dirección:</p>
                  <p class="detail-value"> paciente.direccion </p>
                </div>
              </div>
              <div class="col-md-6">
                <div class="detail-item">
                  <p class="detail-label">Fecha de Nacimiento:</p>
                  <p class="detail-value"> paciente.fechaNacimiento</p>
                </div>
                <div class="detail-item">
                  <p class="detail-label">Teléfono:</p>
                  <p class="detail-value"> paciente.telefono </p>
                </div>
                <div class="detail-item">
                  <p class="detail-label">Correo Electrónico:</p>
                  <p class="detail-value"> paciente.email </p>
                </div>
                
              </div>
            </div>
          </div>
        </main>
        <footer class="text-center mt-5">
          <button class="btn btn-primary" @click="onClose">Cerrar</button>
        </footer>
      </b-modal>
    </div>
  </template>
  
  <script>
  export default {
    name: "ModalPatientView",
    props: {
      patient: {
        type: Object,
        required: true
      }
    },
    methods: {
      onClose() {
        this.$bvModal.hide("modal-patient-view");
      },
    },
  };
  </script>
  
  <style scoped>
  .detail-item {
    margin-bottom: 20px;
  }
  
  .detail-label {
    font-weight: bold;
    margin-bottom: 5px;
    color: #666;
  }
  
  .detail-value {
    color: #333;
  }
  </style>
  