package com.example.thy.dto.response;

import com.example.thy.dto.RouteDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidRoutesResponseDto {

    List<RouteDto> validRoutes = new ArrayList<>();

    public void addRouteIfValid(RouteDto routeDto) {
        if(routeDto.isValid()){
            validRoutes.add(routeDto);
        }
    }
}
