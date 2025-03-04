package com.example.thy.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateLocationRequestDto {

    @Schema(
            description = "id of the location",
            name = "id",
            type = "Long",
            example = "1")
    @NotNull(message = "id should not be null when updating")
    private Long id;

    @Schema(
            description = "name of the location",
            name = "name",
            type = "String",
            example = "Sabiha Gokcen Airport")
    private String name;
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
            description = "location code of the location",
            name = "locationCode",
            type = "String",
            example = "SAW")
    private String locationCode;
}
