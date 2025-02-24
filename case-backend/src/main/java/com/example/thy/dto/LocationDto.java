package com.example.thy.dto;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LocationDto  extends BaseDto {
    private String country;

    private String City;

    private String LocationCode;
}
