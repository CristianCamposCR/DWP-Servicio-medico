import ApiService from "@/modules/patient/appointment/services/service/appointment.gateway"

export default {
    isAppointmentAvailable(payload) {
        try {
            return ApiService.isAppointmentAvailable(payload)
        } catch (error) {
            return {
                code: error.data?.code,
                error: true,
                message: error.data?.message,
            };
        }
    },

    saveAppointment(payload) {
        try {
            return ApiService.saveAppointment(payload)
        } catch (error) {
            return {
                code: error.data?.code,
                error: true,
                message: error.data?.message,
            };
        }
    },
    async getAllAppointmentsActive(pagination) {
        try {
          const data = await ApiService.getAllAppointmentActive(pagination);
          return data;
        } catch (error) {
          return {
            code: error.data?.code,
            error: true,
            message: error.data?.message,
          };
        }
      },
      async getAllAppointmentsPending(pagination) {
        try {
          const data = await ApiService.getAllAppointmentPending(pagination);
          return data;
        } catch (error) {
          return {
            code: error.data?.code,
            error: true,
            message: error.data?.message,
          };
        }
      },
      async getAllAppointmentsHistory(pagination) {
        try {
          const data = await ApiService.getAllAppointmentHistory(pagination);
          return data;
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
          const data = await ApiService.cancelAppointment(id, payload);
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
