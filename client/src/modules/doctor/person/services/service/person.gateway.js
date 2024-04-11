import axios from "../../../../../config/client.gateway";

export default{
    async update(payload) {
        try {
          const response = await axios.doPut("/person/", payload);
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