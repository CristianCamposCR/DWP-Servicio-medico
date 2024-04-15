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
    },
    async getAllAppointmentActive(pagination) {
        try {
          const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
          const response = await axios.doPost(
            `/appointment/patient/active/paged/${payloadPagination}`,
            pagination.data
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
      async getAllAppointmentPending(pagination) {
        try {
          const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
          const response = await axios.doPost(
            `/appointment/patient/pending/paged/${payloadPagination}`,
            pagination.data
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
      async getAllAppointmentHistory(pagination) {
        try {
          const payloadPagination = `?page=${pagination.page}&size=${pagination.size}&sort=${pagination.sort}&direction=${pagination.direction}`;
          const response = await axios.doPost(
            `/appointment/patient/history/paged/${payloadPagination}`,
            pagination.data
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
      async cancelAppointment(id, payload) {
        try {
          const response = await axios.doPost(`/appointment/cancel/${id}`, payload);
          return response.data.data;
        } catch (error) {
          return {
            code: error.data?.code,
            error: true,
            message: error.data?.message,
          };
        }
      },
      async getOne(id) {
        try {
          const response = await axios.doGet(`/appointment/${id}`);
          return response.data.data;
        } catch (error) {
          return {
            code: error.data?.code,
            error: true,
            message: error.data?.message,
          };
        }
      },
      async reescheduleAppointment(id, payload) {
        try {
          const response = await axios.doPost(`/appointment/reschedule/${id}`, payload);
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
