package com.example.thy.service;

import com.example.thy.dto.RouteDto;
import com.example.thy.dto.RouteLocationDto;
import com.example.thy.dto.RouteRequestDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.enums.TransportationTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteService {

    private final TransportationService transportationService;


    public List<RouteDto> findAllValidRoutes(RouteRequestDto routeRequestDto) {
        List<RouteDto> validRoutes = new ArrayList<>();
        List<TransportationDto> transportationDtoListByOriginLocation = transportationService.findByOriginLocationId(routeRequestDto.getStartLocationId());

        transportationDtoListByOriginLocation.forEach(transportationDto -> {

            if(Objects.equals(transportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId()) && transportationDto.getTransportationType().equals(TransportationTypeEnum.FLIGHT)){
                RouteDto routeDto = new RouteDto();
                routeDto.getValidRoutes().add(new RouteLocationDto(transportationDto.getOriginLocation(),transportationDto.getTransportationType()));
                routeDto.getValidRoutes().add(new RouteLocationDto(transportationDto.getOriginLocation()));
                validRoutes.add(routeDto);
            }else{
                List<TransportationDto> transportationDtoListByOriginLocation1    = transportationService.findByOriginLocationId(transportationDto.getDestinationLocation().getId());

                transportationDtoListByOriginLocation1.forEach(transportationDto1 -> {
                    if(Objects.equals(transportationDto1.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())){
                        RouteDto routeDto = new RouteDto();
                        routeDto.getValidRoutes().add(new RouteLocationDto(transportationDto.getOriginLocation(),transportationDto.getTransportationType()));
                        routeDto.getValidRoutes().add(new RouteLocationDto(transportationDto1.getOriginLocation(),transportationDto1.getTransportationType()));
                        routeDto.getValidRoutes().add(new RouteLocationDto(transportationDto1.getDestinationLocation()));
                        validRoutes.add(routeDto);
                    }
                });
            }
        });
        return validRoutes;
    }
}
