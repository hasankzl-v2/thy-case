package com.example.thy.service;

import com.example.thy.dto.*;
import com.example.thy.dto.request.RouteRequestDto;
import com.example.thy.dto.TransportationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class RouteService {

    private final TransportationService transportationService;

    @Value("${app.flight-transfer-limit:1}")
    private int flightTransferLimit;
    @Value("${app.before-flight-transfer-limit:1}")
    private int beforeFlightTransferLimit;
    @Value("${app.after-flight-transfer-limit:1}")
    private int afterFlightTransferLimit;

    /*
     * return all valid routes by given parameters
     * */
    public RouteValidationDto findAllValidRoutes(RouteRequestDto routeRequestDto) {
        RouteValidationDto routeValidationDtoResponseDto = new RouteValidationDto();

        // find transportation by origin is equal to startLocationId
        List<TransportationDto> firstTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(routeRequestDto.getStartLocationId());

        /*
         * loop transportation, if transportation destination is equal to end location then add routeValidationDto
         * */
        firstTransportationDtoListByOriginLocation.forEach(firstTransportationDto -> {

            // destination reached
            if (Objects.equals(firstTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                addRouteValidation(routeRequestDto, firstTransportationDto, routeValidationDtoResponseDto);
            }
            // If the first attempt to reach the end location for transportation fails, try reaching it through the transportation destination.
            else {
                processIndirectRoute(routeRequestDto, firstTransportationDto, routeValidationDtoResponseDto);
            }
        });
        log.info("Found valid routes count: {}", routeValidationDtoResponseDto.getValidRoutes().size());
        return routeValidationDtoResponseDto;
    }

    private void processIndirectRoute(RouteRequestDto routeRequestDto, TransportationDto firstTransportationDto, RouteValidationDto routeValidationDtoResponseDto) {
        // find transportation by given destination
        List<TransportationDto> secondTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(firstTransportationDto.getDestinationLocation().getId());
        secondTransportationDtoListByOriginLocation.forEach(secondTransportationDto -> {
            // destination reached second try
            if (Objects.equals(secondTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                addRouteValidation(routeRequestDto, firstTransportationDto, secondTransportationDto, routeValidationDtoResponseDto);
            } else {
                // If the second attempt to reach the end location for transportation fails, try reaching it through the transportation destination.
                processIndirectRoute(routeRequestDto, firstTransportationDto, routeValidationDtoResponseDto, secondTransportationDto);
            }
        });
    }

    private void processIndirectRoute(RouteRequestDto routeRequestDto, TransportationDto firstTransportationDto, RouteValidationDto routeValidationDtoResponseDto, TransportationDto secondTransportationDto) {
        // find transportation by given destination
        List<TransportationDto> thirdTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(secondTransportationDto.getDestinationLocation().getId());
        thirdTransportationDtoListByOriginLocation.forEach(thirdTransportationDto -> {
            // When the end location is reached, add the route.
            if (Objects.equals(thirdTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                addRouteValidation(routeRequestDto, firstTransportationDto, secondTransportationDto, thirdTransportationDto, routeValidationDtoResponseDto);
            }
        });
    }

    private void addRouteValidation(RouteRequestDto routeRequestDto, TransportationDto firstTransportationDto, RouteValidationDto routeValidationDtoResponseDto) {
        RouteDto routeDto = createRoute(routeRequestDto);
        routeDto.addValidRoute(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
        routeDto.addValidRoute(firstTransportationDto.getDestinationLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
        routeValidationDtoResponseDto.addRouteIfValid(routeDto);
    }

    private void addRouteValidation(RouteRequestDto routeRequestDto, TransportationDto firstTransportationDto, TransportationDto secondTransportationDto, RouteValidationDto routeValidationDtoResponseDto) {
        RouteDto routeDto = createRoute(routeRequestDto);
        routeDto.addValidRoute(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
        routeDto.addValidRoute(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType(), secondTransportationDto.getOperationDays());
        routeDto.addValidRoute(secondTransportationDto.getDestinationLocation(), secondTransportationDto.getTransportationType(), secondTransportationDto.getOperationDays());
        routeValidationDtoResponseDto.addRouteIfValid(routeDto);
    }


    private void addRouteValidation(RouteRequestDto routeRequestDto, TransportationDto firstTransportationDto, TransportationDto secondTransportationDto, TransportationDto thirdTransportationDto, RouteValidationDto routeValidationDtoResponseDto) {
        RouteDto routeDto = createRoute(routeRequestDto);
        routeDto.addValidRoute(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType(), firstTransportationDto.getOperationDays());
        routeDto.addValidRoute(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType(), secondTransportationDto.getOperationDays());
        routeDto.addValidRoute(thirdTransportationDto.getOriginLocation(), thirdTransportationDto.getTransportationType(), thirdTransportationDto.getOperationDays());
        routeDto.addValidRoute(thirdTransportationDto.getDestinationLocation(), thirdTransportationDto.getTransportationType(), thirdTransportationDto.getOperationDays());
        routeValidationDtoResponseDto.addRouteIfValid(routeDto);

    }

    private RouteDto createRoute(RouteRequestDto routeRequestDto) {
        return new RouteDto(routeRequestDto.getDepartureDate(), routeRequestDto.getStartLocationId(), routeRequestDto.getEndLocationId(), flightTransferLimit, beforeFlightTransferLimit, afterFlightTransferLimit);
    }

}
