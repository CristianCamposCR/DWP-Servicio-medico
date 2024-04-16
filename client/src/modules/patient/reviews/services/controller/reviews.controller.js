import ApiService from "../service/reviews.gateway";

export default {
  async getReviewsByPatient() {
    try {
      return await ApiService.getReviewsByPatient()
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async saveReview(payload) {
    try {
      return await ApiService.saveReview(payload)
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  }
};
