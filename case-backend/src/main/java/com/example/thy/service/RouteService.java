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
        List<TransportationDto> firstTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(routeRequestDto.getStartLocationId());

        firstTransportationDtoListByOriginLocation.forEach(firstTransportationDto -> {

            if (Objects.equals(firstTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId()) && firstTransportationDto.getTransportationType().equals(TransportationTypeEnum.FLIGHT)) {
                RouteDto routeDto = new RouteDto();
                routeDto.getValidRoutes().add(new RouteLocationDto(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType()));
                routeDto.getValidRoutes().add(new RouteLocationDto(firstTransportationDto.getOriginLocation()));
                validRoutes.add(routeDto);
            } else {
                List<TransportationDto> secondTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(firstTransportationDto.getDestinationLocation().getId());

                secondTransportationDtoListByOriginLocation.forEach(secondTransportationDto -> {
                    if (Objects.equals(secondTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                        RouteDto routeDto = new RouteDto();
                        routeDto.getValidRoutes().add(new RouteLocationDto(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType()));
                        routeDto.getValidRoutes().add(new RouteLocationDto(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType()));
                        routeDto.getValidRoutes().add(new RouteLocationDto(secondTransportationDto.getDestinationLocation()));
                        validRoutes.add(routeDto);
                    } else {
                        List<TransportationDto> thirdTransportationDtoListByOriginLocation = transportationService.findByOriginLocationId(secondTransportationDto.getDestinationLocation().getId());
                        thirdTransportationDtoListByOriginLocation.forEach(thirdTransportationDto -> {
                            if (Objects.equals(thirdTransportationDto.getDestinationLocation().getId(), routeRequestDto.getEndLocationId())) {
                                RouteDto routeDto = new RouteDto();
                                routeDto.getValidRoutes().add(new RouteLocationDto(firstTransportationDto.getOriginLocation(), firstTransportationDto.getTransportationType()));
                                routeDto.getValidRoutes().add(new RouteLocationDto(secondTransportationDto.getOriginLocation(), secondTransportationDto.getTransportationType()));
                                routeDto.getValidRoutes().add(new RouteLocationDto(thirdTransportationDto.getOriginLocation(), thirdTransportationDto.getTransportationType()));
                                routeDto.getValidRoutes().add(new RouteLocationDto(thirdTransportationDto.getDestinationLocation()));
                                validRoutes.add(routeDto);
                            }
                        });
                    }
                });
            }
        });
        return validRoutes;
    }
}
