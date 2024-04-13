import ApiService from "../service/shift.gateway";

export default {
    async getAllShift() {
      try {
        const data = await ApiService.getAllShift();
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