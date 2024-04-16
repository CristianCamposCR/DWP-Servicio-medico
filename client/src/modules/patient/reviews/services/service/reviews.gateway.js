import axios from "@/config/client.gateway";

export default {
  async getReviewsByPatient() {
    try {
      const response = await axios.doGet(`/appointment/patient/to-review/`);
      return response.data.data;
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
      const response = await axios.doPost(`/review/`, payload);
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  }
};
