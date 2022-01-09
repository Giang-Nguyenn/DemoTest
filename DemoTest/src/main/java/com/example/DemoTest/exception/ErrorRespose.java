package com.example.DemoTest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorRespose {
    HttpStatus httpStatus;
    String message;
    Date time = new Date();
//
    public ErrorRespose(HttpStatus httpStatus,String message){
        this.httpStatus=httpStatus;
        this.message=message;
    }
}
