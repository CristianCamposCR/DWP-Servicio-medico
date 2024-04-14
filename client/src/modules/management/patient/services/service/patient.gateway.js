import axios from "../../../../../config/client.gateway";

export default {
  async getAllPatients(pagination) {
    try {
      const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
      const response = await axios.doPost(
        `/management/patient/paged/${payloadPagination}`,
        { searchValue: pagination.data.name }
      );
      return response.data.data;
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
      const response = await axios.doGet(`/management/patient/${payload}`);
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
};
