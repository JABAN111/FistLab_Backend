package is.fistlab.exceptions.exceptionHandlers;

import is.fistlab.exceptions.mappers.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MappersExceptionHandler {

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fieldInvalid(InvalidFieldException exc){
        return exc.getMessage();
    }

}