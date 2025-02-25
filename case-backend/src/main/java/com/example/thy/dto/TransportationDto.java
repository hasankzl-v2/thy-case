package com.example.thy.dto;

import com.example.thy.enums.TransportationTypeEnum;
import lombok.Data;

@Data
public class TransportationDto {

    private  Long id;
    private TransportationTypeEnum transportationType;
    private LocationDto destinationLocation;
    private LocationDto originLocation;

}
