import Swal from "sweetalert2";

class SweetAlertCustom {
  static questionMessage(
    text,
    title = "¿Está seguro de realizar esta acción?"
  ) {
    return Swal.fire({
      title: title,
      text: text,
      showCancelButton: true,
      confirmButtonText: "Aceptar",
      confirmButtonColor: "#10B981",
      cancelButtonText: "Cancelar",
      cancelButtonColor: "#dc2626",
      reverseButtons: true,
      customClass: {
        actions: "custom-swal-buttons", // Esta es la clase personalizada que añadimos
      },
    });
  }

  static errorMessage(title, text) {
    Swal.fire({
      title: title,
      text: text,
      icon: "error",
      timer: 3000,
      showConfirmButton: false,
    });
  }

  static successMessage(timer, title = "Éxito", text = "Acción realizada") {
    if (!timer) timer = 1500;
    Swal.fire({
      title: title,
      text: text,
      icon: "success",
      timer: timer,
      showConfirmButton: false,
    });
  }

  static successMessageHtml(
    html,
    data,
    timer,
    title = "Éxito",
    confirmButtonText = "Aceptar"
  ) {
    return Swal.fire({
      title: title,
      html: html,
      icon: "success",
      timer: timer,
      showConfirmButton: true,
      confirmButtonText,
    }).then(() => {
      if (data)
        navigator.clipboard
          .writeText(data)
          .then(() =>
            this.successMessage(
              "Contraseña copiada",
              "La contraseña ha sido copiada al portapapeles"
            )
          );
    });
  }

  static infoMessage(title, text, timer) {
    if (!timer) timer = 3000;
    Swal.fire({
      title: title,
      text: text,
      icon: "info",
      timer: timer,
      showConfirmButton: false,
    });
  }

  static invalidForm(
    title = "Fomulario inválido",
    text = "Por favor llena todos los campos obligatorios para continuar."
  ) {
    Swal.fire({
      title: title,
      text: text,
      icon: "warning",
      confirmButtonText: "Aceptar",
      confirmButtonColor: "#10B981",
      showCancelButton: false,
      customClass: {
        actions: "custom-swal-buttons", // Esta es la clase personalizada que añadimos
      },
    });
  }

  static notImplemented() {
    Swal.fire({
      title: "En proceso...",
      text: "Esta funcionalidad aún no está implementada, estamos trabajando en ello. 🏨",
      icon: "info",
      timer: 3000,
      showConfirmButton: false,
    });
  }

  static welcomeMessage() {
    Swal.fire({
      title: "Bienvenido",
      text: "",
      icon: "success",
      timer: 1000,
      showConfirmButton: false,
    });
  }

  static async ErrorServer() {
    await Swal.fire({
      title: "El servidor no responde",
      text: "Sin respuesta del servidor",
      icon: "error",
      timer: 3000,
      confirmButtonColor: "#ddae23bb",
      showConfirmButton: false,
      allowOutsideClick: false,
      allowEscapeKey: false,
    });
  }
}

export default SweetAlertCustom;
