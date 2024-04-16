<template>
  <div>
    <router-view></router-view>
    <ModalListReview @close="openCloseSidebar" :show="isShow" :list="listReviews" @saveReview="saveReview" @updateList="getReviews"/>
    <LoadingCustom :show="isLoading"/>

  </div>
</template>
<script>
import Vue from "vue";
import ReviewsController from "@/modules/patient/reviews/services/controller/reviews.controller";
import SweetAlertCustom from "@/kernel/SweetAlertCustom";

export default Vue.extend({
  name: "AppointmentView",
  components: {
    ModalListReview: () => import("@/modules/patient/reviews/components/ModalListReviews.vue"),
    LoadingCustom: () => import("@/views/components/LoadingCustom.vue")
  },
  mounted() {
    this.getReviews();
  },
  data() {
    return {
      isShow: false,
      isLoading: false,
      listReviews: [],
    }
  },
  methods: {
    openCloseSidebar() {
      this.isShow = !this.isShow;
    },
    async getReviews(){
      try {
        const response = await ReviewsController.getReviewsByPatient();
        if (response.length === 1){
          this.listReviews = response;
          this.isShow = true;
        }else{
          this.isShow = false;
        }
      }catch (e) {
        console.error(e);
      }
    },
    async saveReview(review, ok){
      try {
        this.isLoading = true;
        const response =  await ReviewsController.saveReview(review);
        if (response) {
          if (ok) SweetAlertCustom.successMessage()
          this.isShow = false;
        }
      }catch (e) {
        console.error(e);
      }finally {
        this.isLoading = false;
      }
    }
  }
});

</script>
