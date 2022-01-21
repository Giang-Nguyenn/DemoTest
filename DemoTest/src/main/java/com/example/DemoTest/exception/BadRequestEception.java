package com.example.DemoTest.exception;

public class BadRequestEception extends RuntimeException {
    public BadRequestEception(String message){
        super(message);
    }
}
