package com.tlvflightscanner.validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * A global exception handler for the entire server
 */
@ControllerAdvice
public class ExceptionHandler {
    /**
     * Handles Illegal arguments exceptions only
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IllegalArgumentException> handleServerValidationException(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<>(new IllegalArgumentException(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles unhandled exceptions only
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleServerGeneralException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new Exception(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
