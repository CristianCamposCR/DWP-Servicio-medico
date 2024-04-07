package mx.edu.utez.server.utils;

import mx.edu.utez.server.kernel.Errors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ResponseApi<List<String>>> processUnmergeException(final MethodArgumentNotValidException ex) {

        List<String> list = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ResponseApi<List<String>> res = new ResponseApi<>(
                list,
                HttpStatus.BAD_REQUEST,
                true,
                Errors.REVIEW_REQUEST.name()
        );

        return new ResponseEntity<>(res, res.getStatus());
    }
}
