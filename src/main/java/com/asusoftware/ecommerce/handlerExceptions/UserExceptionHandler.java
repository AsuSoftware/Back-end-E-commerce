package com.asusoftware.ecommerce.handlerExceptions;

import com.asusoftware.ecommerce.exceptions.InvalidPasswordException;
import com.asusoftware.ecommerce.exceptions.NotFoundAdException;
import com.asusoftware.ecommerce.exceptions.NotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

// Annotiamo la classe per far capire a Spring che questa classe si occupa di gestire le eccezioni
// Indica che questa classe gestisce le eccezioni per le chiamate rest
@RestControllerAdvice
public class UserExceptionHandler {

    // Indica che questo metodo gestira l'eccezione NotFoundUserException
    @ExceptionHandler(value =  NotFoundUserException.class)
    protected ResponseEntity<Object> handleException(NotFoundUserException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }


    @ExceptionHandler(value =  InvalidPasswordException.class)
    protected ResponseEntity<Object> handleException(InvalidPasswordException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(value =  Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    private ResponseEntity<Object> buildResponse(HttpStatus httpStatus, String errorMessage) {
        ApiException apiException =
                new ApiException(errorMessage, httpStatus, ZonedDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
