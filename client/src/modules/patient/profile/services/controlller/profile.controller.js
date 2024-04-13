import ApiService from "../service/profile.gateway";

export default{
    async getProfile() {
        try {
          const data = await ApiService.getProfile();
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