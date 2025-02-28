package com.example.thy.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
            description = "name of the location",
            name = "name",
            type = "String",
            example = "Sabiha Gokcen Airport")
    @NotBlank(message = "Name should not be empty")
    private String name;
    @Schema(
            description = "country of the location",
            name = "country",
            type = "String",
            example = "Turkiye")
    @NotBlank(message = "Country should not be empty")
    private String country;
    @Schema(
            description = "city of the location",
            name = "city",
            type = "String",
            example = "Istanbul")
    @NotBlank(message = "City should not be empty")
    private String city;
    @Schema(
            description = "id of the location",
            name = "locationCode",
            type = "String",
            example = "SAW")
    @NotBlank(message = "Location code should not be empty")
    private String locationCode;

    public LocationDto(Long id) {
        this.id = id;
    }

    public LocationDto(){

    }
}
