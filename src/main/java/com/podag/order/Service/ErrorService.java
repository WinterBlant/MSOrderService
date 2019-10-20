package com.podag.order.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.naming.directory.InvalidAttributeValueException;
import javax.naming.directory.InvalidSearchFilterException;

import java.security.InvalidParameterException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ErrorService {
    @ExceptionHandler({InvalidSearchFilterException.class})
    public ResponseEntity<String> handleNoOrdersException(InvalidSearchFilterException e) {
        return error(NO_CONTENT, e);
    }

    @ExceptionHandler({InvalidParameterException.class})
    public ResponseEntity<String> handleNoOrderByOrderIDException(InvalidParameterException e) {
        return error(NOT_FOUND, e);
    }

    @ExceptionHandler({IllegalArgumentException.class, InvalidAttributeValueException.class, Exception.class})
    public ResponseEntity<String> handleException(Exception e) {
        return error(BAD_REQUEST, e);
    }

    private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(e.getMessage());
    }
}
