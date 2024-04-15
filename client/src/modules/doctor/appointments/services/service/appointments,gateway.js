import axios from "../../../../../config/client.gateway";

export default {
  async getAllAppointmentByDoctor(pagination) {
    try {
      const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
      const response = await axios.doPost(
        `/appointment/doctor/assigned/paged/${payloadPagination}`,
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

  async getAllAppointmentHistoryByDoctor(pagination) {
    try {
      const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
      const response = await axios.doPost(
        `/appointment/doctor/history/paged/${payloadPagination}`,
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
}