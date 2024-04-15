import ApiService from "../service/appointments,gateway";

export default {
  async getAllAppointmentsByDoctor(pagination) {
    try {
      const data = await ApiService.getAllAppointmentByDoctor(pagination);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async getAllAppointmentsHistoryByDoctor(pagination) {
    try {
      const data = await ApiService.getAllAppointmentHistoryByDoctor(pagination);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async cancelAppointment(id, payload) {
    try {
      const data = await ApiService.cancelAppointment(id, payload);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async notifyNonAvailability(id) {
    try {
      const data = await ApiService.notifyNonAvailability(id);
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