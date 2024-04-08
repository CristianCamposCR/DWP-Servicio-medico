import ApiService from "../service/doctor.gateway";

export default {
  async getAllDoctors(pagination) {
    try {
      const data = await ApiService.getAllDoctors(pagination);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
      f;
    }
  },

  async getOne(payload) {
    try {
      const data = await ApiService.getOne(payload);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
};
