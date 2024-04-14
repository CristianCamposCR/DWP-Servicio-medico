import ApiService from "../service/appointments,gateway";

export default {
  async getAllAppointmentsActive(pagination) {
    try {
      const data = await ApiService.getAllAppointmentActive(pagination);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
  async getAllAppointmentsPending(pagination) {
    try {
      const data = await ApiService.getAllAppointmentPending(pagination);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
  async getAllAppointmentsHistory(pagination) {
    try {
      const data = await ApiService.getAllAppointmentHistory(pagination);
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