package com.example.udemywebflux.exception;

public class CustomException extends RuntimeException {
    public int errorCode;
    public String message;

    public CustomException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}
