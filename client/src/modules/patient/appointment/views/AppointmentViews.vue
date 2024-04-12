<template>
  <div class="container-fluid  px-5 pt-3" style="height: calc(100vh - 72px)">
    <b-row>
      <b-col cols="12" md="6">
        <custom-bread-crumb :links="sections" :active="sectionActive"></custom-bread-crumb>
        <span v-if="sectionActive === 1 || sectionActive === 2" class="font-weight-bold text-black-50 ml-2">
          Selecciona o arrastra {{ this.sectionActive === 1 ? 'el área de interés' : 'la especialidad' }}
        </span>
      </b-col>

      <b-col cols="12" sm="12" md="6" class="mt-3 mt-md-0 p-0 m-0">
        <b-form @submit.prevent="getAllAreas">
          <b-input-group>
            <b-form-input :placeholder="'Buscar'+ (sectionActive === 1 ? ' área' : ' especialidad') + '...'"
                          v-model="pagination.data.name"
                          @submit
                          @keyup="() => {if(pagination.data.name.length === 0) {
                            if(sectionActive === 1) {
                              this.getAllAreas();
                            } else {
                              // this.getAllSpecialties();
                            }
                          }}"
            ></b-form-input>
            <b-input-group-append>
              <b-button variant="primary" type="submit">Buscar</b-button>
            </b-input-group-append>
          </b-input-group>
        </b-form>
      </b-col>
    </b-row>

    <b-row class="h-75 flex-md-row-reverse">
      <b-col cols="12" lg="3" class="mt-3 ">
        <div class="card p-3">
          <p class="p-0 m-0 mb-2 text-primary">Que tal <b>{{ this.user }}!!</b>, estas apunto de agendar una cita</p>

          <div class="h-100 w-100">
            <div class="h-100 w-100 my-lg-2"
                 @drop="onDrop($event)"
                 @dragenter.prevent
                 @dragover.prevent
            >
              <span class="text-black-50 text-center  ">

                <div v-if="appointment.area" class="text-left">
                  <sub class="text-black-50">Área seleccionada</sub>: <br>
                    <span v-if="appointment.area" class="h4 text-primary">
                    {{ appointment.area.name }}
                    </span>
               </div>

                <div v-if="appointment.specialty" class="text-left mt-3">
                  <sub class="text-black-50">Especialidad seleccionada</sub>: <br>
                   <span v-if="appointment.specialty" class="h4 text-primary">
                   {{ appointment.specialty.name }}
                   </span>
               </div>

                <div v-if="appointment.shift" class="text-left mt-3">
                  <sub class="text-black-50">Turno seleccionado</sub>: <br>
                   <span v-if="appointment.specialty" class="h4 text-primary">
                   {{ appointment.shift.name.charAt(0).toUpperCase() + appointment.shift.name.slice(1).toLowerCase()}}
                   </span>
               </div>

                <div v-if="appointment.appointmentType" class="text-left mt-3">
                  <sub class="text-black-50">Tipo de cita seleccionada</sub>: <br>
                   <span v-if="appointment.specialty" class="h4 text-primary">
                   {{ appointment.appointmentType }}
                   </span>
               </div>


                <div v-if="sectionActive === 1 && !appointment.area"
                     :class="{'squareSelected':!appointment.area, 'h-100 w-100 my-2':true}"
                >Arrastra aquí el área de interés o selecciona una de la lista
                </div>

                <div v-else-if="sectionActive === 2 && !appointment.specialty"
                     :class="{'squareSelected':!appointment.specialty, 'h-75 w-100 py-2 mt-2 mb-2':true}"
                >Arrastra aquí la especialidad o selecciona una de la lista
                </div>


                <div v-else-if="sectionActive === 3 && !appointment.shift"
                     :class="{'squareSelected':!appointment.shift, 'h-75 w-100 py-2 mt-2 mb-2':true}"
                >Arrastra aquí el turno o selecciona uno de la lista
                </div>

                <div v-else-if="sectionActive === 4 && !appointment.appointmentType"
                     :class="{'squareSelected':!appointment.appointmentType, 'h-75 w-100 py-2 mt-2 mb-2':true}"
                >Arrastra aquí el tipo de cita o selecciona uno de la lista
                </div>

              </span>
            </div>
          </div>

          <button class="btn btn-primary mt-3"
                  :disabled="this.sectionActive === 1 && !appointment.area || this.sectionActive === 2 && (!appointment.specialty || !appointment.area)"
                  @click="handleNextStep"
          >
            Continuar
          </button>
          <button class="btn btn-secondary mt-1" @click="toBack">Regresar</button>
        </div>
      </b-col>


      <b-col class="">

        <custom-not-found-registers :show="!isLoading && pagination.totalRows === 0"></custom-not-found-registers>

        <section class="mt-5 mt-md-0 position-relative h-100 pb-5" v-if="sectionActive === 1">
          <b-row>
            <b-col
                v-for="(area, index) in listItems"
                :key="index"
                cols="12" sm="6" md="4" xl="3"
                class="d-flex justify-content-center"
            >
              <b-card
                  @dragover.prevent
                  @dragenter.prevent
                  @dragstart="startDrag($event, area)"
                  draggable="true"

                  no-body
                  class="overflow-hidden mt-3 mx-2 shadow card-animation"
                  style="max-width: 270px; max-height: 800px; min-width: 100%"
                  footer-bg-variant="transparent"
                  footer-border-variant="white"
              >
                <b-row no-gutters>
                  <b-col md="12">
                    <b-card-img
                        :src=" area.bannerImage ? area.bannerImage : '/src/assets/image/default-area.avif'"
                        alt="Image"
                        class="rounded-0"
                        height="100"
                        style="object-fit: cover"
                    ></b-card-img>
                  </b-col>
                  <b-col md="12">
                    <b-card-body class="pb-0 mb-0">
                      <b-card-title class="card-title">{{
                          area.name
                        }}
                      </b-card-title>
                      <b-card-text>
                        <div>
                          <span
                              v-if="area.description && area.description.length > 50"
                              class="card-description"
                          >
                            {{
                              showFullDescriptionIndex === index ? area.description : area.description.substring(0, 50) + "..."
                            }}
                            <a href="#" @click="toggleDescription(index, $event)">
                              {{
                                showFullDescriptionIndex === index ? "Ver menos" : "Ver más"
                              }}
                            </a>
                          </span>
                          <span
                              v-else-if="area.description"
                              class="card-description"
                          >
                            {{ area.description }}
                          </span>
                        </div>
                      </b-card-text>
                    </b-card-body>
                  </b-col>
                </b-row>
                <template #footer>
                  <div>
                    <b-button variant="primary" block @click="handleAreaSelected(area)">
                      Seleccionar
                    </b-button>
                  </div>
                </template>
              </b-card>
            </b-col>
          </b-row>
          <section class="position-absolute" v-if="pagination.totalRows !== 0 " style="bottom: 0; width: 100%">
            <b-row class=" m-0 py-3 py-sm-2 py-lg-1">
              <b-col cols="6" class="d-flex justify-content-center justify-content-md-start">
                <b class="font-weight-light text-black-50">
                  Áreas disponibles {{ pagination.totalRows }}
                </b>
              </b-col>

              <b-col cols="6" class="d-flex align-items-end align-items-md-center justify-content-center">
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
        </section>
        <section class="mt-5 mt-md-0 position-relative h-100 pb-5" v-if="sectionActive === 2">
          <b-row>
            <b-col
                v-for="(speciality, index) in listItems"
                :key="index"
                cols="12" sm="6" md="4" xl="3"
                class="d-flex justify-content-center"
            >
              <b-card
                  @dragover.prevent
                  @dragenter.prevent
                  @dragstart="startDrag($event, speciality)"
                  draggable="true"

                  no-body
                  class="overflow-hidden mt-3 mx-2 shadow card-animation"
                  style="max-width: 270px; max-height: 800px; min-width: 100%"
                  footer-bg-variant="transparent"
                  footer-border-variant="white"
              >
                <b-row no-gutters>
                  <b-col md="12">
                    <b-card-img
                        :src=" speciality.bannerImage ? speciality.bannerImage : '/src/assets/image/default-speciality.png'"
                        alt="Image"
                        class="rounded-0"
                        height="100"
                        style="object-fit: cover"
                    ></b-card-img>
                  </b-col>
                  <b-col md="12">
                    <b-card-body class="pb-0 mb-0">
                      <b-card-title class="card-title">{{
                          speciality.name
                        }}
                      </b-card-title>
                      <b-card-text>
                        <div>
                          <span v-if="speciality.description && speciality.description.length > 50"
                                class="card-description">
                            {{
                              showFullDescriptionIndex === index ? speciality.description : speciality.description.substring(0, 50) + "..."
                            }}
                            <a href="#" @click="toggleDescription(index, $event)">
                              {{
                                showFullDescriptionIndex === index ? "Ver menos" : "Ver más"
                              }}
                            </a>
                          </span>
                          <span
                              v-else-if="speciality.description"
                              class="card-description"
                          >
                            {{ speciality.description }}
                          </span>
                        </div>
                        <p class="pb-0 mb-0">
                          <b>Costo: </b>
                          ${{ speciality.cost }}
                        </p>
                      </b-card-text>
                    </b-card-body>
                  </b-col>
                </b-row>
                <template #footer>
                  <div>
                    <b-button variant="primary" block @click="handleSpecialitySelected(speciality)">
                      Seleccionar
                    </b-button>
                  </div>
                </template>
              </b-card>
            </b-col>
          </b-row>
          <section class="position-absolute" v-if="pagination.totalRows !== 0 " style="bottom: 0; width: 100%">
            <b-row class=" m-0 py-3 py-sm-2 py-lg-1">
              <b-col cols="6" class="d-flex justify-content-center justify-content-md-start">
                <b class="font-weight-light text-black-50">
                  Especialidades disponibles {{ pagination.totalRows }}
                </b>
              </b-col>

              <b-col cols="6" class="d-flex align-items-end align-items-md-center justify-content-center">
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
        </section>
        <section class="mt-5 mt-md-0 position-relative h-100 pb-5" v-if="sectionActive === 3">
