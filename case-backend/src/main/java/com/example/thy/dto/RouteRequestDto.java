package com.example.thy.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RouteRequestDto {
    private Long startLocationId;
    private Long endLocationId;
    private Integer departureDay;
}
