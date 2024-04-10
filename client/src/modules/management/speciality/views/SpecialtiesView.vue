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
              placeholder="Escribe el nombre de la especialidad"
              v-model="pagination.data.name"
              @keyup.enter="getAllSpecialties"
            ></b-form-input>

            <b-input-group-append>
              <b-button variant="primary" block>Buscar</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-col>
        <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
          <div class="d-flex align-items-center mt-2 mt-md-0">
            <span class="mr-1">Agregar nueva especialidad</span> &nbsp;
            <b-button variant="primary" v-b-modal.modal-save-speciality>
              <b-icon icon="plus" />
            </b-button>
          </div>
        </b-col>
      </b-row>
    </section>

    <save-speciality @reloadRegisters="getAllSpecialties" />

    <!-- listato -->
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
                  :src="
                    speciality.bannerImage
                      ? speciality.bannerImage
                      : '/src/assets/image/default-speciality.png'
                  "
                  alt="Image"
                  class="rounded-0"
                  height="160"
                ></b-card-img>
              </b-col>
              <b-col md="12">
                <b-card-body :title="speciality.name">
                  <b-card-text>
                    <div class="mb-3">
                      <span class="speciality-font"
                        >Especialidad / Área {{ speciality.area.name }}</span
                      >
                    </div>
                    <strong> Description: </strong>
                    <div>
                      <span
                        v-if="
                          speciality.description &&
                          speciality.description.length > 10
                        "
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
                      <span v-else-if="speciality.description">{{
                        speciality.description
                      }}</span>
                      <div class="mb-3">
                        <strong>Costo: </strong> {{ speciality.cost }}
                      </div>
                      <div class="mb-3">
                        <strong>Área: </strong>{{ speciality.area.name }}
                      </div>
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
                  variant="primary"
                >
                  Desactivar
                </b-button>
                <b-button
                  v-else-if="speciality.status.name === EStatus.INACTIVE"
                  @click="changeStatus(speciality.id)"
                  variant="danger"
                  >Activar</b-button
                >
                <b-button class="ml-1" variant="primary"
                  ><b-icon icon="eye" @click="getOne(speciality.id)"></b-icon
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
            @input="getAllSpecialties"
          >
          </b-pagination>
        </b-col>
      </b-row>
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
