package com.example.thy.controller;

import com.example.thy.dto.LocationDto;
import com.example.thy.entity.Location;
import com.example.thy.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@Tag(name = "Location Controller", description = "Location APIs")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @GetMapping("/findAll")
    @Operation(summary = "Get all Locations", description = "Returns a list of location")
    public ResponseEntity<List<LocationDto>> findAllLocations(){
        return ResponseEntity.ok().body(locationService.findAll());
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "find location by id", description = "Returns a location by id")
    public ResponseEntity<LocationDto> findLocationById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(locationService.findById(id));
    }

    @PostMapping("/save")
    @Operation(summary = "save location", description = "Save Location")
    public ResponseEntity<LocationDto> SaveLocation(@RequestBody LocationDto location)
    {
        return ResponseEntity.ok().body(locationService.save(location));
    }

    @PutMapping("/update")
    @Operation(summary = "update location", description = "Update Location")
    public ResponseEntity<LocationDto> UpdateLocation(@RequestBody LocationDto location)
    {
        return ResponseEntity.ok().body(locationService.update(location));
    }
    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "delete location by id", description = "delete a location by id")
    public ResponseEntity<Void> deleteLocationById(@PathVariable Long id)
    {
        locationService.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
