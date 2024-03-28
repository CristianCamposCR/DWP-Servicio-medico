<template>
  <div class="container-fluid mt-4">
    <section class="mx-2">
      <b-row>
        <b-col>
          <h1>Áreas</h1>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="6">
          <b-input-group>
            <b-form-input
              placeholder="Escribe el nombre del área"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block>Buscar</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-col>
        <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
          <div class="d-flex align-items-center mt-2 mt-md-0">
            <span>Agregar nueva área</span> &nbsp;
            <b-button variant="primary"> <b-icon icon="plus" /> </b-button>
          </div>
        </b-col>
      </b-row>
    </section>

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
            class="overflow-hidden mt-3 mx-2 shadow card-animation"
            style="max-width: 270px; max-height: 800px"
            footer-bg-variant="transparent"
            footer-border-variant="white"
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
                      <span v-if="area.description.length > 10">
                        {{
                          showFullDescriptionIndex === index
                            ? area.description
                            : area.description.substring(0, 10) + "..."
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
                </b-card-body>
              </b-col>
            </b-row>
            <template #footer>
              <div>
                <b-button
                  v-if="area.status === EStatus.ACTIVE"
                  @click="area.status = EStatus.INACTIVE"
                  variant="primary"
                >
                  Desactivar
                </b-button>
                <b-button
                  v-else-if="area.status === EStatus.INACTIVE"
                  @click="area.status = EStatus.ACTIVE"
                  variant="danger"
                  >Activar</b-button
                >
                <b-button class="ml-2" variant="primary"
                  ><b-icon icon="eye"></b-icon
                ></b-button>
              </div>
            </template>
          </b-card>
        </b-col>
      </b-row>
    </section>
    <section>
      <b-row class="bg-light m-0 py-3 py-sm-2 py-lg-1">
        <b-col
          cols="12"
          md="3"
          class="d-flex justify-content-center justify-content-md-start"
        >
          <b class="fw-bold"
            >Mostrando
            {{
              pagination.totalRows === 0
                ? 0
                : (pagination.page - 1) * pagination.size + 1
            }}
            a
            {{
              pagination.page * pagination.size > pagination.totalRows
                ? pagination.totalRows
                : pagination.page * pagination.size
            }}
            de {{ pagination.totalRows }} registros</b
          >
        </b-col>

        <b-col
          cols="6"
          md="6"
          class="d-flex align-items-end align-items-md-center justify-content-center"
        >
          <b-pagination
            align="center"
            size="sm"
            class="my-0"
            v-model="pagination.page"
            :total-rows="pagination.totalRows"
            :per-page="pagination.size"
            aria-controls="table-transition-example"
            @input="getAllAreas"
          >
          </b-pagination>
        </b-col>
      </b-row>
    </section>
  </div>
</template>

<script>
import Vue from "vue";
import { EStatus } from "../../../../kernel/types";

export default Vue.extend({
  name: "AreasView",
  data() {
    return {
      docState: "saved",
      showFullDescriptionIndex: -1,
      areas: [
        {
          id: 1,
          name: "Médica",
          especiality: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          status: "ACTIVO",
        },
        {
          id: 2,
          name: "Dental",
          especiality: "Ortodoncia",
          description: "Fusce eget semper lorem, in feugiat libero.",
          status: "ACTIVO",
        },
        {
          id: 3,
          name: "Pediatría",
          especiality: "General",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
          status: "ACTIVO",
        },
        {
          id: 4,
          name: "Cardiología",
          especiality: "Intervencionista",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
          status: "ACTIVO",
        },
        {
          id: 5,
          name: "Ginecología",
          especiality: "Obstetricia",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
          status: "ACTIVO",
        },
        {
          id: 6,
          name: "Oncología",
          especiality: "Médica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
          status: "INACTIVO",
        },
        {
          id: 7,
          name: "Dermatología",
          especiality: "Estética",
          description: "Fusce eget semper lorem, in feugiat libero.",
          status: "ACTIVO",
        },
        {
          id: 8,
          name: "Neurología",
          especiality: "Clínica",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          status: "ACTIVO",
        },
        {
          id: 9,
          name: "Urología",
          especiality: "Andrológica",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
          status: "ACTIVO",
        },
        {
          id: 10,
          name: "Oftalmología",
          especiality: "General",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
          status: "ACTIVO",
        },
        {
          id: 11,
          name: "Endocrinología",
          especiality: "Clínica",
          description: "Fusce eget semper lorem, in feugiat libero.",
          status: "ACTIVO",
        },
        {
          id: 12,
          name: "Reumatología",
          especiality: "General",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
          status: "INACTIVO",
        },
        {
          id: 13,
          name: "Hematología",
          especiality: "Clínica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
          status: "ACTIVO",
        },
        {
          id: 14,
          name: "Nefrología",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
          status: "ACTIVO",
        },
        {
          id: 15,
          name: "Neumología",
          especiality: "General",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          status: "ACTIVO",
        },
        {
          id: 16,
          name: "Nutriología",
          especiality: "Clínica",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
          status: "INACTIVO",
        },
        {
          id: 17,
          name: "Psicología",
          especiality: "Clínica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
          status: "INACTIVO",
        },
        {
          id: 18,
          name: "Psiquiatría",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
          status: "ACTIVO",
        },
        {
          id: 19,
          name: "Traumatología",
          especiality: "General",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          status: "INACTIVO",
        },
        {
          id: 20,
          name: "Cirugía",
          especiality: "General",
          description: "Vestibulum volutpat, risus quis suscipit feugiat.",
          status: "ACTIVO",
        },
        {
          id: 21,
          name: "Gastroenterología",
          especiality: "Clínica",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
          status: "ACTIVO",
        },
        {
          id: 22,
          name: "Odontología",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
          status: "ACTIVO",
        },
        {
          id: 23,
          name: "Otorrinolaringología",
          especiality: "General",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          status: "ACTIVO",
        },
        {
          id: 24,
          name: "Medicina Interna",
          especiality: "General",
          description: "Sed accumsan bibendum felis, ut feugiat est.",
          status: "ACTIVO",
        },
        {
          id: 25,
          name: "Ortopedia",
          especiality: "General",
          description: "Fusce eget semper lorem, in feugiat libero.",
          status: "ACTIVO",
        },
      ],
      EStatus: EStatus,
      pagination: {
        page: 1,
        sort: "id",
        size: 10,
        direction: "desc",
        totalRows: 25,
      },
    };
  },
  methods: {
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
    getAllAreas() {
      console.log("Consiguiendo áreas");
    },
  },
});
</script>
