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
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async getAreas() {
    try {
      const response = await axios.doGet(`/open/area/`);
      return response.data.data;
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
      const response = await axios.doPost("/management/speciality/", payload);
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async chageStatus(payload) {
    try {
      const response = await axios.doPatch(`/management/speciality/${payload}`);
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
      const response  = await axios.doGet(`management/speciality/${payload}`);
      response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message
      };
    }
  },

  async updateSpeciality(payload) {
    try {
      const response = await axios.doPut("management/speciality/", payload);
      response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message
      };
    }
  }
};
