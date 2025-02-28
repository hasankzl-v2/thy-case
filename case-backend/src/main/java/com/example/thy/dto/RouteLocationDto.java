package com.example.thy.dto;

import com.example.thy.enums.TransferTypeEnum;
import com.example.thy.enums.TransportationTypeEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RouteLocationDto {

    private LocationDto location;
    private TransportationTypeEnum transportationType;
    private TransferTypeEnum transferType;
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
