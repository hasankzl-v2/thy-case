package com.example.thy.dto.request;


import com.example.thy.validator.FieldsMatch;
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
    @NotNull(message = "transportationType should not be null when updating")
    private TransportationTypeEnum transportationType;
    @Schema(
            description = "destination location id of the transportation",
            name = "destinationLocationId",
            type = "Long",
            example = "1")
    @NotNull(message = "destinationLocationId should not be null when updating")
    private Long destinationLocationId;
    @Schema(
            description = "origin location id of the transportation",
            name = "originLocationId",
            type = "Long",
            example = "1")
    @NotNull(message = "originLocationId should not be null when updating")
    private Long originLocationId;

    @Schema(
            description = "allowed operation days for transportation",
            name = "operationDays",
            type = "array",
            example = "[1,2,3,4,5]")
    @NotNull(message = "operationDays should not be null when updating")
    private Integer[] operationDays;
}
