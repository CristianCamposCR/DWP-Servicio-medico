import axios from "../../../../../config/client.gateway";

export default{
    async getAllShift() {
        try {
          const response = await axios.doGet(`/open/shift/`);
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