<!--                    @dragover.prevent-->
<!--                    @dragenter.prevent-->
<!--                    @dragstart="startDrag($event, speciality)"-->
<!--                    draggable="false"-->
          <span class="font-weight-bold text-black-50 ml-2">
            Selecciona o arrastra el turno
          </span>

          <b-row>
            <b-col  v-for="(shift, index) in shiftList" :key="index">
              <b-card
                      class="shadow img-custom-card card-animation"
                      style="width: 100%;"
                      :img-src="shift.image"
                      img-alt="Image"
                      img-height="60"
                      no-body

                      @dragover.prevent
                      @dragenter.prevent
                      @dragstart="startDrag($event, shift)"
                      draggable="true"
              >
                <b-card-text class="text-center">
                  <b-icon :icon="shift.icon" size="2rem"></b-icon>
                  <p class="m-0 font-weight-bold">{{shift.name}}</p>
                  <p class="p-0 m-0">{{shift.entryHour}} - {{shift.departureHour}}</p>
                </b-card-text>

                <div class="px-3 pb-3">
                  <b-button variant="primary" block @click="appointment.shift = shift.name">
                    Seleccionar
                  </b-button>
                </div>
              </b-card>



            </b-col>
          </b-row>
          <hr>
          <span class="font-weight-bold text-black-50 ml-2">
            Selecciona o arrastra el tipo de cita
          </span>
          <b-row>
            <b-col  v-for="(shift, index) in listItems" :key="index">
              <b-card
                  class="shadow img-custom-card card-animation"
                  style="width: 100%;"
                  :img-src="shift.image"
                  img-alt="Image"
                  img-height="60"
                  no-body

                  @dragover.prevent
                  @dragenter.prevent
                  @dragstart="startDrag($event, shift)"
                  draggable="true"
              >
                <b-card-text class="text-center">
                  <b-icon :icon="shift.icon" size="2rem"></b-icon>
                  <p class="m-0 font-weight-bold">{{shift.name}}</p>
                </b-card-text>
                <div class="px-3 pb-3">
                  <b-button variant="primary" block @click="appointment.shift = shift.name">
                    Seleccionar
                  </b-button>
                </div>
              </b-card>



            </b-col>
          </b-row>
        </section>

      </b-col>
    </b-row>

    <loading-custom :isLoading="isLoading"/>

  </div>
