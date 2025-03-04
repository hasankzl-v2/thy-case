package com.example.thy.controller;


import com.example.thy.dto.RouteDto;
import com.example.thy.dto.request.RouteRequestDto;
import com.example.thy.dto.response.ErrorResponse;
import com.example.thy.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
@Tag(name = "Route Controller", description = "Route API")
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Invalid Request",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server Error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
})
public class RouteController {

    private final RouteService routeService;
    @PostMapping("/findValidRoutes")
    @Operation(summary = "find valid routes", description = "Find all valid routes by given data")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = RouteDto.class))))
    public ResponseEntity<List<RouteDto>> findValidRoutes(@Valid @RequestBody RouteRequestDto routeRequestDto) {
        return ResponseEntity.ok().body(routeService.findAllValidRoutes(routeRequestDto).getValidRoutes());
    }

}
