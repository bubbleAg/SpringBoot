package com.koro.carshomework3.exception;

import com.koro.carshomework3.controller.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage carNotFoundExceptionHandler(CarNotFoundException ex) {
        ResponseMessage responseMsg = new ResponseMessage(ex.getMessage());
        return responseMsg;
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseMessage carAlreadyExistsHandler(CarAlreadyExistsException ex) {
        ResponseMessage responseMsg = new ResponseMessage(ex.getMessage());
        return responseMsg;
    }
}
