package com.example.udemywebflux.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.udemywebflux.dto.ErrorResponse;
import com.example.udemywebflux.exception.CustomException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        
        if(e instanceof CustomException ce){
            return ResponseEntity.badRequest().body(new ErrorResponse(ce.errorCode, ce.message));
        }

        return ResponseEntity.badRequest().body(new ErrorResponse(1000, "unknown error"));
    }
}
