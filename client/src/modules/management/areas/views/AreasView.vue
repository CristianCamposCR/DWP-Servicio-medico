<template>
  <div class="container-fluid mt-4">
    <loading-custom :isLoading="isLoading" />
    <section class="mx-2 px-5">
      <b-row>
        <b-col>
          <h1 class="title-views">Áreas</h1>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="6">
          <b-input-group>
            <b-form-input
              placeholder="Escribe el nombre del área"
              v-model="pagination.data.name"
              @keyup.enter="getAllAreas"
              class="custom-placeholder"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block @click="getAllAreas"
                >Buscar</b-button
              >
            </b-input-group-append>
          </b-input-group>
        </b-col>
        <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
          <div class="d-flex align-items-center mt-2 mt-md-0">
            <span class="mr-1 area-indicator">Agregar nueva área</span> &nbsp;
            <b-button variant="primary" v-b-modal.modal-save-area>
              <b-icon icon="plus" />
            </b-button>
          </div>
        </b-col>
      </b-row>
    </section>

    <save-area @reloadRegisters="getAllAreas" />
    <section class="mt-4 px-5" v-if="areas.length > 0">
      <b-row>
        <b-col
          v-for="(area, index) in areas"
          :key="index"
          cols="12"
          sm="6"
          md="4"
          lg="3"
          class="d-flex justify-content-center"
        >
          <b-card
            no-body
            class="overflow-hidden mt-3 shadow card-animation"
            style="max-width: 270px; max-height: 800px"
            footer-bg-variant="transparent"
            footer-border-variant="white"
            rounded
          >
            <b-row no-gutters>
              <b-col md="12">
                <b-card-img
                  :src="
                    area.bannerImage
                      ? area.bannerImage
                      : '/src/assets/image/default-area.avif'
                  "
                  alt="Image"
                  class="rounded-0"
                  height="160"
                  style="object-fit: cover; object-position: center"
                ></b-card-img>
              </b-col>
              <b-col md="12">
                <b-card-body>
                  <b-card-title class="card-title mb-0">{{
                    area.name
                  }}</b-card-title>
                  <span class="area-indicator">Área</span>
                  <b-card-text>
                    <div class="mt-3">
                      <div>
                        <span class="area-description-title">Descripción</span>
                      </div>
                      <span
                        v-if="area.description && area.description.length > 50"
                        class="card-description"
                      >
                        {{
                          showFullDescriptionIndex === index
                            ? area.description
                            : area.description.substring(0, 50) + "..."
                        }}
                        <a href="#" @click="toggleDescription(index, $event)">
                          {{
                            showFullDescriptionIndex === index
                              ? "Ver menos"
                              : "Ver más"
                          }}
                        </a>
                      </span>
                      <span
                        v-else-if="area.description"
                        class="card-description"
                        >{{ area.description }}</span
                      >
                    </div>
                  </b-card-text>
                </b-card-body>
              </b-col>
            </b-row>
            <template #footer>
              <div>
                <b-button
                  v-if="area.status.name === EStatus.ACTIVE"
                  @click="changeStatus(area.id)"
                  v-b-tooltip.hover.v-info
                  title="Desactivar"
                  variant="outline-primary"
                >
                  <b-icon icon="toggle-on"></b-icon>
                </b-button>
                <b-button
                  v-else-if="area.status.name === EStatus.INACTIVE"
                  @click="changeStatus(area.id)"
                  v-b-tooltip.hover.v-info
                  title="Activar"
                  variant="outline-danger"
                >
                  <b-icon icon="toggle-off"></b-icon>
                </b-button>
                <b-button
                  class="ml-3"
                  variant="outline-secondary"
                  v-b-tooltip.hover.v-info
                  title="Editar"
                  ><b-icon icon="pencil" @click="getOne(area.id)"></b-icon
                ></b-button>
              </div>
            </template>
          </b-card>
        </b-col>
      </b-row>
    </section>
    <section class="mt-4" v-if="areas.length > 0">
      <b-row
        class="m-0 py-3 py-sm-2 py-lg-1 mb-2 d-flex justify-content-center"
      >
        <b-col
          cols="6"
          md="6"
          class="d-flex align-items-end align-items-md-center justify-content-center"
        >
          <b-pagination
            align="center"
            size="md"
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

    <section class="mt-1" v-if="areas.length === 0">
      <no-registers :message="'áreas'" />
    </section>

    <update-area :areaSelected="areaSelected" @reloadRegisters2="getAllAreas" />
  </div>
</template>

<script>
import Vue, { defineAsyncComponent } from "vue";
import { EStatus } from "../../../../kernel/types";
import areaController from "../services/controller/area.controller";
import { encrypt } from "../../../../kernel/hashFunctions";
import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";

export default Vue.extend({
  name: "AreasView",
  components: {
    SaveArea: defineAsyncComponent(() => import("./components/SaveArea.vue")),
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),
    UpdateArea: defineAsyncComponent(() =>
      import("./components/UpdateArea.vue")
    ),
    NoRegisters: defineAsyncComponent(() =>
      import("../../../../views/components/NoRegisters.vue")
    ),
  },
  mounted() {
    this.getAllAreas();
  },
  data() {
    return {
      isLoading: false,
      showFullDescriptionIndex: -1,
      areas: [],
      EStatus: EStatus,
      pagination: {
        page: 1,
        sort: "id",
        size: 8,
        direction: "desc",
        totalRows: 0,
        data: {
          name: null,
        },
      },
      areaSelected: {},
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
        this.isLoading = true;
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
      } finally {
        this.isLoading = false;
      }
    },

    async changeStatus(id) {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          const cipherId = await encrypt(id);
          const resp = await areaController.changeStatus(cipherId);
          const { error } = resp;
          if (!error) {
            this.getAllAreas();
            setTimeout(() => {
              SweetAlertCustom.successMessage();
            }, 100);
            return;
          }
        }
      } catch (error) {
        console.log(error);
      }
    },

    async getOne(id) {
      try {
        const cipherId = await encrypt(id);
        const resp = await areaController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          this.areaSelected = resp;
          this.$bvModal.show("modal-update-area");
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
});
</script>
