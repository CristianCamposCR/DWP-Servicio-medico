<template>
  <div id="app">
    <h1 class="title">Perfil</h1>
    <div class="profile-container">
      <div class="profile-section">
        
        <div class="profile-image"></div>
      </div>
      <div class="profile-section">
        <div class="user-info">
          <h2>{{ user.name }}</h2>
          <p>Email: {{ user.email }}</p>
          <p>Teléfono: {{ user.phone }}</p>
          <p>Dirección: {{ user.address }}</p>
        </div>
        <b-button class="custom-button" @click="editProfile">Editar Perfil</b-button>
      </div>
    </div>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #434547;
  margin-top: 60px;
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
  font-size: 20px;
  padding: 10px 20px;
}

.profile-image {
  width: 300px; 
  height: 300px; 
  background-color: #495157;
  margin-right: 20px;
  border-radius: 15px; 
}

.user-info {
  text-align: left;
  margin-top: 20px;
}

button {
  margin-top: 20px;
  padding: 10px;
  font-size: 16px;
}
</style>

<script>
export default {
  data() {
    return {
      user: {
        name: "Maycon Carmona Medina",
        email: "20213tn004@utez.edu.mx",
        phone: "777-200-2582",
        address: "La huizachera",
      },
    };
  },
  methods: {
    editProfile() {
      alert("Editar información del perfil");
    },
    getProfile(){
      alert("consumo de perfil")
    }
  },

  mounted() {
    this.getProfile();
  },
};
</script>
