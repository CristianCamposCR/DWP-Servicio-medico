import { refresh } from "aos";
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
      if (
        response.data.data === null &&
        response.data.message === "USER_IS_NOT_VERIFIED" &&
        response.data.status === "OK"
      )
        return response.data;
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
      const newPhonewNumber = `+52${payload.phoneNumber}`;
      const newPayload = {
        ...payload,
        password: encryptPassword,
        phoneNumber: newPhonewNumber,
      };
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

  async activateAccount(payload) {
    try {
      const response = await axios.doPost(
        "/open/verification-code/activate-account/",
        { code: payload }
      );
      return response.data;
    } catch (error) {
      return {
        code: error.data?.code,
        error: true,
        message: error.data?.message,
      };
    }
  },
  async refreshActivationCode(payload) {
    try {
      const response = await axios.doPost(
        "/open/verification-code/refresh-activation-code/",
        payload
      );
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
