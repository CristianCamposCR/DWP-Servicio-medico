<template>
  <div>
    <div>
      <b-modal hide-footer hide-header centered id="modal-save-area">
        <header class="text-center border-bottom">
          <p style="font-family: cabin">Registrar Area</p>
        </header>
        <main>
          <form id="registrarArea">
            <b-row>
              <b-col>
                <label for="area">Nombre del area: *</label>
                <b-form-input
                  v-model="area.name"
                  type="text"
                  class="form-control"
                  placeholder="Area..."
                  required
                  :class="{
                    'input-border-error': !validateName && area.name.length > 0,
                    'input-border-success': validateName,
                  }"
                  aria-describedby="input-live-help input-live-feedback"
                />
                <div
                  v-if="!validateName && area.name.length > 0"
                  class="invalid-feedback"
                >
                  Formato inválido
                </div>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <label for="area">Descripción del area: *</label>
                <b-form-textarea
                  id="textarea"
                  v-model="area.description"
                  placeholder="Describe el area..."
                  rows="3"
                  max-rows="6"
                  :state="validateDescription"
                ></b-form-textarea>

                <b-form-invalid-feedback :state="validateDescription">
                  No se aceptan caracteres especiales ni espacios en blanco
                </b-form-invalid-feedback>
              </b-col>
            </b-row>
            <b-row>
              <b-col>
                <label for="area">Imagen del area: *</label>
                <b-form-file
                  v-model="area.image"
                  :state="Boolean(area.image)"
                  accept="image/*"
                  placeholder="Escoge una imagen"
                  drop-placeholder="Escoge una imagen"
                  browse-text="Buscar"
                ></b-form-file>
              </b-col>
            </b-row>
          </form>
        </main>
        <footer class="text-center mt-5">
          <button class="btn m-1 cancel" @click="onClose">Cancelar</button>
          <button
            class="btn m-1 success"
            @click="save"
            :disabled="!validateForm"
            type="submit"
          >
            Registrar
          </button>
        </footer>
      </b-modal>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
export default {
  name: "modal-save-area",

  data() {
    return {
      area: {
        name: "",
        description: "",
        image: null,
      },
    };
  },
  methods: {
    onClose() {
        console.log(this.area);
      this.$bvModal.hide("modal-save-area");
      this.area.name = "";
      this.area.description = "";
      this.area.image = null;
    },
    async save() {
      Swal.fire({
        title: "¿Estás seguro de registrar etsa Area?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#008c6f",
        cancelButtonColor: "#e11c24",
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            console.log(this.area);
            Swal.fire({
              title: "¡Guardada!",
              text: "El area se registró correctamente",
              icon: "success",
            });
            this.onClose();
            // this.$emit("area-updated");
          } catch (error) {
            console.log("Error al registrar el area", error);
          }
        }
      });
    },
  },
  computed: {
    validateName() {
      const regex = /^[a-zA-Z0-9]+$/;
      return (
        this.area.name.length > 0 &&
        this.area.name.length < 100 &&
        regex.test(this.area.name)
      );
    },
    validateDescription() {
      const regex = /^[a-zA-Z0-9]+$/;
      return (
        this.area.description.length > 0 &&
        this.area.description.length < 100 &&
        regex.test(this.area.description)
      );
    },
    validateForm() {
      return this.validateName && this.validateDescription;
    },
  },
};
</script>

<style scoped>
.success {
  font-family: Cabin;
  background-color: #009475;
  color: white;
}

.cancel {
  font-family: Cabin;
  background-color: brown;
  color: black;
}
.input-border-error {
  border: 1px solid red !important;
}

.input-border-success {
  border: 1px solid green !important;
}
</style>
