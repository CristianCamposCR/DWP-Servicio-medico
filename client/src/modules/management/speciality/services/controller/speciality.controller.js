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

  async saveSpeciality(payload) {
    try {
      const data = await ApiService.saveSpeciality(payload);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async changeStatus(payload) {
    try {
      const data = await ApiService.chageStatus(payload);
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

  async updateSpeciality(payload) {
    try {
      const data = await ApiService.updateSpeciality(payload);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async getAreas() {
    try {
      const data = await ApiService.getAreas();
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
  async getSpecialties() {
    try {
      const data = await ApiService.getSpeciality();
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
