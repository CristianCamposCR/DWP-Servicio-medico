import axios from "../../../../../config/client.gateway";

export default {
    async getProfile() {
        try {
          const response = await axios.doGet(`/patient/profile/`);
          return response.data.data;
        } catch (error) {
          return {
            code: error.data?.code,
            error: true,
            message: error.data?.message,
          };
        }
      },
}