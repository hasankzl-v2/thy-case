package com.example.thy.dto.request;


import com.example.thy.defination.FieldsMatch;
import com.example.thy.dto.LocationDto;
import com.example.thy.enums.TransportationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@FieldsMatch(firstField = "destinationLocationId", secondField = "originLocationId",shouldMatch = false, message = "Locations should not be same")
public class UpdateTransportationRequestDto {
    @Schema(
            description = "id of the transportation",
            name = "id",
            type = "Long",
            example = "1")
    @NotNull(message = "id should not be null when updating")
    private Long id;
    @Schema(
            description = "transportation type of the transportation",
            name = "transportationType",
            type = "TransportationTypeEnum",
            example = "BUS")
    private TransportationTypeEnum transportationType;
    @Schema(
            description = "destination location id of the transportation",
            name = "destinationLocationId",
            type = "Long",
            example = "1")
    private Long destinationLocationId;
    @Schema(
            description = "origin location id of the transportation",
            name = "originLocationId",
            type = "Long",
            example = "1")
    private Long originLocationId;

    @Schema(
            description = "allowed oparation days for transportation",
            name = "operationDays",
            type = "int[]",
            example = "{id:1}")
    private Integer[] operationDays;
}
