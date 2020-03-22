package com.koro.carshomework3.exception;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(int id) {
        super("Could not find car with id: " + id);
    }
}
