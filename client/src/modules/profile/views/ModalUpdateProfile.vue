<template>
  <div>
    <b-modal hide-footer hide-header centered id="modal-update-profile">
      <header class="text-center border-bottom" style="margin-bottom: 20px">
        <h4 style="font-family: 'Arial', sans-serif; color: #333">
          Actualizar Perfil
        </h4>
      </header>
      <main>
        <b-tabs content-class="mt-3">
          <b-tab title="Información Personal">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="name" class="detail-label">Nombre:</label>
                    <input
                      type="text"
                      class="form-control"
                      id="name"
                      v-model="user.name"
                    />
                  </div>
                  <div class="form-group">
                    <label for="lastname" class="detail-label"
                      >Apellido paterno:</label
                    >
                    <input
                      type="text"
                      class="form-control"
                      id="lastname"
                      v-model="user.lastname"
                    />
                  </div>
                  <div class="form-group">
                    <label for="surname" class="detail-label"
                      >Apellido materno:</label
                    >
                    <input
                      type="text"
                      class="form-control"
                      id="surname"
                      v-model="user.surname"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="curp" class="detail-label">CURP:</label>
                    <input
                      type="text"
                      class="form-control"
                      id="curp"
                      v-model="user.curp"
                    />
                  </div>

                  <div class="form-group">
                    <label for="gender" class="detail-label">Género:</label>
                    <select
                      class="form-control"
                      id="gender"
                      v-model="user.gender"
                    >
                      <option value="Masculino">Masculino</option>
                      <option value="Femenino">Femenino</option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
          </b-tab>
          <b-tab title="Información Adicional">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="birthdate" class="detail-label"
                      >Fecha de Nacimiento:</label
                    >
                    <input
                      type="date"
                      class="form-control"
                      id="birthdate"
                      v-model="user.birthdate"
                    />
                  </div>

                  <div class="form-group">
                    <label for="phone" class="detail-label">Teléfono:</label>
                    <input
                      type="text"
                      class="form-control"
                      id="phone"
                      v-model="user.phone"
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="email" class="detail-label"
                      >Correo Electrónico:</label
                    >
                    <input
                      type="email"
                      class="form-control"
                      id="email"
                      v-model="user.email"
                    />
                  </div>
                </div>
              </div>
            </div>
          </b-tab>
        </b-tabs>
      </main>
      <footer class="text-center mt-5">
        <div class="btn-group">
          <b-button variant="danger" class="mr-4" @click="onClose">Cerrar</b-button>
          <b-button variant="primary" @click="guardarCambios">
            Guardar
          </b-button>
        </div>
      </footer>
    </b-modal>
  </div>
</template>

<script>
export default {
  name: "ModalUpdateProfile",
  props: {
    user: {
      type: Object,
      required: true,
    },
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-update-profile");
    },
    guardarCambios() {
      console.log("Guardando cambios del usuario:", this.user);
      this.onClose();
    },
  },
}
</script>

<style scoped>
.detail-label {
  font-weight: bold;
  margin-bottom: 5px;
  color: #666;
}
</style>
