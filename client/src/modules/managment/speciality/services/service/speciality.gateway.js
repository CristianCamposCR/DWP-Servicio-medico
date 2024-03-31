import axios from "../../../../../config/client.gateway";

export default {
  async getAllSpecialties(pagination) {
    try {
      const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort},${pagination.direction}`;
      const response = await axios.doPost(
        `/management/speciality/paged/${payloadPagination}`,
        pagination.data
      );
      return response.data.data;
    } catch (error) {
      console.log(error);
    }
  },

  async saveSpeciality(payload) {
    try {
      const response = await axios.doPost("/management/speciality", payload);
      return response.data.data;
    } catch (error) {
      console.log(error);
    }
  },
};
