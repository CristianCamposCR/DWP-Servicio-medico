import ApiService from "../service/user.gateway";

export default{
    async changeStatus(payload) {
        try {
          const data = await ApiService.changeStatus(payload);
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