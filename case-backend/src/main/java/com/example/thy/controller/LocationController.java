package com.example.thy.controller;

import com.example.thy.dto.LocationDto;
import com.example.thy.dto.request.SaveLocationRequestDto;
import com.example.thy.dto.request.UpdateLocationRequestDto;
import com.example.thy.dto.response.ErrorResponse;
import com.example.thy.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@Tag(name = "Location Controller", description = "Location APIs")
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Invalid Request",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server Error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
})
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/findAll")
    @Operation(summary = "Get all Locations", description = "Returns a list of location")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocationDto.class))))
    public ResponseEntity<Page<LocationDto>> findAllLocations(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(locationService.findAll(pageable));
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "find location by id", description = "Returns a location by id")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    public ResponseEntity<LocationDto> findLocationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(locationService.findById(id));
    }

    @PostMapping("/save")
    @Operation(summary = "save location", description = "Save Location")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    public ResponseEntity<LocationDto> SaveLocation(@Valid @RequestBody SaveLocationRequestDto saveLocationRequestDto) {
        return ResponseEntity.ok().body(locationService.save(saveLocationRequestDto));
    }

    @PutMapping("/update")
    @Operation(summary = "update location", description = "Update Location")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    public ResponseEntity<LocationDto> UpdateLocation(@RequestBody UpdateLocationRequestDto updateLocationRequestDto) {
        return ResponseEntity.ok().body(locationService.update(updateLocationRequestDto));
    }

    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "delete location by id", description = "delete a location by id")
    @ApiResponse(responseCode = "200", description = "Successful Request")
    public ResponseEntity<Void> deleteLocationById(@PathVariable Long id) {
        locationService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/search")
    @Operation(summary = "Search location", description = "Search Location")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LocationDto.class)))
    public ResponseEntity<Page<LocationDto>> SaveLocation(
            @RequestBody LocationDto locationDto,
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok().body(locationService.searchLocations(locationDto, pageable));
    }

}
