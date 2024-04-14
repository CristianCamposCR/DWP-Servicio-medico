<template>
  <b-overlay :show="isLoading && sectionActive === 4 "  style="height: 100vh">
   <template #overlay>
     <div>
       <div class="d-flex justify-content-center">
         <b-img
             src="https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExaGJ3Y3RwZnAwNHNqeXN0c2d5amRkbGtrbGVud2dxcnRzdzhudjdiYiZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/QKW9AtBQifEfWKV8iV/giphy.gif"
             style="object-fit: cover; object-position: center; margin-bottom: 0"
             width="350px"
             height="300px"
         >
         </b-img>
       </div>
       <div class="d-flex justify-content-center">
         <div class="text-center not-registers-font mt-0">
           Estamos agendando tu cita, por favor espera un momento...
         </div>
       </div>
     </div>

    </template>

    <div class="container-fluid  px-5 pt-3" style="height: calc(100vh - 72px)">

      <b-row v-if="promotion.isActive">
        <b-col>
          <b-alert show variant="info" class="text-center text-dark">
            Hoy es lunes, por lo que tienes un 20% de descuento en todas las citas
          </b-alert>
        </b-col>
      </b-row>
      <b-row>
        <b-col cols="12" md="6">
          <custom-bread-crumb :links="sections" :active="sectionActive"></custom-bread-crumb>
          <span v-if="sectionActive === 1 || sectionActive === 2" class="font-weight-bold text-black-50 ml-2">
          Selecciona o arrastra {{ this.sectionActive === 1 ? 'el área de interés' : 'la especialidad' }}
        </span>
        </b-col>

        <b-col v-if="this.sectionActive < 3" cols="12" sm="12" md="6" class="mt-3 mt-md-0 p-0 m-0">
          <b-form @submit.prevent="handleSearch">
            <b-input-group>
              <b-form-input :placeholder="'Buscar'+ (sectionActive === 1 ? ' área' : ' especialidad') + '...'"
                            v-model="pagination.data.name"
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
            <p class="p-0 m-0 mb-1 text-primary">Que tal <b>{{ this.user }}!!</b>, estas apunto de agendar una cita</p>

            <div class="h-100 w-100">
              <div class="h-100 w-100 "
                   @drop="onDrop($event)"
                   @dragenter.prevent
                   @dragover.prevent
              >
              <span class="text-center">
                <div v-if="appointment.area" class="text-left">
                  <sub class="text-black-50">Área seleccionada</sub>: <br>
                    <span v-if="appointment.area" class="h4 text-primary ">
                    {{ appointment.area.name }}

                      <b-icon
                          v-if="sectionActive === 1"
                          :icon="icons.area.delete"
                          scale="0.75" aria-hidden="true" variant="danger" class="x-close"
                          @mouseover="icons.area.delete='x-circle'"
                          @mouseleave="icons.area.delete='x-circle-fill'"
                          @click="() => {
                                icons.area.delete = 'x-circle-fill';
                                appointment.area = null;
                              }"
                      />

                    </span>
               </div>

                <div v-if="appointment.specialty" class="text-left mt-3">
                  <sub class="text-black-50">Especialidad seleccionada</sub>: <br>
                   <span v-if="appointment.specialty" class="h4 text-primary">
                   {{ appointment.specialty.name }}
                         <b-icon
                             v-if="sectionActive === 2"
                             :icon="icons.specialty.delete"
                             scale="0.75" aria-hidden="true" variant="danger" class="x-close"
                             @mouseover="icons.specialty.delete='x-circle'"
                             @mouseleave="icons.specialty.delete='x-circle-fill'"
                             @click="() => {
                                icons.specialty.delete = 'x-circle-fill';
                                appointment.specialty = null;
                              }"
                         />
                   </span>
               </div>

                <div v-if="appointment.date" class="text-left mt-3">
                  <sub class="text-black-50">Fecha seleccionada</sub>: <br>
                   <span v-if="appointment.date" class="h5 text-primary">
                   {{ formatDate(appointment.date) }}

                         <b-icon
                             v-if="sectionActive === 3"
                             :icon="icons.date.delete"
                             scale="0.75" aria-hidden="true" variant="danger" class="x-close"
                             @mouseover="icons.date.delete='x-circle'"
                             @mouseleave="icons.date.delete='x-circle-fill'"
                             @click="() => {
                                icons.date.delete = 'x-circle-fill';
                                appointment.date = null;
                              }"
                         />
                   </span>
               </div>

                <div v-if="appointment.shift" class="text-left mt-3">
                  <sub class="text-black-50">Turno seleccionado</sub>: <br>
                   <span v-if="appointment.shift" class="h4 text-primary">
                   {{ appointment.shift.name.charAt(0).toUpperCase() + appointment.shift.name.slice(1).toLowerCase() }}

                         <b-icon
                             v-if="sectionActive === 3"
                             :icon="icons.shift.delete"
                             scale="0.75" aria-hidden="true" variant="danger" class="x-close"
                             @mouseover="icons.shift.delete='x-circle'"
                             @mouseleave="icons.shift.delete='x-circle-fill'"
                             @click="() => {
                                icons.shift.delete = 'x-circle-fill';
                                appointment.shift = null;
                              }"
                         />
                   </span>
               </div>

                <div v-if="appointment.appointmentType" class="text-left mt-3">
                  <sub class="text-black-50">Tipo de cita seleccionada</sub>: <br>
                   <span v-if="appointment.specialty" class="h4 text-primary">
                   {{
                       appointment.appointmentType.name.charAt(0).toUpperCase() + appointment.appointmentType.name.slice(1).toLowerCase()
                     }}

                         <b-icon
                             v-if="sectionActive === 3"
                             :icon="icons.appointmentType.delete"
                             scale="0.75" aria-hidden="true" variant="danger"
                             @mouseover="icons.appointmentType.delete='x-circle'"
                             @mouseleave="icons.appointmentType.delete='x-circle-fill'"
                             @click="() => {
                                icons.appointmentType.delete = 'x-circle-fill';
                                appointment.appointmentType = null;
                              }"
                         />
                   </span>
               </div>

                <div v-if="sectionActive === 1 && !appointment.area"
                     :class="{'squareSelected':!appointment.area, 'h-100 w-100 my-2 text-black-50':true}"
                >Arrastra aquí el área de interés o selecciona una de la lista
                </div>

                <div v-else-if="sectionActive === 2 && !appointment.specialty"
                     :class="{'squareSelected':!appointment.specialty, 'h-75 w-100 py-2 mt-2 mb-2 text-black-50':true}"
                >Arrastra aquí la especialidad o selecciona una de la lista
                </div>

                <div v-else-if="sectionActive === 3 && !appointment.date"
                     :class="{'squareSelected':!appointment.date, 'h-75 w-100 py-2 mt-2 mb-2 text-black-50':true}"
                >Selecciona una fecha
                </div>

                <div v-else-if="sectionActive === 3 && !appointment.shift"
                     :class="{'squareSelected':!appointment.shift, 'h-75 w-100 py-2 mt-2 mb-2 text-black-50':true}"
                >Arrastra aquí el turno o selecciona uno de la lista
                </div>

                <div v-else-if="sectionActive === 3 && !appointment.appointmentType"
                     :class="{'squareSelected':!appointment.appointmentType, 'h-75 w-100 py-2 mt-2 mb-2 text-black-50':true}"
                >Arrastra aquí el tipo de cita o selecciona uno de la lista
                </div>


                <div v-if="appointment.specialty" class="text-left">
                <hr>
                  <span class="text-black">Total a pagar</span>:&nbsp;
                   <span v-if="appointment.specialty && !promotion.isActive" class="h4 text-dark border-bottom ">
                   ${{ appointment.specialty.cost }}
                   </span>

                    <span v-else-if="appointment.specialty && promotion.isActive" class="h4 text-dark border-bottom ">
                   ${{ appointment.specialty.cost - (appointment.specialty.cost * promotion.discount.quantity / 100) }}
                    <sub><del class="text-black-50 mr-1">${{ appointment.specialty.cost }}</del></sub>
                   </span>
               </div>


              </span>
              </div>
            </div>


            <div v-if="!appointment.isAcceptedTerms" class="text-center">
              <button class="btn-block btn btn-primary mt-1" @click="handleShowModal('termAndCondition')">Términos y
                condiciones
              </button>
              <sub class="text-danger " v-if="!modalsShow.termAndCondition">Es necesario aceptar los términos y
                condiciones para
                continuar</sub>
            </div>


            <button class="btn btn-primary mt-3"
                    v-if="sectionActive < 4"
                    :disabled="
                  !appointment.isAcceptedTerms ||
                  sectionActive === 1 && !appointment.area ||
                  sectionActive === 2 && (!appointment.specialty || !appointment.area) ||
                  sectionActive === 3 && (!appointment.shift || !appointment.appointmentType || !appointment.date)"
                    @click="handleNextStep"
            >
              {{
                this.sectionActive < 3 ? 'Siguiente' : 'Validar disponibilidad'
              }}
            </button>


            <button class="btn btn-primary mt-3"
                    v-if="sectionActive === 4"
                    :disabled="!appointment.isAcceptedTerms   ||
                  !creditCard.cvv ||
                  !creditCard.expirationDate.month ||
                   !creditCard.expirationDate.year ||
                   !creditCard.number ||
                   !creditCard.owner || isLoading"
                    @click="saveAppointment"
            >
              Pagar
            </button>

            <!--   TODO: Change values for disables to $v.model.invalid -->
            <button class="btn btn-secondary mt-1" @click="toBack">Regresar</button>
          </div>
        </b-col>


        <b-col class="">

          <custom-not-found-registers :show="!isLoading && pagination.totalRows === 0"></custom-not-found-registers>

          <section class="mt-5 mt-md-0 position-relative h-100 pb-5" v-if="sectionActive === 1">
            <custom-loading-section :busy="isLoading  "/>
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
                    @dragstart="startDrag($event, area, 'area')"
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
                      <b-button variant="primary" block @click="handleItemSelected(area, 'area')"
                                :disabled="appointment.area?.id === area?.id">
                        {{
                          appointment.area?.id === area?.id ? 'Seleccionado' : 'Seleccionar'
                        }}
                        <b-icon v-if="appointment.area? appointment.area === area : false " icon="check"
                                aria-hidden="true" variant="white"/>
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
            <custom-loading-section :busy="isLoading  "/>
            <b-row>
              <b-col
                  v-for="(speciality, index) in specialtyList"
                  :key="index"
                  cols="12" sm="6" md="4" xl="3"
                  class="d-flex justify-content-center"
              >
                <b-card
                    @dragover.prevent
                    @dragenter.prevent
                    @dragstart="startDrag($event, speciality, 'specialty')"
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
                          <p class="pb-0 mb-0" v-if="!promotion.isActive">
                            <b>Costo: </b>
                            ${{ speciality.cost }}
                          </p>
                          <p class="pb-0 mb-0 position-relative" v-else>
                            <b>Costo: </b>
                            ${{ speciality.cost - (speciality.cost * promotion.discount.quantity / 100) }}
                            <sub>
                              <del class="text-black-50 mr-1">${{ speciality.cost }}</del>
                            </sub>
                            <b-icon icon="tag-fill" variant="danger" aria-hidden="true" class="position-absolute"
                                    style="top:11px;"/>

                          </p>
                        </b-card-text>
                      </b-card-body>
                    </b-col>
                  </b-row>
                  <template #footer>
                    <div>
                      <b-button variant="primary" block @click="handleItemSelected(speciality, 'specialty')"
                                :disabled="appointment.specialty?.id === speciality?.id"
                      >
                        {{
                          appointment.specialty?.id === speciality?.id ? 'Seleccionado' : 'Seleccionar'
                        }}

                        <b-icon v-if="appointment.specialty? appointment.specialty === speciality : false " icon="check"
                                aria-hidden="true" variant="white"/>
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
            <custom-loading-section :busy="isLoading" :opacity="0.6"/>
            <b-row>
              <b-col cols="12" md="6">
                <span class="font-weight-bold text-black-50 ml-2">Selecciona una fecha</span>
                <b-card style="height: 93%" class="d-flex align-items-center p-3" no-body>
                  <b-calendar
                      block
                      v-model="appointment.date"
                      label-no-date-selected="Selecciona una fecha"
                      label-selected="Fecha seleccionada"
                      selected-variant="primary"
                      :initial-date="new Date(dateTomorrow())"
                      :min="new Date(dateTomorrow())"
                      :max="new Date(dateMoreYear(2))"
                      today-variant="info"
                      nav-button-variant="primary"
                      label-help="Usa las flechas simples para navegar entre los meses y las dobles para cambiar de año"
                  ></b-calendar>
                </b-card>
              </b-col>
              <b-col cols="12" md="6">
                <span class="font-weight-bold text-black-50 ml-2">Selecciona o arrastra el turno</span>
                <b-row>
                  <b-col v-for="(shift, index) in shiftList"
                         :key="index" cols="12" lg="12"
                         class="my-4 my-md-0 d-flex justify-content-center px-lg-5"
                  >
                    <b-card
                        class="shadow img-custom-card card-animation my-2"
                        style="width: 100%;"
                        :img-src="shift.image"
                        img-alt="Image"
                        img-height="60"
                        no-body
                        @dragover.prevent
                        @dragenter.prevent
                        @dragstart="startDrag($event, shift, 'shift')"
                        draggable="true"
                        footer-bg-variant="transparent"
                        footer-border-variant="white"
                    >
                      <b-card-text class="text-center mb-0">
                        <b-icon :icon="shift.icon" size="2rem"></b-icon>
                        <p class="m-0 font-weight-bold">{{ shift.name }}</p>
                        <p class="p-0 m-0">{{ shift.entryHour }} - {{ shift.departureHour }}</p>
                      </b-card-text>

                      <template #footer>
                        <div>
                          <b-button variant="primary" block @click="handleItemSelected(shift, 'shift')"
                                    :disabled="appointment.shift?.id === shift.id"
                          >
                            {{
                              appointment.shift?.id === shift.id ? 'Seleccionado' : 'Seleccionar'
                            }}

                            <b-icon v-if="appointment.shift? appointment.shift === shift : false " icon="check"
                                    aria-hidden="true" variant="white"/>
                          </b-button>
                        </div>
                      </template>
                    </b-card>
                  </b-col>
                </b-row>

              </b-col>
            </b-row>


            <hr>
            <span class="font-weight-bold text-black-50 ml-2">
            Selecciona o arrastra el tipo de cita
          </span>
            <b-row>
              <b-col v-for="(appointmentType, index) in appointmentTypeList" :key="index"
                     class="my-4 my-md-0 pt-2 d-flex align-content-stretch">
                <b-card
                    class="shadow img-custom-card card-animation h-100"
                    style="width: 100%;"
                    @dragover.prevent
                    @dragenter.prevent
                    @dragstart="startDrag($event, appointmentType, 'appointmentType')"
                    draggable="true"
                    footer-bg-variant="transparent"
                    footer-border-variant="white"
                    no-body
                >
                  <b-card-text class="text-center m-0 pt-2">
                    <p class="m-0 font-weight-bold">{{ appointmentType.name }}</p>
                    <b-icon icon="file-earmark-medical" size="2rem"></b-icon>
                  </b-card-text>
                  <template #footer>
                    <b-button variant="primary" block @click="handleItemSelected(appointmentType, 'appointmentType')"
                              :disabled="appointment.appointmentType?.id === appointmentType.id"
                    >
                      {{
                        appointment.appointmentType?.id === appointmentType.id ? 'Seleccionado' : 'Seleccionar'
                      }}

                      <b-icon
                          v-if="appointment.appointmentType? appointment.appointmentType === appointmentType : false "
                          icon="check"
                          aria-hidden="true" variant="white"/>

                    </b-button>
                  </template>
                </b-card>
              </b-col>
            </b-row>
          </section>


          <section class="mt-2 mt-md-3 position-relative h-75 pb-5" v-else-if="sectionActive === 4">
            <b-card>
              <h3 class="text-center pb-4 text-primary">Ingresa los datos de la tarjeta</h3>
              <b-row>
                <b-col cols="12" md="5" class="mt-5">
                  <bank-card
                      :card-holder-p="creditCard.owner"
                      :card-number-p="creditCard.number"
                  >
                    <template #validDate>
                      {{ !creditCard.expirationDate.month ? 'MM' : creditCard.expirationDate.month }} /
                      {{ !creditCard.expirationDate.year ? 'YY' : creditCard.expirationDate.year.slice(-2) }}
                    </template>
                    <template #cvv>
                      {{ creditCard.cvv ? creditCard.cvv : 'CVV' }}
                    </template>
                  </bank-card>
                </b-col>

                <b-col>
                  <div>
                    <b-form-group>
                      <label for="cardNumber">Número de tarjeta</label>
                      <b-form-input
                          id="cardNumber"
                          placeholder="645879654123"
                          type="text"
                          required
                          v-model.trim="v$.creditCard.number.$model"
                          trim maxlength="16"
                          :state=" v$.creditCard.number.$dirty ? !v$.creditCard.number.$error : null"
                          @blur="v$.creditCard.number.$touch()"
                      >
                      </b-form-input>
                      <b-form-invalid-feedback v-for="error in v$.creditCard.number.$errors" :key="error.$uid">
                        {{ error.$message }}
                      </b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group>
                      <label for="cardNumber">Titular de la tarjeta</label>
                      <b-form-input
                          id="cardNumber"
                          placeholder="645879654123"
                          type="text"
                          required
                          v-model.trim="v$.creditCard.owner.$model"
                          trim maxlength="50"
                          :state=" v$.creditCard.owner.$dirty ? !v$.creditCard.owner.$error : null"
                          @blur="v$.creditCard.owner.$touch()"
                      >
                      </b-form-input>
                      <b-form-invalid-feedback v-for="error in v$.creditCard.owner.$errors" :key="error.$uid">
                        {{ error.$message }}
                      </b-form-invalid-feedback>
                    </b-form-group>
                    <b-form-group>
                      <label for="expirationDate">Fecha de expiración</label>
                      <b-row>
                        <b-col>
                          <p class="m-0 text-black-50">Mes</p>
                          <b-form-select :options="listMonths" v-model="creditCard.expirationDate.month">
                            <b-form-select-option :value="null" disabled>Selecciona una opción</b-form-select-option>
                          </b-form-select>
                        </b-col>
                        <b-col>
                          <p class="m-0 text-black-50">Año</p>
                          <b-form-select :options="listYears" v-model="creditCard.expirationDate.year">
                            <b-form-select-option :value="null" disabled>Selecciona una opción</b-form-select-option>
                          </b-form-select>
                        </b-col>
                      </b-row>
                    </b-form-group>

                    <b-form-group>
                      <label for="cvv">CVV</label>
                      <b-form-input
                          id="cvv"
                          placeholder="123"
                          type="password"
                          required
                          v-model.trim="v$.creditCard.cvv.$model"
                          trim
                          maxlength="3"
                          :state=" v$.creditCard.cvv.$dirty ? !v$.creditCard.cvv.$error : null"
                          @blur="v$.creditCard.cvv.$touch()"
                      >
                      </b-form-input>
                      <b-form-invalid-feedback v-for="error in v$.creditCard.cvv.$errors" :key="error.$uid">
                        {{ error.$message }}
                      </b-form-invalid-feedback>
                    </b-form-group>
                  </div>

                </b-col>
              </b-row>
            </b-card>
          </section>

        </b-col>
      </b-row>

      <term-and-condition-view @close="handleShowModal('termAndCondition')" :show="modalsShow.termAndCondition"
                               @ok="handleIsAcceptedTerms"/>


    </div>
  </b-overlay>
