package com.example.thy.controller;

import com.example.thy.dto.request.SaveTransportationRequestDto;
import com.example.thy.dto.TransportationDto;
import com.example.thy.dto.request.SearchTransportationDto;
import com.example.thy.dto.request.UpdateTransportationRequestDto;
import com.example.thy.dto.response.ErrorResponse;
import com.example.thy.service.TransportationService;
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
@RequestMapping("/api/transportation")
@RequiredArgsConstructor
@Tag(name = "Transportation Controller", description = "Transportation APIs")
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Invalid Request",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Server Error",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
})
public class TransportationController {

    private final TransportationService transportationService;

    @GetMapping("/findAll")
    @Operation(summary = "Get all transportations", description = "Returns a list of transportation")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TransportationDto.class))))
    public ResponseEntity<Page<TransportationDto>> findAllTransportations(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(transportationService.findAll(pageable));
    }

    @GetMapping("/findById/{id}")
    @Operation(summary = "Find transportation by id", description = "Returns a transportation by id")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransportationDto.class)))
    public ResponseEntity<TransportationDto> findTransportationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(transportationService.findById(id));
    }

    @PostMapping("/save")
    @Operation(summary = "save transportation", description = "save transportation")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransportationDto.class)))
    public ResponseEntity<TransportationDto> SaveTransportation(@Valid @RequestBody SaveTransportationRequestDto saveTransportationRequestDto) {
        return ResponseEntity.ok().body(transportationService.save(saveTransportationRequestDto));
    }

    @DeleteMapping("/deleteById/{id}")
    @Operation(summary = "delete transportation by id", description = "delete transportation by id")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json"))
    public ResponseEntity<Void> deleteTransportationById(@PathVariable Long id) {
        transportationService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/search")
    @Operation(summary = "Search Transportation", description = "Search Transportation")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransportationDto.class)))
    public ResponseEntity<Page<TransportationDto>> searchTransportation(
            @RequestBody SearchTransportationDto searchDto,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        return ResponseEntity.ok().body(transportationService.searchTransportations(searchDto, pageable));
    }


    @PutMapping("/update")
    @Operation(summary = "update transportation", description = "update transportation")
    @ApiResponse(responseCode = "200", description = "Successful Request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransportationDto.class)))
    public ResponseEntity<TransportationDto> updateTransportation(@Valid @RequestBody UpdateTransportationRequestDto updateTransportationRequestDto) {
        TransportationDto transportationDto = transportationService.update(updateTransportationRequestDto);
        return ResponseEntity.ok().body(transportationDto);
    }

}
