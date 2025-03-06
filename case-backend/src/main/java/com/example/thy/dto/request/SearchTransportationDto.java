package com.example.thy.dto.request;

import com.example.thy.enums.TransportationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchTransportationDto {
    @Schema(
            description = "id of the transportation",
            name = "id",
            type = "Long",
            example = "1")
    private Long id;
    @Schema(
            description = "transportation type of the transportation",
            name = "transportationType",
            type = "TransportationTypeEnum",
            example = "BUS")
    private TransportationTypeEnum transportationType;
    @Schema(
            description = "destination location code of the transportation",
            name = "destinationLocationCode",
            type = "Long",
            example = "SAW")
        private String destinationLocationCode;
    @Schema(
            description = "origin location code of the transportation",
            name = "originLocationCode",
            type = "String",
            example = "SAW")
    private String originLocationCode;

    @Schema(
            description = "allowed operation days for transportation",
            name = "operationDays",
            type = "array",
            example = "[1,2,3,4]")
    private Integer[] operationDays;
}
