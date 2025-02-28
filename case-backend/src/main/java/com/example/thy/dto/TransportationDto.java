package com.example.thy.dto;

import com.example.thy.enums.TransportationTypeEnum;
import com.example.thy.exception.TransportationOperationDaysNotValidException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

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
    private TransportationTypeEnum transportationType;
    @Schema(
            description = "destination location of the transportation",
            name = "destinationLocation",
            type = "LocationDto",
            example = "{id:1}")
    private LocationDto destinationLocation;
    @Schema(
            description = "origin location of the transportation",
            name = "originLocation",
            type = "LocationDto",
            example = "{id:1}")
    private LocationDto originLocation;

    @Schema(
            description = "allowed oparation days for transportation",
            name = "operationDays",
            type = "int[]",
            example = "{id:1}")
    private Integer[] operationDays;



}
