package com.example.thy.controller;


import com.example.thy.dto.request.RouteRequestDto;
import com.example.thy.dto.response.ValidRoutesResponseDto;
import com.example.thy.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
@Tag(name = "Route Controller", description = "Route API")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    @GetMapping("/findValidRoutes")
    @Operation(summary = "find valid routes", description = "Find all valid routes by given data")
    public ResponseEntity<ValidRoutesResponseDto> findValidRoutes(@Valid @RequestBody RouteRequestDto routeRequestDto) {
        return ResponseEntity.ok().body(routeService.findAllValidRoutes(routeRequestDto));
    }

}
