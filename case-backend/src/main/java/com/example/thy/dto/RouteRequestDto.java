package com.example.thy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RouteRequestDto {

    @NotNull(message = "Start location id should be set")
    private Long startLocationId;

    @NotNull(message = "End location id  should be set")
    private Long endLocationId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Departure date should be set")
    private LocalDate departureDate;
}
