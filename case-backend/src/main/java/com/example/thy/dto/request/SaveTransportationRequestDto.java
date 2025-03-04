package com.example.thy.dto.request;

import com.example.thy.validator.FieldsMatch;
import com.example.thy.dto.LocationDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.enums.TransportationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@FieldsMatch(firstField = "destinationLocationId", secondField = "originLocationId",shouldMatch = false, message = "locations should not be same")
public class SaveTransportationRequestDto {

    @Schema(
            description = "transportation type of the transportation",
            name = "transportationType",
            type = "TransportationTypeEnum",
            example = "BUS")
    @NotNull(message = "transportation Type Location should be set")
    private TransportationTypeEnum transportationType;
    @Schema(
            description = "destination location id of the transportation",
            name = "destinationLocationId",
            type = "Long",
            example = "1")
    @NotNull(message = "destination Location should not be set")
    private Long destinationLocationId;
    @Schema(
            description = "origin location id of the transportation",
            name = "originLocationId",
            type = "Long",
            example = "1")
    @NotNull(message = "originLocationId should be set")
    private Long originLocationId;

    @Schema(
            description = "allowed operation days for transportation",
            name = "operationDays",
            type = "array",
            example = "[1,2,3,4]")
    @NotNull(message = "operation days  should not be empty")
    private Integer[] operationDays;

    public TransportationDto convertToDto() {
        TransportationDto transportationDto = new TransportationDto();
        transportationDto.setTransportationType(transportationType);
        transportationDto.setOperationDays(operationDays);
        transportationDto.setDestinationLocation(new LocationDto(destinationLocationId));
        transportationDto.setOriginLocation(new LocationDto(originLocationId));
        return transportationDto;
    }
}
