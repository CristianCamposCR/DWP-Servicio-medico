import ApiService from "../service/records.gateway";

export default {
    async saveRecords(payload) {
        try {
          const data = await ApiService.saveRecord(payload);
          return data;
        } catch (error) {
          return {
            code: error.data?.code,
            error: true,
            message: error.data?.message,
          };
        }
      },  
}