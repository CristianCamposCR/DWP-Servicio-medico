import AxiosClient from "./axios";
import router from "../router";
import Vue from "vue";
import SweetAlertCustom from "@/kernel/SweetAlertCustom";

// Interceptor que manda el token en todas las peticiones
AxiosClient.interceptors.request.use(
  function (config) {
    const auth_token = localStorage.token;
    if (
      auth_token &&
      (!config.url.includes("auth") || !config.url.includes("open"))
    ) {
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

    const { status } = error.response;
    switch (status) {
      case 400:
      case "BAD_REQUEST":
        handle400Error(error);
        break;
      case 401:
      case "UNAUTHORIZED":
        handle401Error(error);
        break;
      case 403:
      case "FORBIDEN":
        Vue.swal(
          "Acceso restringido",
          "No cuentas con permisos necesarios para acceder al recurso",
          "warning"
        );
        break;
      case 404:
      case "NOT_FOUND":
        handle404Error(error);
        break;
      case 500:
      case "INTERNAL_SERVER_ERROR":
        Vue.swal(
          "Error interno",
          "Se ha presentado un error en los servicios, vuelve a intentarlo",
          "warning"
        );
        break;
    }
    return Promise.reject(error.response);
  }
);

function handle400Error(error) {
  const { message } = error.response.data;
  let titleAlert = "";
  let messageAlert = "";

  switch (message.trim()) {
    // AUTH
    case "USER_IS_BLOCKED":
      titleAlert = "Credenciales incorrectas";
      messageAlert = "Usuario y/o contraseña erróneos";
      break;
    case "USER_IS_INACTIVE":
      titleAlert = "La cuenta se encuentra desactivada";
      messageAlert = "Contacta al administrador para que active la cuenta";
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
      messageAlert = "El registro no puede ser eliminado, tiene dependencias";
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
      break;
    case "DUPLICATED_AREA":
      titleAlert = "Registro duplicado";
      messageAlert = "Esta área ya existe";
      break;
    case "DUPLICATED_DOCTOR":
      titleAlert = "Registro duplicado";
      messageAlert = "Información en uso";
      break;
    // APPOINTMENTS
    case "NO_RESCHEDULES_REMAINING":
      titleAlert = "Sin reagendas disponibles";
      messageAlert = "Ya has usado tu oportunidad de reagendar en esat cita";
      break;
    case "DUPLICATED_PATIENT":
      titleAlert = "Registro duplicado";
      messageAlert = "Información en uso";
      break;
    case "DUPLICATED_USER":
      titleAlert = "Registro duplicado";
      messageAlert = "Información en uso";
      break;
    case "NO_AVAILABILITY":
      titleAlert = "Sin disponibilidad";
      messageAlert = "No hay disponibilidad en esa fecha";
      break;
    case "AREA_HAS_DEPENDENCIES":
      titleAlert = "Área con dependencias";
      messageAlert = "El área está siendo utilizada";
      break;
    case "AREA_IS_INACTIVE":
      titleAlert = "Área inactiva";
      messageAlert = "El área está inactiva";
      break;
    case "DUPLICATED_SPECIALITY":
      titleAlert = "Registro duplicado";
      messageAlert = "Esta área ya existe";
      break;
    case "SPECIALITY_HAS_DEPENDENCIES":
      titleAlert = "Especialidad con dependencias";
      messageAlert = "La especialidad está siendo utilizada";
      break;
    case "SPECIALITY_IS_INACTIVE":
      titleAlert = "Especialidad inactiva";
      messageAlert = "La espacialidad está inactiva";
      break;
    case "USER_HAS_DEPENDENCIES":
      titleAlert = "Usuario con dependencias";
      messageAlert = "El usuario está siendo utilizado";
      break;
    case "INCORRECT_CURRENT_PASSWORD":
      titleAlert = "Contraseña incorrecta";
      messageAlert = "Contreaseña incorrecta";
      break;
    case "PASSWORD_ARE_THE_SAME":
      titleAlert = "Coincidencia de contraseña";
      messageAlert =
        "La nueva contraseña no puede ser la misma que ya has utilizado";
      break;
    case "DUPLICATED_PATIENT":
      titleAlert = "Registro duplicado";
      messageAlert = "Este paciente ya existe";
      break;
  }
  if (message !== "Review request")
    Vue.swal({
      title: titleAlert,
      text: messageAlert,
      icon: "warning",
      confirmButtonText: "Aceptar",
      confirmButtonColor: "#10B981",
    });
}

function handle401Error(error) {
  const { message } = error.response.data;

  let titleAlert = "";
  let messageAlert = "";

  switch (message.trim()) {
    // AUTH
    case "CREDENTIALS_MISMATCH":
      titleAlert = "Credenciales incorrectas";
      messageAlert = "Usuario y/o contraseña erróneos";
      break;
    case "EXPIRED_SESSION":
      Vue.swal(
        "Sesión expirada",
        "Por seguridad tu sesión se ha cerrado, vuelve a iniciar sesión",
        "warning"
      ).then((result) => {
        router.push("/");
      });
      localStorage.clear();
      break;
    case "USER_IS_INACTIVE":
      titleAlert = "Usuario inactivo";
      messageAlert = "EL usuario se encuentra deshabilitado";
      break;
    case "USER_IS_BLOCKED":
      titleAlert = "Usuario bloqueado";
      messageAlert = "EL usuario está bloqueado";
      break;
  }
  if (message !== "EXPIRED_SESSION")
    Vue.swal({
      title: titleAlert,
      text: messageAlert,
      icon: "warning",
      confirmButtonText: "Aceptar",
      confirmButtonColor: "#10B981",
    });
}

function handle404Error(error) {
  const { message } = error.response.data;

  let titleAlert = "";
  let messageAlert = "";

  switch (message.trim()) {
    case "NO_AREA_FOUND":
      titleAlert = "No se encontró";
      messageAlert = "No se encontró el área";
      break;
    case "NO_SPECIALITY_FOUND":
      titleAlert = "No se encontró";
      messageAlert = "No se encontró la especialidad";

      break;
    case "NO_DATA_FOUND":
      titleAlert = "No hay datos";
      messageAlert = "No se encontraron datos";
      break;
    case "RECORDS_ARE_NOT_RELATED":
      titleAlert = "Registros no relacionados";
      messageAlert = "Los registros no están relacionados";
      break;
    case "NO_STATUS_FOUND":
      titleAlert = "No se econtró el estado";
      messageAlert = "No se encontró el estado";
      break;
    case "NO_USER_FOUND":
      titleAlert = "No se encontro el usuario";
      messageAlert = "No se encontró el usuario";
      break;
    case "CREDENTIALS_MISMATCH":
      titleAlert = "Credenciales incorrectas";
      messageAlert = "Usuario y/o contraseña erróneos";
      break;
    case "NO_ROLE_FOUND":
      titleAlert = "No se encontró";
      messageAlert = "No se encontró el rol";
      break;
    case "NO_DOCTOR_FOUND":
      titleAlert = "No se encontró";
      messageAlert = "No se encontró el doctor";
      break;
    default:
      titleAlert = "No encontrado";
      messageAlert = "No se encontró el recurso";
      break;
  }
  if (message !== "Review request")
    Vue.swal({
      title: titleAlert,
      text: messageAlert,
      icon: "warning",
      confirmButtonText: "Aceptar",
      confirmButtonColor: "#10B981",
    });
}

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
