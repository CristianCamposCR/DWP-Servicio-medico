<template>
  <div class="container-fluid mt-4">
    <section class="mx-2">
      <b-row>
        <b-col>
          <h1>Especialidades</h1>
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
            <span>Agregar nueva especialidad</span> &nbsp;
            <b-button class="custom-button" @click="saveSpeciality" variant="primary">
              <b-icon icon="plus"/>
            </b-button>
          </div>
        </b-col>
      </b-row>
    </section>
    <ModalSaveSpeciality :specialties="specialties" :isNew="isNewSpeciality"/>  

    <section class="mt-5">
      <b-row>
        <b-col
          v-for="(speciality, index) in specialties"
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
                <b-card-body :title="speciality.name">
                  <div class="mb-3">
                    <font color="#848689"
                      >Especialidad / Área {{ speciality.area.name }}</font
                    >
                  </div>
                  <b-card-text>
                    <strong> Description: </strong>
                    <div>
                      <span v-if="speciality.description.length > 10">
                        {{
                          showFullDescriptionIndex === index
                            ? speciality.description
                            : speciality.description.substring(0, 10) + "..."
                        }}
                        <a href="#" @click="toggleDescription(index, $event)">
                          {{
                            showFullDescriptionIndex === index
                              ? "Ver menos"
                              : "Ver más"
                          }}
                        </a>
                      </span>
                      <span v-else>{{ speciality.description }}</span>
                      <div class="mb-3">
                        <strong>Costo:</strong> {{ speciality.costo }}
                      </div>
                      <div class="mb-3">
                        <strong>Área:</strong>{{ speciality.area.name }}
                      </div>
                    </div>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
            <template #footer>
              <div>
                <b-button
                  v-if="speciality.status === EStatus.ACTIVE"
                  @click="speciality.status = EStatus.INACTIVE"
                  variant="primary"
                >
                  Desactivar
                </b-button>
                <b-button
                  v-else-if="speciality.status === EStatus.INACTIVE"
                  @click="speciality.status = EStatus.ACTIVE"
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
            @input="getAllSpecialties"
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

import ModalSaveSpeciality from "./ModalSaveSpeciality.vue"

export default Vue.extend({
  components: { ModalSaveSpeciality },
  name: "SpecialtiesView",
  data() {
    return {
      showElement: true,
      docState: "saved",
      showFullDescriptionIndex: -1,
      specialties: [
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
          status: "ACTIVO",
        },
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
          status: "ACTIVO",
        },
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
          status: "ACTIVO",
        },
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
          status: "ACTIVO",
        },
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
          status: "ACTIVO",
        },
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
          status: "ACTIVO",
        },
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
          status: "ACTIVO",
        },
        {
          id: 1,
          name: "Familiar",
          description: "Lorem ipsum dolor sit amet, consectetur.",
          costo: 1534.54,
          area: {
            id: 1,
            name: "Médica",
          },
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
    saveSpeciality(){
      console.log("saveSpeciality method called");
      this.$bvModal.show("form");
      this.isNewSpeciality = true;
    },

    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },

    getAllSpecialties() {
      console.log("Consiguiendo áreas");
    },
  },
});
</script>
