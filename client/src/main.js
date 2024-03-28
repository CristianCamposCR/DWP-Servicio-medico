import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import "./assets/main.css";

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap/dist/js/bootstrap.min.js'



import AOS from 'aos';
import 'aos/dist/aos.css';
import './assets/main.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)



new Vue({
  router,
  render: (h) => h(App),
  mounted() {
    AOS.init();
  }
}).$mount('#app')
