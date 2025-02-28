package com.example.thy.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RouteRequestDto {
    @Schema(
            description = "id of the Start location ",
            name = "id",
            type = "Long",
            example = "1")
    @NotNull(message = "Start location id should be set")
    private Long startLocationId;
    @Schema(
            description = "id of the End location ",
            name = "id",
            type = "Long",
            example = "1")
    @NotNull(message = "End location id  should be set")
    private Long endLocationId;
    @Schema(
            description = "Departure date",
            name = "departureDate",
            type = "Date",
            example = "30/04/2025")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Departure date should be set")
    private LocalDate departureDate;
}
