package com.rms.recruitEdge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ExceptionMessage> userNotFoundHandler(RuntimeException ex){
        return ResponseEntity.status(404).body(new ExceptionMessage(ex.getMessage()));
    }

    
}
