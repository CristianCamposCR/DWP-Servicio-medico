<template>
  <b-modal id="form" title="Formulario" hide-footer size="lg">
    <form @submit.prevent="">
      <b-row>
        <b-col cols="12" class="my-2">
          <b-form-group label="Nombre de la especialidad:">
            <b-form-input
              id="name"
              v-model="specialityForm.name"
              required
              :class="{
                'input-border-error': !validateName && specialityForm.name.length > 0,
                'input-border-success': validateName,
              }"
              aria-describedby="input-live-help input-live-feedback"
            />

            <div
              v-if="!validateName && specialityForm.name.length > 0"
              class="invalid-feedback"
            >
              Formato inválido
            </div>
          </b-form-group>
        </b-col>

        <b-col cols="12" class="my-2">
          <b-form-group label="Descripción:">
            <b-form-text
              id="description"
              v-model="specialityForm.description"
              rows="3"
              max-rows="6"
              :state="validateDescription"
            ></b-form-text>

            <b-form-invalid-feedback :state="validateDescription">
              No se aceptan caracteres especiales ni espacios en blanco
            </b-form-invalid-feedback>
          </b-form-group>
        </b-col>

        <b-col cols="12" class="my-2">
          <b-form-group label="Costo:">
            <b-form-input
              id="costo"
              v-model="specialityForm.costo"
              required
            ></b-form-input>
          </b-form-group>
        </b-col>

        <b-col cols="12" class="my-2">
          <b-form-group label="Área">
            <b-form-select v-model="selectedSpeciality.id" :options="specialtiesOptions">
              <template #first>
                <b-form-select-option :value="null"
                  >Selecciona una opción</b-form-select-option
                >
              </template>
            </b-form-select>
          </b-form-group>
        </b-col>

        <b-col cols="12">
          <b-row class="d-flex flex-row-reverse">
            <b-button type="submit" variant="success" class="mx-3">{{
              isNew ? "Registrar" : "Actualizar"
            }}</b-button>
            <b-button variant="danger" @click="closeModal">Cancelar</b-button>
          </b-row>
        </b-col>
      </b-row>
    </form>
  </b-modal>
</template>

<script>
export default {
  name: "ModalSaveSpecility",
  props: ["isNew"],
  data() {
    return {
      fullPage: true,
      specialtiesOptions: [],
    };
  },
  computed: {
    specialityForm() {
      if (this.specialityData) {
        return { ...this.specialityData };
        a;
      } else {
        return {
          id: null,
          name: null,
          description: null,
          costo: null,
          area: {
            id: null,
            name: null,
          },
          status: null,
        };
      }
    },

    selectedSpeciality() {
      if (this.specialityData) {
        return { id: this.specialityData.area.id };
      } else {
        return null;
      }
    },

    validateName() {
      const regex = /^[a-zA-Z0-9]+$/;
      return (
        this.specialityData.name.length > 0 &&
        this.specialityData.name.length < 100 &&
        regex.test(this.specialityData.name)
      );
    },

    validateDescription() {
      const regex = /^[a-zA-Z0-9]+$/;
      return (
        this.specialityData.description.length > 0 &&
        this.specialityData.description.length < 100 &&
        regex.test(this.specialityData.description)
      );
    },

    validateCosto() {
      const regex = /^\d+(\.\d{1,2})?$/;
      return regex.test(this.specialityData.costo);
    },

    validateForm() {
      return this.validateName && this.validateDescription;
    },
  },
  methods: {
    closeModal() {
      this.$bvModal.hide("form");
    },
  },
};
</script>
