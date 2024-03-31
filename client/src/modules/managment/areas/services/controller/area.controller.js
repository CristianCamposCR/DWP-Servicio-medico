import ApiService from "../service/area.gateway";

export default {
  async getAllAreas(pagination) {
    try {
      const data = await ApiService.getAllAreas(pagination);
      return data;
    } catch (error) {
      console.log(error);
    }
  },

  async saveArea(payload) {
    try {
      const data = await ApiService.saveArea(payload);
      return data;
    } catch (error) {
      console.log(error);
    }
  },
};
