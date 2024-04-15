import axios from "../../../../../config/client.gateway";

export default {

async saveRecord(payload) {
    try {
      const response = await axios.doPost("/record/", payload);
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