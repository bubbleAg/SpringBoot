package com.koro.carshomework3.controller;

public class ResponseMessage {

    private String message;

    public ResponseMessage(String msg){
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
