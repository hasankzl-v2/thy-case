package com.example.thy.service;

import com.example.thy.dto.*;
import com.example.thy.dto.request.RouteRequestDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.enums.TransportationTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteService {

    private final TransportationService transportationService;



    public RouteValidationDto findAllValidRoutes(RouteRequestDto routeRequestDto) {
        RouteValidationDto routeValidationDtoResponseDto = new RouteValidationDto();
        List<TransportationDto> firstTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(routeRequestDto.getStartLocationId());

        firstTransportationDtoListByOriginLocation.forEach(firstTransportationDto -> {

            if (Objects.equals(firstTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId()) && firstTransportationDto.getTransportationType().equals(TransportationTypeEnum.FLIGHT)) {
                RouteDto routeDto = new RouteDto(routeRequestDto.getDepartureDate(),routeRequestDto.getStartLocationId(),routeRequestDto.getEndLocationId());
                routeDto.addValidRoute(firstTransportationDto.getOriginLocation(),firstTransportationDto.getTransportationType(),firstTransportationDto.getOperationDays());
                routeDto.addValidRoute(firstTransportationDto.getDestinationLocation(),firstTransportationDto.getTransportationType(),firstTransportationDto.getOperationDays());
                routeValidationDtoResponseDto.addRouteIfValid(routeDto);
            } else {
                List<TransportationDto> secondTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(firstTransportationDto.getDestinationLocation().getId());

                secondTransportationDtoListByOriginLocation.forEach(secondTransportationDto -> {
                    if (Objects.equals(secondTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                        RouteDto routeDto =  new RouteDto(routeRequestDto.getDepartureDate(),routeRequestDto.getStartLocationId(),routeRequestDto.getEndLocationId());
                        routeDto.addValidRoute(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
                        routeDto.addValidRoute(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType(), secondTransportationDto.getOperationDays());
                        routeDto.addValidRoute(secondTransportationDto.getDestinationLocation(), secondTransportationDto.getTransportationType(), secondTransportationDto.getOperationDays());
                        routeValidationDtoResponseDto.addRouteIfValid(routeDto);
                    } else {
                        List<TransportationDto> thirdTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(secondTransportationDto.getDestinationLocation().getId());
                        thirdTransportationDtoListByOriginLocation.forEach(thirdTransportationDto -> {
                            if (Objects.equals(thirdTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                                RouteDto routeDto =  new RouteDto(routeRequestDto.getDepartureDate(),routeRequestDto.getStartLocationId(),routeRequestDto.getEndLocationId());
                                routeDto.addValidRoute(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
                                routeDto.addValidRoute(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType(),secondTransportationDto.getOperationDays());
                                routeDto.addValidRoute(thirdTransportationDto.getOriginLocation(), thirdTransportationDto.getTransportationType(), thirdTransportationDto.getOperationDays());
                                routeDto.addValidRoute(thirdTransportationDto.getDestinationLocation(), thirdTransportationDto.getTransportationType(), thirdTransportationDto.getOperationDays());
                                routeValidationDtoResponseDto.addRouteIfValid(routeDto);
                            }
                        });
                    }
                });
            }
        });
        log.info("Found valid routes count: {}", routeValidationDtoResponseDto.getValidRoutes().size());
        return routeValidationDtoResponseDto;
    }
}
