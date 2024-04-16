import moment from "moment";

export const signal = (event) => {
  if (isNaN(event.key) && onlyPositive(event)) return false;
  else return true;
};

export const onlyPositive = (event) => {
    if (event.keyCode >= 48 && event.keyCode <= 57) {
      return true; // Permitir dígitos del 0 al 9
    } else {
      event.preventDefault(); // Previene la acción predeterminada del evento
      return false; // Rechazar cualquier otro carácter
    }
  }
  


  export const onlyNumber = (event) => {
    if ((isNaN(event.key) && event.key !== '.') && onlyPositiveNumbers(event)) return false;
    else return true;
  };
  
  export const onlyPositiveNumbers = (event) => {
      if ((event.keyCode >= 48 && event.keyCode <= 57) || event.key === '.') {
        return true; // Permitir dígitos del 0 al 9
      } else {
        event.preventDefault(); // Previene la acción predeterminada del evento
        return false; // Rechazar cualquier otro carácter
    }
}


export const onlyCustomNumber = (event) => {
    const { key } = event;
    const listKeysAllows = ['Backspace', 'Delete', 'ArrowLeft', 'ArrowRight', 'Tab', 'Enter', 'Escape', 'Delete', 'Shift', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
    const verify = listKeysAllows.includes(key);
    if (!verify) event.preventDefault(); //not write the key
    return verify;
};


export const formatStyleText = (text, type) => {
    if (!text) return '---'
    let newText = text?.trim();
    if (type === 'uppercase') newText = text.toUpperCase();
    if (type === 'lowercase') newText = text.toLowerCase();
    return newText;
}



/*
  * @Section: Date Moment
 */

moment.updateLocale('es', {
    months: 'Enero_Febrero_Marzo_Abril_Mayo_Junio_Julio_Agosto_Septiembre_Octubre_Noviembre_Diciembre'.split('_'),
    monthsShort: 'Ene_Feb_Mar_Abr_May_Jun_Jul_Ago_Sep_Oct_Nov_Dic'.split('_'),
    weekdays: 'Domingo_Lunes_Martes_Miércoles_Jueves_Viernes_Sábado'.split('_'),
    weekdaysShort: 'Dom_Lun_Mar_Mié_Jue_Vie_Sáb'.split('_'),
    weekdaysMin: 'Do_Lu_Ma_Mi_Ju_Vi_Sá'.split('_')
});
moment.locale('es');

export const formatDate = (date) => moment(date).format('dddd, D [de] MMMM [de] YYYY')
