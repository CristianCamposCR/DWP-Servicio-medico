<template>
  <div id="app">
    <h1 class="title">Perfil</h1>
    <div class="profile-container">
      <div class="profile-section">
        <div class="profile-image"></div>
      </div>
      <div class="profile-section">
        <div class="patient-info">
          <h2>{{ patient.person.name}} {{ patient.person.surname  }} {{ patient.person.lastname }}</h2>
          <p><b>Codigo:</b>  {{ patient.patientCode }}</p>
          <p><b>Email: </b>{{ patient.person.email }}</p>
          <p><b>Teléfono: </b>{{ patient.person.phoneNumber }}</p>
          <p><b>Dirección: </b>{{ patient.person.details ? patient.person.details : 'N/A' }}</p>
        </div>
        <b-button class="custom-button" @click="editProfile">Editar Perfil</b-button>
      </div>
    </div>
    <ModalUpdateProfile :patients="patient" @reloadProfile="getProfile"/>
  </div>
</template>


<script>
import ModalUpdateProfile from './components/ModalUpdateProfile.vue';
import profileController from '../services/controlller/profile.controller'
export default {
  components:{ModalUpdateProfile},
  data() {
    return {
      patient: {},
    };
  },
  methods: {
    editProfile() {
      this.$bvModal.show("modal-update-profile");
    },
    async getProfile() {
      try {
        this.isLoading = true;
        const response = await profileController.getProfile();
        this.patient = response;
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
  },

  mounted() {
    this.getProfile();
  },
};
</script>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #434547;

}

.title {
  position: absolute;
  top: 90px;
  left: 20px;

}

.profile-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;

}

.profile-section {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.custom-button {
  background-color: #10b981;
  border-color: #10b981;
  color: white;
  padding: 10px 20px;
  margin-top: 20px;
  font-size: 16px;
}

.profile-image {
  width: 300px;
  height: 300px;
  background-color: #495157;
  margin-right: 20px;
  border-radius: 15px;

}

.patient-info {
  text-align: left;
  margin-top: 20px;
}

@media (max-width: 768px) {
  .title {
    position: static;
    text-align: center;
  }

  .profile-container {
    flex-direction: column;
    align-items: center;
    height: auto;
  }

  .profile-section {
    margin-bottom: 20px;
  }

  .profile-image {
    margin-right: 0;
    margin-bottom: 20px;
  }
}
</style>