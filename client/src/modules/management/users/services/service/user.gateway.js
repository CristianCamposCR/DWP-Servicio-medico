import axios from "../../../../../config/client.gateway";

export default{
    async changeStatus(payload) {
        try {
          const response = await axios.doPatch(`/management/user/${payload}`);
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