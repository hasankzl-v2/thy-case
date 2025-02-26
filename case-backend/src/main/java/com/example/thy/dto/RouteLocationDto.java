package com.example.thy.dto;

import com.example.thy.enums.TransportationTypeEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RouteLocationDto {

    private LocationDto location;
    private TransportationTypeEnum transportationType;


    public RouteLocationDto(LocationDto location, TransportationTypeEnum transportationTypeEnum) {
        this.location = location;
        this.transportationType = transportationTypeEnum;
    }

    public RouteLocationDto(LocationDto location) {
        this.location = location;
    }
}
