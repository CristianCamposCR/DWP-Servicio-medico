import ApiService from "../service/speciality.gateway";

export default {
  async getAllSpecialties(pagination) {
    try {
      const data = await ApiService.getAllSpecialties(pagination);
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