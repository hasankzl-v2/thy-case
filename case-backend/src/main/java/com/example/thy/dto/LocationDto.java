package com.example.thy.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LocationDto {
    @Schema(
            description = "id of the location",
            name = "id",
            type = "Long",
            example = "1")
    private Long id;
    @Schema(
            description = "country of the location",
            name = "country",
            type = "String",
            example = "Turkiye")
    private String country;
    @Schema(
            description = "city of the location",
            name = "city",
            type = "String",
            example = "Istanbul")
    private String city;
    @Schema(
            description = "id of the location",
            name = "locationCode",
            type = "String",
            example = "SAW")
    private String locationCode;
}