</template>


<script>
import Vue from "vue";
import areaController from "@/modules/patient/areas/services/controller/area.controller";
import specialtyController from "@/modules/patient/speciality/services/controller/speciality.controller";
import {jwtDecode} from "jwt-decode";
import shiftController from "@/modules/patient/shifts/services/controller/shift.controller";
import moment from "moment/moment";
import appointmentTypeController  from "@/modules/patient/appointment-type/services/controller/appointment-type.controller";


export default Vue.extend({
  name: "AppointmentView",

  beforeRouteLeave(to, from, next) {
    if (this.handleBeforeRouteLeave()) {
      next(false)
    } else next()
  },
  components: {
    LoadingCustom: () => import("@/views/components/LoadingCustom.vue"),
    CustomBreadCrumb: () => import("@/modules/patient/appointment/views/components/CustomBreadCrumb.vue"),
    CustomNotFoundRegisters: () => import("@/modules/patient/appointment/views/components/CustomNotFoundRegisters.vue"),
  },
  mounted() {
    window.addEventListener('beforeunload', this.handleBeforeRouteLeave);
    this.getAllAreas();
    this.user = jwtDecode(localStorage.token).sub;
    this.user = this.user.charAt(0).toUpperCase() + this.user.slice(1);
  },
  beforeDestroy() {
    window.removeEventListener('beforeunload', this.handleBeforeRouteLeave);
  },
  data() {
    return {
      sectionActive: 1,
      sections: [
        {step: 0, text: 'Inicio', to: {name: 'landing'}, icon: 'house-fill', active: false},
        {step: 1, text: 'Áreas', to: '#areas', icon: 'geo-alt-fill', active: false},
        {step: 2, text: 'Especialidades', to: '#specialty', icon: 'calendar', active: false},
        {step: 3, text: 'Datos de la cita', to: '#appointment-data', icon: 'calendar', active: false},
        {step: 4, text: 'Confirmación', to: '#appointment-confirmation', icon: 'calendar', active: false},
      ],

      isLoading: false,
      showFullDescriptionIndex: -1,
      shiftList: [],
      listItems: [],

      appointment: {
        area: null,
        specialty: null,
        shift: null,
        appointmentType: null,
      },

      user: null,

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
    async handleBeforeRouteLeave(e) {
      const answer = window.confirm('¿Estás seguro de que quieres salir?');
      if (!answer) {
        e.preventDefault();
        e.returnValue = '';
      }
    },
    handleAreaSelected(area) {
      this.appointment.area = area;
    },
    handleSpecialitySelected(speciality) {
      this.appointment.specialty = speciality;
    },
    handleShiftSelected(shift) {
      this.appointment.shift = shift;
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
        this.listItems = response.content;
        this.pagination.totalRows = response.totalElements ? response.totalElements : 0;
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    startDrag(evt, item) {
      evt.dataTransfer.dropEffect = "move";
      evt.dataTransfer.effectAllowed = "move";
      evt.dataTransfer.setData("item", JSON.stringify(item));
    },
    onDrop(evt) {
      evt.preventDefault();

      const item = evt.dataTransfer.getData("item");
      const parsedItem = JSON.parse(item);

      if (this.sectionActive === 1) {
        this.handleAreaSelected(parsedItem);
      } else if (this.sectionActive === 2){
        this.handleSpecialitySelected(parsedItem);
      }else if (this.sectionActive === 3){
        this.handleShiftSelected(parsedItem);
      }
    },
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex = this.showFullDescriptionIndex === index ? -1 : index;
    },
    handleNextStep() {
      if (this.sectionActive === 1 && !this.appointment.area) {
        console.log('Selecciona un área')
        return;
      } else if (!this.appointment.specialty) {
        this.sectionActive = 2;
        this.getAllSpecialtiesByArea();
        console.log('Selecciona una especialidad')
        return;
      }else if (!this.appointment.shift) {
        this.sectionActive = 3;
        this.getAllShifts();
        this.getAllAppointmentTypes()
        console.log('Selecciona un turno')
        return;
      }
    },
    async getAllSpecialtiesByArea() {
      try {
        this.isLoading = true;
        const response = await specialtyController.getAllSpecialtiesByArea({
          page: this.pagination.page - 1,
          size: this.pagination.size,
          sort: this.pagination.sort,
          direction: this.pagination.direction,
          data: {
            name: this.pagination.data.name,
            area: {
              id: this.appointment.area.id,
            },
          }
        });
        this.listItems = response.content;
        this.pagination.totalRows = response.totalElements ? response.totalElements : 0;
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    async getAllShifts() {
      try {
        this.isLoading = true;
        const response = await shiftController.getAllShifts();
        const dataExtraShift = [
          {
            image: 'https://img.freepik.com/free-photo/wind-spring-white-stratosphere-pure_1127-2384.jpg',
            icon: 'sunrise',
          },
          {
            image: 'https://img.freepik.com/free-photo/cloudy-sky-landscape-background_23-2149134096.jpg',
            icon: 'sun',
          }
        ]
        response.map((shift, index) => {
          shift.image = dataExtraShift[index].image;
          shift.icon = dataExtraShift[index].icon;
          shift.departureHour = moment().set({hour: shift.departureHour, minute:0}).format('hh:mm a');
          shift.entryHour = moment().set({hour: shift.entryHour, minute:0}).format('hh:mm a');
        })
        this.shiftList = response;
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    async getAllAppointmentTypes() {
      try {
        this.isLoading = true;
        const response = await appointmentTypeController.getAllAppointment();
        this.listItems =  response.data
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    toBack() {
      if (this.sectionActive === 1) {
        this.$router.back();
      } else {
        this.sectionActive--;
        if (this.sectionActive === 1) {
          this.getAllAreas();
          this.appointment.specialty = null;
        } else if (this.sectionActive === 2) {
          this.getAllSpecialtiesByArea();
        }
      }
    }
  },
});
</script>


<style scoped>
.squareSelected {
  border: 2px dashed #ddd;
  border-radius: 5px;
  align-items: center;
  display: flex;
}

.img-custom-card > .card-img {
  object-fit: cover;
  object-position: center;
}

</style>