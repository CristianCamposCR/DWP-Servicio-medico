<template>
  <div>
    <b-modal
      hide-footer
      :no-close-on-backdrop="true"
      id="doctor-modal-details"
      title="Detalles del Doctor"
      centered
    >
      <div class="modal-container">
        <div class="doctor-details fullscreen">
          <div class="modal-content">
            <div class="profile-section">
              <img
                class="profile-picture"
                :src="
                  doctor && doctor.person?.profilePhoto
                    ? doctor.person?.profilePhoto
                    : 'https://elcomercio.pe/resizer/gj5JbwxkmqRAa4HSpfOHEIUBf7k=/580x330/smart/filters:format(jpeg):quality(75)/cloudfront-us-east-1.images.arcpublishing.com/elcomercio/6FUBT6XQXNHHNFOMCHIT7I34NA.jpg'
                "
                alt="Profile Picture"
              />
            </div>
          </div>
          <div class="info-section">
            <div class="header">
              <h1 class="doctor-name">
                {{ doctor.person?.name }} {{ doctor.person?.surname }}
                {{ doctor.person?.lastname }}
              </h1>
              <p class="specialty">{{ doctor.speciality?.name }}</p>
            </div>
            <div class="details">
              <div class="personal-info">
                <div class="info-item">
                  <strong>Edad:</strong>
                  {{ calculateAge(doctor.person?.birthday) }}
                </div>
                <div class="info-item">
                  <strong>Género:</strong> {{ doctor.person?.gender?.name }}
                </div>
                <div class="info-item">
                  <strong>Email:</strong> {{ doctor.person?.email }}
                </div>
                <div class="info-item">
                  <strong>Teléfono:</strong> {{ doctor.person?.phoneNumber }}
                </div>
                <div class="info-item">
                  <strong>Turno:</strong> {{ doctor.shift?.name }}
                </div>
                <div class="info-item">
                  <strong>Experiencia:</strong> {{ doctor.experience }} años
                </div>
              </div>
              <div class="professional-info">
                <div class="info-item">
                  <strong>CURP:</strong> {{ doctor.person?.curp }}
                </div>

                <div class="info-item">
                  <strong>¿Es Auxiliar?</strong>
                  {{ doctor.isAux ? "Sí" : "No" }}
                </div>

                <div class="info-item">
                  <strong>Días Disponibles:</strong>
                  <li v-for="day in daysTranslates" :key="day">{{ day }}</li>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <footer class="text-center mt-5">
        <div class="btn-group">
          <b-button variant="danger" class="mr-4" @click="onClose"
            >Cerrar</b-button
          >
          <b-button variant="primary"> Aceptar </b-button>
        </div>
      </footer>
    </b-modal>
  </div>
</template>
<script>
export default {
  name: "DoctorModal",
  props: {
    doctor: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      daysTranslate: {
        MONDAY: "Lunes",
        TUESDAY: "Martes",
        WEDNESDAY: "Miércoles",
        THURSDAY: "Jueves",
        FRIDAY: "Viernes",
        SATURDAY: "Sábado",
        SUNDAY: "Domingo"
      },
    };
  },
  computed: {
    daysTranslates() {
      const cleanedDays = this.doctor.availableDays
        ? this.doctor.availableDays.replace("[", "").replace("]", "")
        : "";
      const days = cleanedDays.split(", ");
      return days.map((day) => this.daysTranslate[day]);
    },
  },
  methods: {
    calculateAge(birthday) {
      const today = new Date();
      const birthDate = new Date(birthday);
      let age = today.getFullYear() - birthDate.getFullYear();
      const month = today.getMonth() - birthDate.getMonth();
      if (month < 0 || (month === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      return age;
    },
    onClose() {
      this.$bvModal.hide("doctor-modal-details");
    },
  },
};
</script>

<style scoped>
.modal-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 999;
}

.modal-content {
  max-width: 600px;
  width: 100%;
  background-color: #10b981;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  z-index: 1000;
}

.profile-section {
  width: 100%;
}

.profile-picture {
  width: 100%;
  border-radius: 10px;
}

.info-section {
  margin-top: 20px;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.doctor-name {
  font-size: 24px;
  color: #333;
}

.specialty {
  color: #2ecc71;
  font-size: 18px;
}

.details {
  display: flex;
  justify-content: space-between;
}

.personal-info,
.professional-info {
  text-align: left;
}

.info-item {
  margin-bottom: 10px;
}

.info-item strong {
  margin-right: 5px;
}

.section-title {
  margin-top: 20px;
  font-size: 20px;
  color: #555;
}
</style>
