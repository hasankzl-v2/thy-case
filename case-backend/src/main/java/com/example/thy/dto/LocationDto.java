package com.example.thy.dto;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LocationDto {
    private Long id;

    private String country;

    private String City;

    private String LocationCode;
}
