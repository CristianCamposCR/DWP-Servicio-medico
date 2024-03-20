<template>
  <div class="body">
    <div class="mt-5">
      <H1>ÁREAS</H1>
    </div>
    <b-row class="mt-5 ml-3">
      <b-col v-for="(area, key) in areas" :key="key" lg="3" md="6" sm="12">
        <b-card :title="area.name" :img-src="area.image" class="mb-2">
          <b-card-text class="card-text-scroll">
            <p class="area">Área</p>
            <b>Descripción:</b> {{ area.description }}<br />
          </b-card-text>
          <b-button @click="enableDisable" class="custom-button">Desactivar</b-button>
          <b-button @click="getOne" class="custom-button ml-3">
            <b-icon icon="eye" aria-hidden="true"></b-icon
          ></b-button>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  name: "AreaView",
  data() {
    return {
      areas: [
        {
          id: 0,
          name: "Medica",
          image:
            "https://imgs.search.brave.com/5yLy2Vd-AcHQFOAMoQtlMkUY5VNtYEPsmMJ2pLqI1HA/rs:fit:860:0:0/g:ce/aHR0cHM6Ly93YWxs/cGFwZXJzZXQuY29t/L3cvZnVsbC9iLzcv/OC80MDQzNy5qcGc",
          description: "",
        },
        {
          id: 0,
          name: "Quirurgica",
          image: "",
          description: "aaa",
        },
        {
          id: 0,
          name: "Diagnostico y Apoyo Clinico",
          image: "",
          description: "aaa",
        },
        {
          id: 0,
          name: "Diagnostico y Apoyo Clinico",
          image: "",
          description: "aaa",
        },
      ],
    };
  },

  methods: {
    async getArea() {
      try {
        console.log("consumo de areas");
      } catch (error) {
        console.log("trono papito", error);
      }
    },
    async getOne() {
      try {
        console.log("viending");
      } catch (error) {
        console.log("trono papito", error);
      }
    },
    async enableDisable() {
      try {
        console.log("desactivanding");
      } catch (error) {
        console.log("trono papito", error);
      }
    },
  },
  mounted() {
    this.getArea();
  },
};
</script>

<style>
.body {
  padding-left: 20px;
  padding-right: 20px;
  margin-top: 10px;
}

@media screen and (max-width: 768px) {
  .body {
    padding-left: 10px;
    padding-right: 10px;
  }
}

.custom-button {
  background-color: #10b981;
  border-color: #10b981;
  color: white;
  font-size: 15px;
  padding: 4px 20px;
}

.card-text-scroll {
  max-height: 150px;
  overflow-y: auto;
}
.area {
  color: slategray;
  font-family: sans-serif;
}
</style>
