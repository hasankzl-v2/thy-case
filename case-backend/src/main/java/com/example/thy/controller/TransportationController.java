package com.example.thy.controller;

import com.example.thy.dto.request.TransportationDto;
import com.example.thy.service.TransportationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transportation")
@RequiredArgsConstructor
@Tag(name = "Transportation Controller", description = "Transportation APIs")
public class TransportationController {

    private final TransportationService transportationService;

    @GetMapping("/findAll")
    @Operation(summary = "Get all transportations", description = "Returns a list of transportation")
    public ResponseEntity<List<TransportationDto>> findAllTransportations(){
        return ResponseEntity.ok().body(transportationService.findAll());
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Find transportation by id", description = "Returns a transportation by id")
    public ResponseEntity<TransportationDto> findTransportationById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(transportationService.findById(id));
    }

    @PostMapping("/save")
    @Operation(summary = "save transportation", description = "save transportation")
    public ResponseEntity<TransportationDto> SaveTransportation(@Valid @RequestBody TransportationDto transportationDto)
    {
        return ResponseEntity.ok().body(transportationService.save(transportationDto));
    }

    @PutMapping("/update")
    @Operation(summary = "update transportation", description = "update transportation")
    public ResponseEntity<TransportationDto> updateTransportation( @RequestBody TransportationDto transportationDto)
    {
        return ResponseEntity.ok().body(transportationService.update(transportationDto));
    }
    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "delete transportation by id", description = "delete transportation by id")
    public ResponseEntity<Void> deleteTransportationById(@PathVariable Long id)
    {
        transportationService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
