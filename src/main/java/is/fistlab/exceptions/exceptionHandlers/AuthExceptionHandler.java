package is.fistlab.exceptions.exceptionHandlers;

import is.fistlab.controllers.Response;
import is.fistlab.exceptions.auth.UserConflictException;
import is.fistlab.exceptions.auth.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response<String> userNotFound(UserNotFoundException ex) {
        return new Response<>(ex.getMessage());
    }

    @ExceptionHandler(UserConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Response<String> userConflict(UserConflictException ex) {
        return new Response<>(ex.getMessage());
    }

}
