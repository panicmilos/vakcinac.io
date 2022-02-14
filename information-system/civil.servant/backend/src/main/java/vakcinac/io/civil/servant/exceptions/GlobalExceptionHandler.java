package vakcinac.io.civil.servant.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vakcinac.io.core.exceptions.ErrorObject;
import vakcinac.io.core.exceptions.HandleableException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {HandleableException.class})
	public ResponseEntity<ErrorObject> handleException(HandleableException exception) {
		ErrorObject errorObject = new ErrorObject(exception.getMessage(), exception.getPayload(), exception.getCode());
		
		return new ResponseEntity<>(errorObject, exception.getCode());
	}
}
