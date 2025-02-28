package com.example.thy.service;

import com.example.thy.dto.*;
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


    public ValidRoutesResponseDto findAllValidRoutes(RouteRequestDto routeRequestDto) {
        ValidRoutesResponseDto validRoutesResponseDto = new ValidRoutesResponseDto();
        List<TransportationDto> firstTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(routeRequestDto.getStartLocationId());

        firstTransportationDtoListByOriginLocation.forEach(firstTransportationDto -> {

            if (Objects.equals(firstTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId()) && firstTransportationDto.getTransportationType().equals(TransportationTypeEnum.FLIGHT)) {
                RouteDto routeDto = new RouteDto(routeRequestDto.getDepartureDay(),routeRequestDto.getStartLocationId(),routeRequestDto.getEndLocationId());
                routeDto.addValidRoute(firstTransportationDto.getOriginLocation(),firstTransportationDto.getTransportationType(),firstTransportationDto.getOperationDays());
                routeDto.addValidRoute(firstTransportationDto.getDestinationLocation(),firstTransportationDto.getTransportationType(),firstTransportationDto.getOperationDays());
                validRoutesResponseDto.addRouteIfValid(routeDto);
            } else {
                List<TransportationDto> secondTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(firstTransportationDto.getDestinationLocation().getId());

                secondTransportationDtoListByOriginLocation.forEach(secondTransportationDto -> {
                    if (Objects.equals(secondTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                        RouteDto routeDto =  new RouteDto(routeRequestDto.getDepartureDay(),routeRequestDto.getStartLocationId(),routeRequestDto.getEndLocationId());
                        routeDto.addValidRoute(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
                        routeDto.addValidRoute(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType(), secondTransportationDto.getOperationDays());
                        routeDto.addValidRoute(secondTransportationDto.getDestinationLocation(), secondTransportationDto.getTransportationType(), secondTransportationDto.getOperationDays());
                        validRoutesResponseDto.addRouteIfValid(routeDto);
                    } else {
                        List<TransportationDto> thirdTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(secondTransportationDto.getDestinationLocation().getId());
                        thirdTransportationDtoListByOriginLocation.forEach(thirdTransportationDto -> {
                            if (Objects.equals(thirdTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                                RouteDto routeDto =  new RouteDto(routeRequestDto.getDepartureDay(),routeRequestDto.getStartLocationId(),routeRequestDto.getEndLocationId());
                                routeDto.addValidRoute(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
                                routeDto.addValidRoute(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType(),secondTransportationDto.getOperationDays());
                                routeDto.addValidRoute(thirdTransportationDto.getOriginLocation(), thirdTransportationDto.getTransportationType(), thirdTransportationDto.getOperationDays());
                                routeDto.addValidRoute(thirdTransportationDto.getDestinationLocation(), thirdTransportationDto.getTransportationType(), thirdTransportationDto.getOperationDays());
                                validRoutesResponseDto.addRouteIfValid(routeDto);
                            }
                        });
                    }
                });
            }
        });
        return validRoutesResponseDto;
    }
}
