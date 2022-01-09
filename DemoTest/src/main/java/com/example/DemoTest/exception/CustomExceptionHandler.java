package com.example.DemoTest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public  ErrorRespose handlerNotFoundException(NotFoundException exception, WebRequest webRequest){
        return new ErrorRespose(HttpStatus.NOT_FOUND,exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public  ErrorRespose handlerUnauthorizedException(UnauthorizedException exception, WebRequest webRequest){
        return new ErrorRespose(HttpStatus.UNAUTHORIZED,exception.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public  ErrorRespose handlerForbiddenException(ForbiddenException exception, WebRequest webRequest){
        return new ErrorRespose(HttpStatus.FORBIDDEN,exception.getMessage());
    }
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public  ErrorRespose handlerAlreadyExistsException(AlreadyExistsException exception, WebRequest webRequest){
        return new ErrorRespose(HttpStatus.CONFLICT,exception.getMessage());
    }
}
