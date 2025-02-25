package com.example.thy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransportationNotFoundException extends RuntimeException {
    private Long id;
    public TransportationNotFoundException(String message,Long id) {
        super(message);
        this.id = id;
    }
}
