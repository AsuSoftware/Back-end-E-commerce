package com.asusoftware.ecommerce.handlerExceptions;

import com.asusoftware.ecommerce.exceptions.NotFoundAdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class AdExceptionHandler {

    @ExceptionHandler(value =  NotFoundAdException.class)
    protected ResponseEntity<Object> handleException(NotFoundAdException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus httpStatus, String errorMessage) {
        ApiException apiException =
                new ApiException(errorMessage, httpStatus, ZonedDateTime.now());
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
