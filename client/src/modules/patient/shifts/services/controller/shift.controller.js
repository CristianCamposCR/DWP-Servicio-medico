import ApiService from "@/modules/patient/shifts/services/service/shift.gateway";

export default {
  getAllShifts(pagination) {
    try {
      return ApiService.getAllShifts(pagination);
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
};
