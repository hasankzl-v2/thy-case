package com.example.thy.dto;

import com.example.thy.enums.TransferTypeEnum;
import com.example.thy.enums.TransportationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RouteLocationDto {

    @Schema(
            description = "location for route",
            name = "location",
            type = "LocationDto")
    private LocationDto location;
    @Schema(
            description = "Transportation Type",
            name = "transportationType",
            type = "TransportationTypeEnum",
            example = "BUS")
    private TransportationTypeEnum transportationType;
    @Schema(
            description = "Transfer Type",
            name = "transferType",
            type = "TransferTypeEnum",
            example = "AFTER_FLIGHT")
    private TransferTypeEnum transferType;
    @Schema(
            description = "Operation days",
            name = "operationDays",
            type = "Integer[]",
            example = "1")
    private Integer[] operationDays;

    public RouteLocationDto(LocationDto location, TransportationTypeEnum transportationTypeEnum, TransferTypeEnum transferTypeEnum,Integer[] operationDays) {
        this.location = location;
        this.transportationType = transportationTypeEnum;
        this.transferType = transferTypeEnum;
        this.operationDays = operationDays;
    }

    public RouteLocationDto(LocationDto location) {
        this.location = location;
    }
}
