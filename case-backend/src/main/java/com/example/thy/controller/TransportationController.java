package com.example.thy.controller;

import com.example.thy.dto.LocationDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.entity.Transportation;
import com.example.thy.repository.TransportationRepository;
import com.example.thy.service.TransportationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportation")
@RequiredArgsConstructor
public class TransportationController {

    private final TransportationService transportationService;

    @GetMapping("/findAll")
    public ResponseEntity<List<TransportationDto>> findAllLocations(){
        return ResponseEntity.ok().body(transportationService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TransportationDto> findLocationById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(transportationService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<TransportationDto> SaveLocation(@RequestBody TransportationDto transportationDto)
    {
        return ResponseEntity.ok().body(transportationService.save(transportationDto));
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteLocationById(@PathVariable Long id)
    {
        transportationService.deleteById(id);
        return ResponseEntity.ok().body("Deleted Successfully");
    }
}
