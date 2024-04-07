<template>
  <div>
    <Navbar/>
    <div class="container-fluid mt-4">
      <loading-custom :isLoading="isLoading" />
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
                @keyup.enter="getAllSpecialities"
              ></b-form-input>
  
              <b-input-group-append>
                <b-button variant="primary" block>Buscar</b-button>
              </b-input-group-append>
            </b-input-group>
          </b-col>
          <b-col cols="12" sm="12" md="6" class="d-flex justify-content-end">
          </b-col>
        </b-row>
      </section>
  
      <section class="mt-5">
        <b-row>
          <b-col
            v-for="(speciality, index) in specialities"
            :key="index"
            cols="12"
            sm="6"
            md="4"
            lg="3"
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
                        : '/src/assets/image/default-area.avif'
                    "
                    alt="Image"
                    class="rounded-0"
                    height="160"
                  ></b-card-img>
                </b-col>
                <b-col md="12">
                  <b-card-body>
                    <b-card-title class="card-title">{{
                      speciality.name
                    }}</b-card-title>
                    <b-card-text>
                      <div>
                        <span
                          v-if="speciality.description && speciality.description.length > 50"
                          class="card-description"
                        >
                          {{
                            showFullDescriptionIndex === index
                              ? speciality.description
                              : speciality.description.substring(0, 50) + "..."
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
              @input="getAllSpecialities"
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
  import { EStatus } from "../../../kernel/types";
  export default Vue.extend({
    name: "PublicSpecialitiesView",
    components: {
      LoadingCustom: () =>
        import("../../../views/components/LoadingCustom.vue"),
        Navbar: () => import('@/modules/public/components/Navbar.vue'),

    },
    mounted() {
      this.getAllSpecialities();
    },
    data() {
      return {
        isLoading: false,
        showFullDescriptionIndex: -1,
        specialities: [
        {
            id:1,
            name:"Hodontologia",
            area:1,
            description: "Una comunicación abierta y transparente puede mejorar la cohesión y la coordinación dentro de la empresa. La comunicación vertical y horizontal puede facilitar el intercambio de información y la toma de decisiones ágil. Las barreras en la comunicación pueden obstaculizar la efectividad organizativa y generar malentendidos.",
            cost:0,
            createAt: null,
            bannerImage:'',
            status:1
        },
        {
            id:2,
            name:"Hodontologia",
            area:1,
            description: "Una comunicación abierta y transparente puede mejorar la cohesión y la coordinación dentro de la empresa. La comunicación vertical y horizontal puede facilitar el intercambio de información y la toma de decisiones ágil. Las barreras en la comunicación pueden obstaculizar la efectividad organizativa y generar malentendidos.",
            cost:0,
            createAt: null,
            bannerImage:'',
            status:1
        },
        {
            id:3,
            name:"Hodontologia",
            area:1,
            description: "Una comunicación abierta y transparente puede mejorar la cohesión y la coordinación dentro de la empresa. La comunicación vertical y horizontal puede facilitar el intercambio de información y la toma de decisiones ágil. Las barreras en la comunicación pueden obstaculizar la efectividad organizativa y generar malentendidos.",
            cost:0,
            createAt: null,
            bannerImage:'',
            status:1
        },
        {
            id:4,
            name:"Hodontologia",
            area:1,
            description: "Una comunicación abierta y transparente puede mejorar la cohesión y la coordinación dentro de la empresa. La comunicación vertical y horizontal puede facilitar el intercambio de información y la toma de decisiones ágil. Las barreras en la comunicación pueden obstaculizar la efectividad organizativa y generar malentendidos.",
            cost:0,
            createAt: null,
            bannerImage:'',
            status:1
        },
        {
            id:5,
            name:"Hodontologia",
            area:1,
            description: "Una comunicación abierta y transparente puede mejorar la cohesión y la coordinación dentro de la empresa. La comunicación vertical y horizontal puede facilitar el intercambio de información y la toma de decisiones ágil. Las barreras en la comunicación pueden obstaculizar la efectividad organizativa y generar malentendidos.",
            cost:0,
            createAt: null,
            bannerImage:'',
            status:1
        },
        {
            id:6,
            name:"Hodontologia",
            area:1,
            description: "Una comunicación abierta y transparente puede mejorar la cohesión y la coordinación dentro de la empresa. La comunicación vertical y horizontal puede facilitar el intercambio de información y la toma de decisiones ágil. Las barreras en la comunicación pueden obstaculizar la efectividad organizativa y generar malentendidos.",
            cost:0,
            createAt: null,
            bannerImage:'',
            status:1
        },
        {
            id:7,
            name:"Hodontologia",
            area:1,
            description: "Una comunicación abierta y transparente puede mejorar la cohesión y la coordinación dentro de la empresa. La comunicación vertical y horizontal puede facilitar el intercambio de información y la toma de decisiones ágil. Las barreras en la comunicación pueden obstaculizar la efectividad organizativa y generar malentendidos.",
            cost:0,
            createAt: null,
            bannerImage:'',
            status:1
        },
        ],
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
        specialitiesSelected: {},
      };
    },
    methods: {
      toggleDescription(index, event) {
        event.preventDefault();
        this.showFullDescriptionIndex =
          this.showFullDescriptionIndex === index ? -1 : index;
      },
      async getAllSpecialities() {
        console.log("wachando specialidades");
        // try {
        //   this.isLoading = true;
        //   const response = await specialitiesController.getAllSpecialities({
        //     page: this.pagination.page - 1,
        //     size: this.pagination.size,
        //     sort: this.pagination.sort,
        //     direction: this.pagination.direction,
        //     data: this.pagination.data,
        //   });
        //   this.specialities = response.content;
        //   this.pagination.totalRows = response.totalElements;
        // } catch (error) {
        //   console.log(error);
        // } finally {
        //   this.isLoading = false;
        // }
      },
    },
  });
  </script>
  