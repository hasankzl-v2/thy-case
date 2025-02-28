package com.example.thy.handler;

import com.example.thy.dto.response.ErrorResponse;
import com.example.thy.exception.*;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Hidden
public class GlobalExceptionHandler {


    @ExceptionHandler(LocationAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleLocationAlreadyExistsException(LocationAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("LOCATION_ALREADY_EXIST", "Location with given location code is already exists."));
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleLocationNotFoundException(LocationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("LOCATION_NOT_FOUND", "Location not found with given id. " + ex.getId()));
    }

    @ExceptionHandler(TransportationAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTransportationAlreadyExistsException(TransportationAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("TRANSPORTATION_ALREADY_EXIST", "Transportation with given data is already exists."));

    }

    @ExceptionHandler(TransportationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTransportationNotFoundException(TransportationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("TRANSPORTATION_NOT_FOUND", "Transportation not found with given id. " + ex.getId()));

    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("ERROR", ex.getMessage()));
    }

    @ExceptionHandler(TransportationOperationDaysNotValidException.class)
    public ResponseEntity<ErrorResponse> handleTransportationOperationDaysNotValidException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("TRANSPORTATION_OPERATION_DAYS_NOT_VALID", "Transportation operation days not valid."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleViolationException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("VIOLATION_ERROR", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class) // Tüm Exception türlerini yakalar
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("UNEXPECTED_ERROR", ex.getMessage()));
    }


}