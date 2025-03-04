package com.example.thy.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RouteValidationDto {

    @ArraySchema(
            schema = @Schema(implementation = RouteDto.class),
            arraySchema = @Schema(
                    description = "valid routes list"
            )
    )
    private final List<RouteDto> validRoutes = new ArrayList<>();

    // checks route before adding to list
    public void addRouteIfValid(RouteDto routeDto) {
        if (routeDto.isValid()) {
            routeDto.setId(validRoutes.size() + 1);
            validRoutes.add(routeDto);
        }
    }
}
