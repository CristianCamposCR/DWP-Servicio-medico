import ApiService from "@/modules/patient/appointment-type/services/service/appointment-type.gateway";

export default {
  getAllAppointment() {
    try {
      return ApiService.getAllAppointmentType()
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
};
