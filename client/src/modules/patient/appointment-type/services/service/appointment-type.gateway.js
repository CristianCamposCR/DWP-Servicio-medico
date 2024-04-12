import axios from "@/config/client.gateway";

export default {
  async getAllAppointmentType() {
    try {
      const response = await axios.doGet(`/open/appointment-type/`);
      return response.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
};
