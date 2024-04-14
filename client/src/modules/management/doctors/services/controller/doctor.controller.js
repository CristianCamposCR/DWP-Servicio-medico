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
    }
  },

  async saveDoctor(payload) {
    try {
      const data = await ApiService.saveDoctor(payload);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
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
  async update(payload) {
    try {
      const data = await ApiService.update(payload);
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