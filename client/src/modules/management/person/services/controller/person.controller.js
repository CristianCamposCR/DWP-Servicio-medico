import ApiService from "../service/person.gateway";

export default{
    async update(payload) {
        try {
          const data = await ApiService.update(payload);
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