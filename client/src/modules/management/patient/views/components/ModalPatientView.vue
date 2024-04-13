<template>
  <div>
    <b-modal hide-footer hide-header centered id="modal-patient-view">
      <header class="text-center border-bottom" style="margin-bottom: 20px">
        <h4 style="font-family: 'Arial', sans-serif; color: #333">
          Detalles del Paciente
        </h4>
      </header>
      <main>
        <div class="container-fluid">
          <div class="text-center">
            <img
              :src="srcImageSelected ? srcImageSelected : ''"
              alt="Foto de Perfil"
              class="profile-pic"
            />
            <h4 class="profile-name">
              {{ patient.person?.name }} {{ patient.person?.surname }}
              {{ patient.person?.lastname }}
            </h4>
          </div>
          <div class="row mt-4">
            <div class="col-md-6">
              <div class="detail-item">
                <p class="detail-label">Estado:</p>
                <p class="detail-value">
                  {{ patient.person?.user?.status.name }}
                </p>
              </div>
              <div class="detail-item">
                <p class="detail-label">Género:</p>
                <p class="detail-value">{{ patient.person?.gender.name }}</p>
              </div>
              <div class="detail-item">
                <p class="detail-label">CURP:</p>
                <p class="detail-value">{{ patient.person?.curp }}</p>
              </div>
            </div>
            <div class="col-md-6">
              <div class="detail-item">
                <p class="detail-label">Fecha de Nacimiento:</p>
                <p class="detail-value">{{ patient.person?.birthday }}</p>
              </div>
              <div class="detail-item">
                <p class="detail-label">Teléfono:</p>
                <p class="detail-value">{{ patient.person?.phoneNumber }}</p>
              </div>
              <div class="detail-item">
                <p class="detail-label">Correo Electrónico:</p>
                <p class="detail-value">{{ patient.person?.email }}</p>
              </div>
            </div>
          </div>
        </div>
      </main>
      <footer class="text-center mt-5">
        <button class="btn btn-primary" @click="onClose">Aceptar</button>
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
      required: true,
    },
    srcImageSelected:{
      type: String,
      required: true,
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
.profile-pic {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 10px;
  margin: 0 auto; /* Centrar la imagen */
}

.profile-name {
  font-family: "Arial", sans-serif;
  color: #333;
  margin-bottom: 20px;
}

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
