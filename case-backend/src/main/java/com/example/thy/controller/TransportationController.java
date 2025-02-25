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
    public ResponseEntity<List<TransportationDto>> findAllTransportations(){
        return ResponseEntity.ok().body(transportationService.findAll());
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TransportationDto> findTransportationById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(transportationService.findById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<TransportationDto> SaveTransportation(@RequestBody TransportationDto transportationDto)
    {
        return ResponseEntity.ok().body(transportationService.save(transportationDto));
    }

    @PutMapping("/update")
    public ResponseEntity<TransportationDto> updateTransportation(@RequestBody TransportationDto transportationDto)
    {
        return ResponseEntity.ok().body(transportationService.update(transportationDto));
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteTransportationById(@PathVariable Long id)
    {
        transportationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
