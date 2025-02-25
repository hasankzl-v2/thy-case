package com.example.thy.handler;

import com.example.thy.dto.ErrorResponse;
import com.example.thy.exception.LocationAlreadyExistsException;
import com.example.thy.exception.LocationNotFoundException;
import com.example.thy.exception.TransportationAlreadyExistsException;
import com.example.thy.exception.TransportationNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(LocationAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLocationAlreadyExistsException(LocationAlreadyExistsException ex) {
        return new ErrorResponse("LOCATION_ALREADY_EXIST", "Location with given location code is already exists.");
    }

    @ExceptionHandler(LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleLocationNotFoundException(LocationNotFoundException ex) {
        return new ErrorResponse("LOCATION_NOT_FOUND", "Location not found with given id. " + ex.getId());
    }

    @ExceptionHandler(TransportationAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTransportationAlreadyExistsException(TransportationAlreadyExistsException ex) {
        return new ErrorResponse("TRANSPORTATION_ALREADY_EXIST", "Transportation with given data is already exists.");
    }

    @ExceptionHandler(TransportationNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTransportationNotFoundException(TransportationNotFoundException ex) {
        return new ErrorResponse("TRANSPORTATION_NOT_FOUND", "Transportation not found with given id. " + ex.getId());
    }

    @ExceptionHandler(Exception.class) // Tüm Exception türlerini yakalar
    public ErrorResponse handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorResponse("UNEXPECTED_ERROR", ex.getMessage());
    }

}