package com.koro.carshomework3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String carNotFoundExceptionHandler(CarNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String carAlreadyExistsHandler(CarAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
