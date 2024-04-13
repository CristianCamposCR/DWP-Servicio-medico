import axios from "../../../../../config/client.gateway";

export default {
  async getAllSpecialties(pagination) {
    try {
      const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
      const response = await axios.doPost(
        `/open/speciality/paged/${payloadPagination}`,
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