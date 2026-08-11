package com.example.mini_jira.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> resNotFoundEx(ResourceNotFoundException ex){

        log.warn("Resource Not found Field: {}", ex.getFieldName());

        Map<String, String> response = new HashMap<>();

        response.put(ex.getFieldName(), ex.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(response);
    
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage()));

        errors
            .forEach((field,error) ->
                log.warn("Validation Error, Field: {}, Message: {}", field, error));

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errors);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredToken(ExpiredJwtException ex) {
        
        Map<String, String> response = new HashMap<>();
        
        response.put("JWT Token has expired", ex.getMessage());
        
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(response);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleInvalidToken(JwtException ex) {
        
        Map<String, String> response = new HashMap<>();
        
        response.put("Invalid JWT Token", ex.getMessage());
        
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFound(UsernameNotFoundException ex){

        Map<String, String> response = new HashMap<>();

        response.put("User Not Found", ex.getMessage());

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(response);
    }
}
