import ApiService from "../service/speciality.gateway";

export default {
  async getAllSpecialties(pagination) {
    try {
      const data = await ApiService.getAllSpecialties(pagination);
      return data;
    } catch (error) {
      console.log(error);
    }
  },

  async saveSpeciality(payload) {
    try {
      const data = await ApiService.saveSpeciality(payload);
      return data;
    } catch (error) {
      console.log(error);
    }
  },
};
