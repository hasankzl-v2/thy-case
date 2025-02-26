package com.example.thy.dto;

import com.example.thy.entity.Location;
import com.example.thy.enums.TransportationTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RouteDto {

    List<RouteLocationDto> validRoutes = new ArrayList<>();

}
