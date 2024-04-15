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
    let newText = text?.trim();
    if (type === 'uppercase') newText = text.toUpperCase();
    if (type === 'lowercase') newText = text.toLowerCase();
    return newText;
}
