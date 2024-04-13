import axios from "../../../../../config/client.gateway";

export default{
    async getAllGender() {
        try {
          const response = await axios.doGet(`/open/gender/`);
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