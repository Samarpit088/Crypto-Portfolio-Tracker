package com.crypto.portfolio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(RuntimeException ex){
        Map<String,Object> error = new HashMap<>();
        error.put("timeStamp", LocalDateTime.now());
        error.put("status", HttpStatus.BAD_REQUEST.value());
        error.put("error",ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationException(MethodArgumentNotValidException ex){
        Map<String,Object> error = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError->
                error.put(fieldError.getField(),fieldError.getDefaultMessage())
                );

        Map<String,Object> response = new HashMap<>();
        response.put("timeStamp",LocalDateTime.now());
        response.put("status",HttpStatus.BAD_REQUEST.value());
        response.put("errors",error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String,Object>> handleAccessDenied(AccessDeniedException ex){
        Map<String,Object> error = new HashMap<>();
        error.put("timeStamp",LocalDateTime.now());
        error.put("status",HttpStatus.FORBIDDEN.value());
        error.put("error","Access Denied");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

}
