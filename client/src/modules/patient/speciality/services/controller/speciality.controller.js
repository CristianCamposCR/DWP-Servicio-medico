import ApiService from "../service/speciality.gateway";

export default {
  async getAllSpecialtiesByArea(pagination) {
    try {
      return await ApiService.getAllSpecialtiesByArea(pagination);
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

};
