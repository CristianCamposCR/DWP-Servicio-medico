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
              v-model="pagination.data.name"
              @keyup.enter="getAllAreas"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block>Buscar</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-col>
        <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
          <div class="d-flex align-items-center mt-2 mt-md-0">
            <span class="mr-1">Agregar nueva área</span> &nbsp;
            <b-button variant="primary" v-b-modal.modal-save-area>
              <b-icon icon="plus" />
            </b-button>
          </div>
        </b-col>
      </b-row>
    </section>

    <save-area />
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
                      <span
                        v-if="area.description && area.description.length > 10"
                      >
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
                      <span v-else-if="area.description">{{
                        area.description
                      }}</span>
                    </div>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
            <template #footer>
              <div>
                <b-button
                  v-if="area.status.name === EStatus.ACTIVE"
                  @click="area.status.name = EStatus.INACTIVE"
                  variant="primary"
                >
                  Desactivar
                </b-button>
                <b-button
                  v-else-if="area.status.name === EStatus.INACTIVE"
                  @click="area.status.name = EStatus.ACTIVE"
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
    <section class="mt-4">
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
import Vue, { defineAsyncComponent } from "vue";
import { EStatus } from "../../../../kernel/types";
import areaController from "../services/controller/area.controller";

export default Vue.extend({
  name: "AreasView",
  components: {
    SaveArea: defineAsyncComponent(() => import("./components/SaveArea.vue")),
  },
  mounted() {
    this.getAllAreas();
  },
  data() {
    return {
      docState: "saved",
      showFullDescriptionIndex: -1,
      areas: [],
      EStatus: EStatus,
      pagination: {
        page: 1,
        sort: "id",
        size: 10,
        direction: "desc",
        totalRows: 0,
        data: {
          name: null,
        },
      },
    };
  },
  methods: {
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
    async getAllAreas() {
      try {
        const response = await areaController.getAllAreas({
          page: this.pagination.page - 1,
          size: this.pagination.size,
          sort: this.pagination.sort,
          direction: this.pagination.direction,
          data: this.pagination.data,
        });
        this.areas = response.content;
        this.pagination.totalRows = response.totalElements;
      } catch (error) {
        console.log(error);
      }
    },
  },
});
</script>
