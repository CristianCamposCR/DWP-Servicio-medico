import axios from "../../../../../config/client.gateway";

export default {
  async getAllAreas(pagination) {
    try {
      const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort},${pagination.direction}`;
      const response = await axios.doPost(
        `/management/area/paged/${payloadPagination}`,
        pagination.data
      );
      return response.data.data;
    } catch (error) {
      console.log(error);
    }
  },

  async saveArea(payload) {
    try {
      const response = await axios.doPost("/management/area/", payload);
      return response.data.data;
    } catch (error) {
      console.log(error);
    }
  },
};
