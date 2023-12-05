package udm.pixo.board.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import udm.pixo.board.common.Api;

@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ValidationExceptionHandler {
	
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Api> validationException(
            MethodArgumentNotValidException exception
    ) {
    	var status = HttpStatus.BAD_REQUEST;
        var errorMessageList = exception.getFieldErrors()
                .stream()
                .map(it -> {
                    var format = "%s : { %s } 은 %s";
                    var message = String.format(format, it.getField(), it.getRejectedValue(), it.getDefaultMessage());
                    return message;
                }).toList();

        var errorResponse = Api.error(errorMessageList, status);

        return ResponseEntity.status(status).body(errorResponse);
    }
}
