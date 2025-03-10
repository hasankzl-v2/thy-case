package com.example.thy.dto;

import com.example.thy.enums.TransportationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransportationDto {

    @Schema(
            description = "id of the transportation",
            name = "id",
            type = "Long",
            example = "1")
    private Long id;
    @Schema(
            description = "transportation type of the transportation",
            name = "transportationType",
            type = "TransportationTypeEnum",
            example = "BUS")
    @NotNull(message = "transportation Type Location should be set")
    private TransportationTypeEnum transportationType;
    @Schema(
            description = "destination location of the transportation",
            name = "destinationLocation",
            type = "LocationDto")
    @NotNull(message = "destination Location should not be set")
    private LocationDto destinationLocation;
    @Schema(
            description = "origin location of the transportation",
            name = "originLocation",
            type = "LocationDto")
    @NotNull(message = "originLocation should be set")
    private LocationDto originLocation;

    @Schema(
            description = "allowed operation days for transportation",
            name = "operationDays",
            type = "array",
            example = "[1,2,3,4]")
    @NotNull(message = "operation days  should not be empty")
    private Integer[] operationDays;



}
