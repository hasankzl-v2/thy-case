package com.example.thy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationNotFoundException extends RuntimeException {
    private Long id;
    public LocationNotFoundException(String message,Long id) {
        super(message);
        this.id = id;
    }
}