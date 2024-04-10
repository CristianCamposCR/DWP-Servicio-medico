import axios from "../../../../config/client.gateway";
import { encrypt } from "../../../../kernel/hashFunctions";
export default {
  async login(payload) {
    try {
      const encryptPassword = await encrypt(payload.password);
      const response = await axios.doPost("/auth/login", {
        username: payload.username,
        password: encryptPassword,
      });
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },

  async singup(payload) {
    try {
      const encryptPassword = await encrypt(payload.password);
      const newPayload = { ...payload, password: encryptPassword };
      const response = await axios.doPost("/auth/signup/", newPayload);
      return response.data.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
};
