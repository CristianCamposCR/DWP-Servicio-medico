import AxiosClient from "./axios";
import router from "../router";
import Vue from "vue";
import SweetAlertCustom from "@/kernel/SweetAlertCustom";

// Interceptor que manda el token en todas las peticiones
AxiosClient.interceptors.request.use(
  function (config) {
    const auth_token = localStorage.token;
    if (auth_token && !config.url.includes("auth")) {
      config.headers.Authorization = `Bearer ${auth_token}`;
    }
    return config;
  },
  function (err) {
    return Promise.reject(err);
  }
);

// Interceptor para manejo de errores
AxiosClient.interceptors.response.use(
  (response) => {
    const { status } = response;
    if (status === 200 || status === 201) {
      return Promise.resolve(response);
    } else {
      return Promise.reject(response);
    }
  },
  async (error) => {
    if (!error.response) {
      await SweetAlertCustom.ErrorServer();
      return Promise.reject(error);
    }
    const {
      response: { status },
    } = error;
    if (status) {
      switch (status) {
        case 400: {
          const {
            response: {
              data: { message },
            },
          } = error;
          if (message) {
            let messageAlert = "",
              titleAlert = "";
            switch (message.trim()) {
              // AUTH
              case "CREDENTIALS_MISMATCH":
                titleAlert = "Credenciales incorrectas";
                messageAlert = "Usuario y/o contraseña erróneos";
                break;
              case "USER_IS_BLOCKED":
                titleAlert = "Credenciales incorrectas";
                messageAlert = "Usuario y/o contraseña erróneos";
                break;
              case "USER_IS_INACTIVE":
                titleAlert = "La cuenta se encuentra desactivada";
                messageAlert =
                  "Contacta al administrador para que active la cuenta";
                break;
              // CRUD operations
              case "MISSING_FIELDS":
                titleAlert = "Campos faltantes";
                messageAlert = "Por favor completa todos los campos";
                break;
              case "RECORD_NOT_REGISTERED":
                titleAlert = "Registro no guardado";
                messageAlert = "El registro no ha sido guardado";
                break;
              case "RECORD_NOT_UPDATED":
                titleAlert = "Registro no actualizado";
                messageAlert = "El registro no ha sido actualizado";
                break;
              case "RECORD_NOT_DELETED":
                titleAlert = "Registro no eliminado";
                messageAlert = "El registro no ha sido eliminado";
                break;
              case "RECORD_HAS_DEPENDENCIES":
                titleAlert = "Registro con dependencias";
                messageAlert =
                  "El registro no puede ser eliminado, tiene dependencias";
                break;
              case "INVALID_FIELDS":
                titleAlert = "Campos inválidos";
                messageAlert = "Por favor verifica los campos";
                break;
              case "DUPLICATED_RECORD":
                titleAlert = "Registro duplicado";
                messageAlert = "El registro ya existe";
                break;
              case "REVIEW_REQUEST":
                titleAlert = "Solicitud de revisión";
                messageAlert = "Solicitud de revisión";
                break;
              case "The curp is already registered":
                titleAlert = "Registro duplicado";
                messageAlert = "Esta CURP ya existe";
              case "DUPLICATED_AREA":
                titleAlert = "Registro duplicado";
                messageAlert = "Esta área ya existe";
            }
            if (message !== "Review request")
              Vue.swal(titleAlert, messageAlert, "warning");
            break;
          }
          if (message !== "Review request")
            Vue.swal("ERROR", error.response.data.message, "error");
          break;
        }
        case 401: {
          console.log("401 error", "handle interceptor");
          Vue.swal(
            "Sesión expirada",
            "Por seguridad tu sesión se ha cerrado, vuelve a iniciar sesión",
            "warning"
          ).then((result) => {
            router.push("/");
          });
          localStorage.clear();
          break;
        }
        case 403: {
          Vue.swal(
            "Acceso restringido",
            "No cuentas con permisos necesarios para acceder al recurso",
            "warning"
          );
          break;
        }
        case 404: {
          const {
            response: {
              data: { message },
            },
          } = error;
          console.info(message, "message error, handle interceptor");
          if (message) {
            let messageAlert = "",
              titleAlert = "";
            switch (message.trim()) {
              case "NO_DATA_FOUND":
                titleAlert = "No hay datos";
                messageAlert = "No se encontraron datos";
                break;
              case "RECORDS_ARE_NOT_RELATED":
                titleAlert = "Registros no relacionados";
                messageAlert = "Los registros no están relacionados";
                break;
              case "NO_STATUS_FOUND":
                titleAlert = "No se econtro el estado";
                messageAlert = "No se encontró el estado";
                break;
              case "NO_USER_FOUND":
                titleAlert = "No se encontro el usuario";
                messageAlert = "No se encontró el usuario";
                break;
              default:
                titleAlert = "No encontrado";
                messageAlert = "No se encontró el recurso";
                break;
            }
          }
          break;
        }
        case 500: {
          Vue.swal(
            "Error interno",
            "Se ha presentado un error en los servicios, vuelve a intentarlo",
            "warning"
          );
          break;
        }
      }
      return Promise.reject(error.response);
    }
    return Promise.reject(error);
  }
);

export default {
  doGet(endPoint) {
    return AxiosClient.get(endPoint);
  },
  doPost(endPoint, object, config) {
    return AxiosClient.post(endPoint, object, config);
  },
  doPut(endPoint, object, config) {
    return AxiosClient.put(endPoint, object, config);
  },
  doPatch(endPoint, object, config) {
    return AxiosClient.patch(endPoint, object, config);
  },
  doDelete(endPoint, config) {
    return AxiosClient.delete(endPoint, config);
  },
};
