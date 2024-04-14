import ApiService from "../service/appointments,gateway";

export default {
  async getAllAppointments(pagination) {
    try {
      const data = await ApiService.getAllAreas(pagination);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
}