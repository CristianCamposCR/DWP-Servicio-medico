import ApiService from "../service/gender.gateway";

export default {
    async getAllGender() {
      try {
        const data = await ApiService.getAllGender();
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