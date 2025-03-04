package com.example.thy.dto;

import com.example.thy.enums.TransferTypeEnum;
import com.example.thy.enums.TransportationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Getter
public class RouteDto {

    @Schema(
            description = "incremented value starts from 1",
            name = "id",
            type = "integer",
            example = "1")
    private int id;
    @Schema(
            description = "list of locations route depending on origin and destination locations",
            name = "validRoutes",
            type = "RouteLocationDto")
    private List<RouteLocationDto> validRoutes = new ArrayList<>();
    @Schema(
            description = "flight Transfer Count",
            name = "flightTransferCount",
            type = "integer",
            example = "1")
    private int flightTransferCount = 0;
    @Schema(
            description = "after Flight Transfer Count",
            name = "afterFlightTransferCount",
            type = "integer",
            example = "1")
    private int afterFlightTransferCount = 0;
    @Schema(
            description = "before Flight Transfer Count",
            name = "beforeFlightTransferCount",
            type = "integer",
            example = "1")
    private int beforeFlightTransferCount = 0;
    @Schema(
            description = "selected departure date",
            name = "departureDate",
            type = "LocalDate",
            example = "2025-03-03")
    private final LocalDate departureDate;
    @Schema(
            description = "departure day number (1-7)",
            name = "departureDay",
            type = "integer",
            example = "1")
    private final int departureDay;
    @Schema(
            description = "origin location id",
            name = "startLocationId",
            type = "integer",
            example = "1")
    private final Long startLocationId;
    @Schema(
            description = "destination location id",
            name = "endLocationId",
            type = "integer",
            example = "1")
    private final Long endLocationId;

    public void addValidRoute(LocationDto locationDto, TransportationTypeEnum transportationType, Integer[] operationDays) {
        /*do not need to calculate transferType if this location is the end location*/
        if(!locationDto.getId().equals(endLocationId)){
            TransferTypeEnum transferType = findTransferTypeForLocation(transportationType);
            RouteLocationDto routeLocationDto = new RouteLocationDto(locationDto, transportationType, transferType,operationDays);
            validRoutes.add(routeLocationDto);
            updateTransferTypeCounts(transferType);
        }
        else{
            RouteLocationDto routeLocationDto = new RouteLocationDto(locationDto);
            validRoutes.add(routeLocationDto);
        }
    }

    private TransferTypeEnum findTransferTypeForLocation(TransportationTypeEnum transportationType) {

        if (transportationType.equals(TransportationTypeEnum.FLIGHT)) {
            return TransferTypeEnum.FLIGHT;
        } else if (flightTransferCount > 0) {
            return TransferTypeEnum.AFTER_FLIGHT;
        } else {
            return TransferTypeEnum.BEFORE_FLIGHT;
        }
    }

    private void updateTransferTypeCounts(TransferTypeEnum transferType) {
        if (transferType.equals(TransferTypeEnum.FLIGHT)) {
            flightTransferCount++;
        } else if (transferType.equals(TransferTypeEnum.AFTER_FLIGHT)) {
            afterFlightTransferCount++;
        } else {
            beforeFlightTransferCount++;
        }

    }

    /*  A sequence of connected transportations cannot be considered as a valid route if:
    • There are more than 3 transportations from origin to destination.
    • There is no flight among them
    • There are more than one flights among them
    • There are more than one before flight transfers among them
    • There are more than one after flight transfers among them´
    * Departure date should be available for route
    */
    public boolean isValid() {

        if (flightTransferCount == 0 || flightTransferCount > 1 || validRoutes.size() > 4 || beforeFlightTransferCount > 1 || afterFlightTransferCount > 1) {
            return false;
        }

        for (int i=0 ; i<validRoutes.size()-1;i++) {
            RouteLocationDto routeLocationDto = validRoutes.get(i);
            if(Arrays.stream(routeLocationDto.getOperationDays()).noneMatch(day -> day.equals(departureDay))){
                return false;
            }
        }

        return true;
    }

    public RouteDto(LocalDate departureDate, Long startLocationId, Long endLocationId) {
        this.departureDate = departureDate;
        this.startLocationId = startLocationId;
        this.endLocationId = endLocationId;
        this.departureDay = departureDate.getDayOfWeek().getValue();
    }

    public void setId(int id) {
        this.id = id;
    }
}
