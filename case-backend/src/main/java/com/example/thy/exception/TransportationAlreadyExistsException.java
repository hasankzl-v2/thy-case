package com.example.thy.exception;

public class TransportationAlreadyExistsException extends RuntimeException {
    public TransportationAlreadyExistsException(String message) {
        super(message);
    }
}
