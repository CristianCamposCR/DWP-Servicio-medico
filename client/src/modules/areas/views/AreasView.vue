<template>
  <div class="container-fluid mt-5">
    <b-row>
      <b-col>
        <h1>Áreas</h1>
      </b-col>
    </b-row>
    <b-row>
      <b-col cols="12" md="6">
        <b-input-group>
          <b-input-group-prepend is-text>
            <b-icon icon="search"></b-icon>
          </b-input-group-prepend>

          <b-form-input
            placeholder="Escribe el nombre del hotel"
          ></b-form-input>

          <b-input-group-append>
            <b-button variant="primary" block>Buscar</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
      <b-col cols="12" md="6" class="d-flex justify-content-end">
        <div>
          Agregar nueva área &nbsp;
          <b-button variant="primary"> <b-icon icon="plus" /> </b-button>
        </div>
      </b-col>
    </b-row>

    <section class="mt-5">
      <b-row>
        <b-col
          v-for="(area, index) in areas"
          :key="index"
          cols="12"
          sm="6"
          md="4"
          lg="3"
          xl="2"
          class="d-flex justify-content-center"
        >
          <b-card
            no-body
            class="overflow-hidden mt-3 mx-2"
            style="max-width: 270px; max-height: 800px"
          >
            <b-row no-gutters>
              <b-col md="12">
                <b-card-img
                  src="https://via.placeholder.com/270"
                  alt="Image"
                  class="rounded-0"
                  height="160"
                ></b-card-img>
              </b-col>
              <b-col md="12">
                <b-card-body :title="area.name">
                  <b-card-text>
                    <div class="mb-3">
                      {{ area.especiality }}
                    </div>
                    <div>
                      <span v-if="area.description.length > 20">
                        {{
                          showFullDescriptionIndex === index
                            ? area.description
                            : area.description.substring(0, 20) + "..."
                        }}
                        <a href="#" @click="toggleDescription(index, $event)">
                          {{
                            showFullDescriptionIndex === index
                              ? "Ver menos"
                              : "Ver más"
                          }}
                        </a>
                      </span>
                      <span v-else>{{ area.description }}</span>
                    </div>
                  </b-card-text>
                  <div>
                    <b-button v-if="1 == 1" variant="primary">
                      Desactivar
                    </b-button>
                    <b-button v-else>Activar</b-button>
                    <b-button class="ml-2" variant="primary"
                      ><b-icon icon="eye"></b-icon
                    ></b-button>
                  </div>
                </b-card-body>
              </b-col>
            </b-row>
          </b-card>
        </b-col>
      </b-row>
    </section>
  </div>
</template>

<script>
import Vue from "vue";
export default Vue.extend({
  name: "AreasView",
  data() {
    return {
      showFullDescriptionIndex: -1,
      areas: [
        {
          id: 1,
          name: "Médica",
          especiality: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
        },
        {
          id: 2,
          name: "Dental",
          especiality: "Ortodoncia",
          description: "Fusce eget semper lorem, in feugiat libero.",
        },
        {
          id: 3,
          name: "Pediatría",
          especiality: "General",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
        },
        {
          id: 4,
          name: "Cardiología",
          especiality: "Intervencionista",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
        },
        {
          id: 5,
          name: "Ginecología",
          especiality: "Obstetricia",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
        },
        {
          id: 6,
          name: "Oncología",
          especiality: "Médica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
        },
        {
          id: 7,
          name: "Dermatología",
          especiality: "Estética",
          description: "Fusce eget semper lorem, in feugiat libero.",
        },
        {
          id: 8,
          name: "Neurología",
          especiality: "Clínica",
          description: "Lorem ipsum dolor sit amet, consectetur.",
        },
        {
          id: 9,
          name: "Urología",
          especiality: "Andrológica",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
        },
        {
          id: 10,
          name: "Oftalmología",
          especiality: "General",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
        },
        {
          id: 11,
          name: "Endocrinología",
          especiality: "Clínica",
          description: "Fusce eget semper lorem, in feugiat libero.",
        },
        {
          id: 12,
          name: "Reumatología",
          especiality: "General",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
        },
        {
          id: 13,
          name: "Hematología",
          especiality: "Clínica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
        },
        {
          id: 14,
          name: "Nefrología",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
        },
        {
          id: 15,
          name: "Neumología",
          especiality: "General",
          description: "Lorem ipsum dolor sit amet, consectetur.",
        },
        {
          id: 16,
          name: "Nutriología",
          especiality: "Clínica",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
        },
        {
          id: 17,
          name: "Psicología",
          especiality: "Clínica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
        },
        {
          id: 18,
          name: "Psiquiatría",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
        },
        {
          id: 19,
          name: "Traumatología",
          especiality: "General",
          description: "Lorem ipsum dolor sit amet, consectetur.",
        },
        {
          id: 20,
          name: "Cirugía",
          especiality: "General",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
        },
        {
          id: 21,
          name: "Gastroenterología",
          especiality: "Clínica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
        },
        {
          id: 22,
          name: "Odontología",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
        },
        {
          id: 23,
          name: "Otorrinolaringología",
          especiality: "General",
          description: "Lorem ipsum dolor sit amet, consectetur.",
        },
        {
          id: 24,
          name: "Medicina Interna",
          especiality: "General",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
        },
        {
          id: 25,
          name: "Ortopedia",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
        },
      ],
    };
  },
  methods: {
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
  },
});
</script>
