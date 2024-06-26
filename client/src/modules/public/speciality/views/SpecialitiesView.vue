<template>
  <div>
    <div class="container-fluid mt-4">
      <loading-custom :isLoading="isLoading" />
      <section class="mx-2 px-5">
        <b-row>
          <b-col>
            <h1 class="title-views">Especialidades</h1>
          </b-col>
        </b-row>
        <b-row>
          <b-col cols="12" md="6">
            <b-input-group>
              <b-form-input
                placeholder="Escribe el nombre de la especialidad"
                v-model="pagination.data.name"
                @keyup.enter="getAllSpecialties"
                class="custom-placeholder"
              ></b-form-input>

              <b-input-group-append>
                <b-button variant="primary" block>Buscar</b-button>
              </b-input-group-append>
            </b-input-group>
          </b-col>
        </b-row>
      </section>

      <section class="mt-4 px-5">
        <b-row>
          <b-col
            v-for="(speciality, index) in specialties"
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
                      speciality.bannerImage
                        ? speciality.bannerImage
                        : '/src/assets/image/default-speciality.png'
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
                      speciality.name
                    }}</b-card-title>
                    <b-card-text>
                      <span class="area-indicator">
                        Área: {{ speciality.area.name }} / Especialidad</span
                      >
                      <div>
                        <span class="area-description-title">Costo:</span>
                        <span class="card-description"
                          >${{ speciality.cost }}</span
                        >
                      </div>
                      <div class="mt-3">
                        <span class="area-description-title">Descripción</span>
                      </div>
                      <div>
                        <span
                          v-if="
                            speciality.description &&
                            speciality.description.length > 50
                          "
                          class="card-description"
                        >
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
                        <span
                          v-else-if="speciality.description"
                          class="card-description"
                          >{{ speciality.description }}</span
                        >
                      </div>
                    </b-card-text>
                  </b-card-body>
                </b-col>
              </b-row>
            </b-card>
          </b-col>
        </b-row>
      </section>
      <section class="mt-4">
        <b-row
          class="bg-light m-0 py-3 py-sm-2 py-lg-1 d-flex justify-content-center"
        >
          <b-col
            cols="6"
            md="6"
            class="d-flex align-items-end align-items-md-center justify-content-center"
          >
            <b-pagination
              align="center"
              size="md"
              class="my-0 mb-2"
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
  </div>
</template>

<script>
import Vue from "vue";
import { EStatus } from "../../../../kernel/types";
import specialityController from "../services/controller/speciality.controller";

export default Vue.extend({
  name: "SpecialtiesView",
  components: {
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),
  },
  mounted() {
    this.getAllSpecialties();
  },
  data() {
    return {
      isLoading: false,
      showFullDescriptionIndex: -1,
      specialties: [],
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
    };
  },
  methods: {
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex =
        this.showFullDescriptionIndex === index ? -1 : index;
    },
    async getAllSpecialties() {
      try {
        this.isLoading = true;
        const response = await specialityController.getAllSpecialties({
          page: this.pagination.page - 1,
          size: this.pagination.size,
          sort: this.pagination.sort,
          direction: this.pagination.direction,
          data: this.pagination.data,
        });
        this.specialties = response.content;
        this.pagination.totalRows = response.totalElements;
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
  },
});
</script>
