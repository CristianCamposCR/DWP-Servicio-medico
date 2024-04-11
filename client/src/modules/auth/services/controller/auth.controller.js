import ApiService from "../service/auth.gateway";

export default {
  async login(payload) {
    try {
      const data = await ApiService.login(payload);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
  async signup(payload) {
    try {
      const data = await ApiService.singup(payload);
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
  async activateAccount(payload) {
    try {
      const data = await ApiService.activateAccount(payload);
      console.log("controller",data)
      return data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
};
