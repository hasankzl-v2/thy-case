package com.example.thy.controller;

import com.example.thy.dto.LocationDto;
import com.example.thy.entity.Location;
import com.example.thy.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @GetMapping("/findAll")
    public ResponseEntity<List<Location>> findAllLocations(){
        return ResponseEntity.ok().body(locationService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Location> findLocationById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(locationService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<LocationDto> SaveLocation(@RequestBody LocationDto location)
    {
        return ResponseEntity.ok().body(locationService.save(location));
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteLocationById(@PathVariable Long id)
    {
        locationService.deleteById(id);
        return ResponseEntity.ok().body("Deleted Successfully");
    }


}
