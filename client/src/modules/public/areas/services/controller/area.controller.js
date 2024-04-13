import ApiService from "../service/area.gateway";

export default {
  async getAllAreas(pagination) {
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