</template>


<script>
import Vue from "vue";
import moment from "moment/moment";
import {jwtDecode} from "jwt-decode";
import SweetAlertCustom from "@/kernel/SweetAlertCustom";
import areaController from "@/modules/patient/areas/services/controller/area.controller";
import specialtyController from "@/modules/patient/speciality/services/controller/speciality.controller";
import shiftController from "@/modules/patient/shifts/services/controller/shift.controller";
import appointmentTypeController
  from "@/modules/patient/appointment-type/services/controller/appointment-type.controller";
import appointmentController from "@/modules/patient/appointment/services/controller/appointment.controller";
import {helpers, maxLength, minLength, numeric, required} from "@vuelidate/validators";
import {useVuelidate} from "@vuelidate/core";
import {decrypt, encrypt} from "@/kernel/hashFunctions";


export default Vue.extend({
  name: "AppointmentView",
  components: {
    LoadingCustom: () => import("@/views/components/LoadingCustom.vue"),
    CustomBreadCrumb: () => import("@/modules/patient/appointment/views/components/CustomBreadCrumb.vue"),
    CustomNotFoundRegisters: () => import("@/modules/patient/appointment/views/components/CustomNotFoundRegisters.vue"),
    TermAndConditionView: () => import("@/modules/patient/appointment/views/components/TermAndConditionView.vue"),
    BankCard: () => import("@/modules/patient/appointment/views/components/BankCard.vue"),
    CustomLoadingSection: () => import("@/views/components/CustomLoadingSection.vue"),
  },
  mounted() {
    window.addEventListener('beforeunload', this.handleBeforeRouteLeave);
    this.handleShowModal('termAndCondition')
    this.getAllAreas();
    this.user = jwtDecode(localStorage.token).sub;
    this.user = this.user.charAt(0).toUpperCase() + this.user.slice(1);
    this.promotion.isActive = moment().day() === this.promotion.day.monday;
  },
  beforeDestroy() {
    window.removeEventListener('beforeunload', this.handleBeforeRouteLeave);
  },
  watch: {
    'pagination.data.name': function (val) {
      if (!val) this.getAllAreas()
    }
  },
  data() {
    return {
      sectionActive: 1,
      sections: [
        {step: 0, text: 'Inicio', to: {name: 'landing'}, icon: 'house-fill', active: false},
        {step: 1, text: 'Áreas', icon: 'geo-alt-fill', active: false},
        {step: 2, text: 'Especialidades', to: '#specialty', icon: 'calendar', active: false},
        {step: 3, text: 'Datos de la cita', to: '#appointment-data', icon: 'calendar', active: false},
        {step: 4, text: 'Pago', to: '#appointment-confirmation', icon: 'calendar', active: false},
      ],
      icons: {
        area: {
          delete: 'x-circle-fill',
        },
        specialty: {
          delete: 'x-circle-fill',
        },
        shift: {
          delete: 'x-circle-fill',
        },
        appointmentType: {
          delete: 'x-circle-fill',
        },
        date: {
          delete: 'x-circle-fill',
        }
      },
      modalsShow: {
        termAndCondition: false,
      },
      isLoading: false,
      showFullDescriptionIndex: -1,
      listYears: [],
      listMonths: [],
      appointmentTypeList: [],
      specialtyList: [],

      promotion: {
        isActive: false,
        day: {
          sunday: 0,
          monday: 1,
        },
        discount: {
          quantity: 20,
          type: 'percentaje'
        }
      },

      shiftList: [],
      listItems: [],
      appointment: {
        isAcceptedTerms: false,
        date: null,
        area: null,
        specialty: null,
        shift: null,
        appointmentType: null,
        discount: 0,
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

      creditCard: {
        number: null,
        expirationDate: {
          month: null,
          year: null,
        },
        cvv: null,
        owner: null
      },

      wasAppointmentSaved: false,

      errorMessagges: {
        required: 'Este campo es requerido',
        numbers: 'Este campo solo acepta números',
        creditCard: {
          minLenght: 'El número de tarjeta debe tener al menos 16 dígitos',
          maxLenght: 'El número de tarjeta debe tener máximo 16 dígitos',
          date: 'La fecha de expiración debe ser mayor a la fecha actual',
          cvv: 'El CVV debe tener 3 dígitos',
        }
      },
    };
  },
  methods: {
    isValidSectionData() {
      if (this.sectionActive === 1) {
        return Boolean(this.appointment.area && this.appointment.isAcceptedTerms)
      } else if (this.sectionActive === 2) {
        return Boolean(this.appointment.specialty)
      } else if (this.sectionActive === 3) {
        return this.appointment.shift && this.appointment.appointmentType && this.appointment.date;
      } else if (this.sectionActive === 4) {
        return this.appointment.isAcceptedTerms && this.creditCard.cvv && this.creditCard.expirationDate.month && this.creditCard.expirationDate.year && this.creditCard.number && this.creditCard.owner;
      }
      return false;
    },
    startDrag(evt, item, field) {
      evt.dataTransfer.dropEffect = "move";
      evt.dataTransfer.effectAllowed = "move";
      evt.dataTransfer.setData("item", JSON.stringify({item, field}));
    },
    onDrop(evt) {
      evt.preventDefault();
      const item = evt.dataTransfer.getData("item");
      const {field, ...parsedItem} = JSON.parse(item);
      this.handleItemSelected(parsedItem.item, field);
    },
    handleItemSelected(item, field) {
      this.appointment[field] = item;
    },
    handleNextStep() {

      if (!this.isValidSectionData()) {
        SweetAlertCustom.invalidForm()
        return
      }


      if (this.sectionActive === 1 && !this.appointment.area) {
        this.sectionActive = 1
      } else if (this.sectionActive === 1 && !this.appointment.specialty) {
        this.sectionActive = 2;
        this.pagination.data.name = null;
        this.getAllSpecialtiesByArea();
      } else if (
          this.sectionActive === 2 &&
          (!this.appointment.shift || !this.appointment.appointmentType || !this.appointment.date)) {
        this.sectionActive = 3;
        this.pagination.data.name = null;
        this.getAllShifts();
        this.getAllAppointmentTypes()
      } else if (this.sectionActive === 3) {

        //check if the fields are filled
        if (this.appointment.area && this.appointment.specialty && this.appointment.shift && this.appointment.appointmentType && this.appointment.date) {

          this.isAvailableAppointment({
            scheduledAt: this.appointment.date,
            speciality: {id: this.appointment.specialty.id,},
            shift: {id: this.appointment.shift.id,},
          }).then((resp) => {
            if (resp.data) {
              SweetAlertCustom.successMessage(null, 'Cita disponible', 'Complete los datos para agendar la cita')
              this.populateYears()
              this.sectionActive = 4;
            } else {
              SweetAlertCustom.infoMessage('No hay citas disponibles para la fecha seleccionada', 'Por favor, seleccione otra fecha o turno')
            }
          })

        } else {
          this.sectionActive = 3;
          SweetAlertCustom.infoMessage('Por favor, complete todos los campos')
        }
      }
    },
    handleSearch() {
      if (this.sectionActive === 1) {
        this.getAllAreas();
      } else if (this.sectionActive === 2) {
        this.getAllSpecialtiesByArea();
      }
    },
    toBack() {
      if (this.sectionActive === 1) {
        this.$router.back();
      } else {
        this.sectionActive--;
        if (this.sectionActive === 1) {
          this.getAllAreas();
          this.appointment = {
            shift: null,
            specialty: null,
            appointmentType: null,
            area: this.appointment.area,
            isAcceptedTerms: this.appointment.isAcceptedTerms
          }
        } else if (this.sectionActive === 2) {
          this.getAllSpecialtiesByArea();
          this.appointment = {
            shift: null,
            appointmentType: null,
            area: this.appointment.area,
            specialty: this.appointment.specialty,
            isAcceptedTerms: this.appointment.isAcceptedTerms
          }

        }
      }
    },
    handleIsAcceptedTerms() {
      this.appointment.isAcceptedTerms = true;
    },
    handleShowModal(name) {
      this.modalsShow[name] = !this.modalsShow[name];
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
        this.specialtyList = response.content;
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
          shift.departureHour = moment().set({hour: shift.departureHour, minute: 0}).format('hh:mm a');
          shift.entryHour = moment().set({hour: shift.entryHour, minute: 0}).format('hh:mm a');
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
        this.appointmentTypeList = response.data
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    async isAvailableAppointment(payload) {
      try {
        this.isLoading = true;
        return await appointmentController.isAppointmentAvailable(payload);
      } catch (error) {
        console.log(error);
      } finally {
        this.isLoading = false;
      }
    },
    async saveAppointment() {
      try {

        const responseAlert = await SweetAlertCustom.questionMessage('Una vez agendada no se podrá cancelar', '¿Estás seguro de que deseas agendar la cita?')
        if (!responseAlert.isConfirmed) return;


        const user = jwtDecode(localStorage.token);
        const userId = await decrypt(user.encryptedId)

        this.isLoading = true;
        const payload = {
          scheduledAt: this.appointment.date,
          patient: {
            id: Number(userId)
          },
          speciality: {
            id: this.appointment.specialty.id
          },
          appointmentType: {
            id: this.appointment.appointmentType.id
          },
          shift: {
            id: this.appointment.shift.id
          },
        }

        if (this.promotion.isActive) {
          payload.payment = {
            total: Number(this.appointment.specialty.cost),
            totalPaid: Number(this.appointment.specialty.cost - (this.appointment.specialty.cost * this.promotion.discount.quantity / 100)),
            discount: Number(this.promotion.discount.quantity)
          }
        } else {
          payload.payment = {
            total: Number(this.appointment.specialty.cost),
            totalPaid: Number(this.appointment.specialty.cost),
            discount: 0
          }
        }

        payload.payment.charge = await encrypt(JSON.stringify(payload))
        const response = await appointmentController.saveAppointment(payload);

        if (!response.error) {
          await SweetAlertCustom.successMessage(1500, 'Cita agendada', 'La cita ha sido agendada con éxito')
          setTimeout(() => {
            this.wasAppointmentSaved = true;
            this.$router.push({name: 'landing'})
          }, 1500)
        } else {
          this.isLoading = false;
          throw new Error('Error al agendar la cita')
        }

      } catch (error) {
        SweetAlertCustom.errorMessage('Error al agendar la cita', 'Por favor, intente nuevamente')
        console.log(error);
      } finally {
        this.isLoading = false;
      }

    },
    formatDate(date) {
      const local = moment(date);
      moment.locale('es');
      moment.lang('es', {
            months: 'Enero_Febrero_Marzo_Abril_Mayo_Junio_Julio_Agosto_Septiembre_Octubre_Noviembre_Diciembre'.split('_'),
            monthsShort: 'Enero._Feb._Mar_Abr._May_Jun_Jul._Ago_Sept._Oct._Nov._Dec.'.split('_'),
            weekdays: 'Domingo_Lunes_Martes_Miercoles_Jueves_Viernes_Sabado'.split('_'),
            weekdaysShort: 'Dom._Lun._Mar._Mier._Jue._Vier._Sab.'.split('_'),
            weekdaysMin: 'Do_Lu_Ma_Mi_Ju_Vi_Sa'.split('_')
          }
      );
      local.locale(false);
      return local.format('dddd, D [de] MMMM [de] YYYY');
    },
    dateTomorrow() {
      const today = moment();
      today.add(1, 'days').format('YYYY-MM-DD');
      return today;
    },
    dateMoreYear(year) {
      const today = moment();
      today.add(year, 'years').format('YYYY-MM-DD');
      return today;
    },
    toggleDescription(index, event) {
      event.preventDefault();
      this.showFullDescriptionIndex = this.showFullDescriptionIndex === index ? -1 : index;
    },
    questionToLeave() {
      return window.confirm('¿Estás seguro de que quieres salir?');
    },
    handleBeforeRouteLeave(e) {
      if(this.wasAppointmentSaved) {
        e.preventDefault();
        e.returnValue = '';
      }else if (this.questionToLeave()) {
        e.preventDefault();
        e.returnValue = '';
      }
    },
    populateYears() {
      const currentYear = moment().year();
      const endYear = currentYear + 30;
      for (let year = currentYear; year <= endYear; year++) {
        this.listYears.push(year.toString());
      }
      for (let month = 1; month <= 12; month++) {
        this.listMonths.push(month.toString());
      }
    }
  },
  beforeRouteLeave(to, from, next) {
    if (this.wasAppointmentSaved) next();
    else if (this.questionToLeave()) {
      next();
    } else {
      next(false);
    }
  },
  setup() {
    return {
      v$: useVuelidate(),
    };
  },
  validations() {
    return {
      creditCard: {
        owner: {
          required: helpers.withMessage(this.errorMessagges.required, required),
        },
        number: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          numeric: helpers.withMessage(this.errorMessagges.numbers, numeric),
          minLength: helpers.withMessage(this.errorMessagges.creditCard.minLenght, minLength(16)),
          maxLength: helpers.withMessage(this.errorMessagges.creditCard.maxLenght, maxLength(16)),
        },
        expirationDate: {
          month: {
            required: helpers.withMessage(this.errorMessagges.required, required),
          },
          year: {
            required: helpers.withMessage(this.errorMessagges.required, required),
          }

        },
        cvv: {
          required: helpers.withMessage(this.errorMessagges.required, required),
          numeric: helpers.withMessage(this.errorMessagges.numbers, numeric),
          minLength: helpers.withMessage(this.errorMessagges.creditCard.cvv, minLength(3)),
          maxLength: helpers.withMessage(this.errorMessagges.creditCard.cvv, maxLength(3)),
        },
      }
    }
  }
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

.x-close:hover {
  cursor: pointer;
}
</style>