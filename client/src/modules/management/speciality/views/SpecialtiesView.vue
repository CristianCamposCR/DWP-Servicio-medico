<template>
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
              <b-button variant="primary" block @click="getAllSpecialties"
                >Buscar</b-button
              >
            </b-input-group-append>
          </b-input-group>
        </b-col>
        <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
          <div class="d-flex align-items-center mt-2 mt-md-0">
            <span class="mr-1 area-indicator">Agregar nueva especialidad</span>
            &nbsp;
            <b-button variant="primary" v-b-modal.modal-save-speciality>
              <b-icon icon="plus" />
            </b-button>
          </div>
        </b-col>
      </b-row>
    </section>

    <save-speciality @reloadRegisters="getAllSpecialties" />

    <!-- listato -->
    <section class="mt-4 px-5" v-if="specialties.length > 0">
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
            <template #footer>
              <div>
                <b-button
                  v-if="speciality.status.name === EStatus.ACTIVE"
                  @click="changeStatus(speciality.id)"
                  v-b-tooltip.hover.v-info
                  title="Desactivar"
                  variant="outline-primary"
                >
                  <b-icon icon="toggle-on"></b-icon>
                </b-button>
                <b-button
                  v-else-if="speciality.status.name === EStatus.INACTIVE"
                  @click="changeStatus(speciality.id)"
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
                  @click="getOne(speciality.id)"
                  ><b-icon icon="pencil"></b-icon
                ></b-button>
              </div>
            </template>
          </b-card>
        </b-col>
      </b-row>
    </section>
    <section class="mt-4" v-if="specialties.length > 0">
      <b-row class="m-0 py-3 py-sm-2 py-lg-1 d-flex justify-content-center">
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

    <section class="mt-1" v-if="specialties.length === 0">
      <no-registers :message="'áreas'" />
    </section>

    <update-speciality
      :specialitySelected="specialitySelected"
      @reloadRegisters2="getAllSpecialties"
    />
  </div>
</template>

<script>
import Vue, { defineAsyncComponent } from "vue";
import { EStatus } from "../../../../kernel/types";
import specialityController from "../services/controller/speciality.controller";
import { encrypt } from "../../../../kernel/hashFunctions";
import SweetAlertCustom from "../../../../kernel/SweetAlertCustom";

export default Vue.extend({
  name: "SpecialtiesView",
  components: {
    SaveSpeciality: defineAsyncComponent(() =>
      import("./components/SaveSpeciality.vue")
    ),
    LoadingCustom: () =>
      import("../../../../views/components/LoadingCustom.vue"),
    UpdateSpeciality: defineAsyncComponent(() =>
      import("./components/UpdateSpeciality.vue")
    ),
    NoRegisters: defineAsyncComponent(() =>
      import("../../../../views/components/NoRegisters.vue")
    ),
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
      specialitySelected: {},
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

    async changeStatus(id) {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          const cipherId = await encrypt(id);
          const resp = await specialityController.changeStatus(cipherId);
          const { error } = resp;
          if (!error) {
            this.getAllSpecialties();
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
        const resp = await specialityController.getOne(cipherId);
        const { error } = resp;
        if (!error) {
          this.specialitySelected = resp;
          this.$bvModal.show("modal-update-speciality");
        }
      } catch (error) {
        console.log(error);
      }
    },
  },
});
</script>
