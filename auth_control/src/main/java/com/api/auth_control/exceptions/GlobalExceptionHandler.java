package com.api.auth_control.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.api.auth_control.dtos.ExceptionsDto;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ExceptionsDto> handleEmailAlreadyInUse(EmailAlreadyInUseException ex){
        var exceptionsDto = new ExceptionsDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionsDto);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionsDto> handleBadCredentials(){
        var exceptionsDto = new ExceptionsDto(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Provided credentials are invalid. Please check your email and password."
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionsDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionsDto> handleGenericException(){
        var exceptionsDto = new ExceptionsDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later."
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionsDto);
    }
}