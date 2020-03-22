package com.koro.carshomework3.exception;

public class CarAlreadyExistsException extends RuntimeException {

    public CarAlreadyExistsException(long id) {
        super("Car with id=" + id + " already exists");
    }
}
