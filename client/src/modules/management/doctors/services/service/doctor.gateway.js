import axios from "../../../../../config/client.gateway";

export default {
  async getAllDoctors(pagination) {
    try {
      const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
      const response = await axios.doPost(
        `/management/doctor/paged/${payloadPagination}`,{searchValue: pagination.data.name}
        
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
      const response = await axios.doGet(`/management/doctor/${payload}`);
      return response.data.data;
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
      const response = await axios.doPost("/management/doctor/", payload);
      return response.data.data;
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
      const response = await axios.doPut("/management/doctor/", payload);
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async getAvailableDoctors(payload) {
    try {
      const response = await axios.doPost("/management/doctor/available/", payload);
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async getAvailableAuxDoctors(payload) {
    try {
      const response = await axios.doPost("/management/doctor/aux/available/", payload);
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async getAvailableHours(payload) {
    try {
      const response = await axios.doPost("/management/doctor/available-hours/", payload);
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
