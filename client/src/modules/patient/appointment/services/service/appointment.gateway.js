import axios from "@/config/client.gateway";

export default {

    async isAppointmentAvailable(data) {
        try {
            const response = await axios.doPost(`/appointment/check-availability/`, data);
            return response.data;
        } catch (error) {
            return {
                code: error.data?.code,
                error: true,
                message: error.data?.message,
            };
        }
    },

    async saveAppointment(data) {
        try {
            const response = await axios.doPost(`/appointment/`, data);
            return response.data;
        } catch (error) {
            return {
                code: error.data?.code,
                error: true,
                message: error.data?.message,
            };
        }
    }
};
