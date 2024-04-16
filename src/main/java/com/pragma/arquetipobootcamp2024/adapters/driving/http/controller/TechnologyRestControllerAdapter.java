package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.ITechnologyServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/technology")
@RequiredArgsConstructor
public class TechnologyRestControllerAdapter {
    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;
    private final ITechnologyResponseMapper technologyResponseMapper;

    @Operation(summary = "Add a new technology to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Technology added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping("/")
    public ResponseEntity<String> addTechnology(@Valid @RequestBody AddTechnologyRequest request) {
        technologyServicePort.saveTechnology(technologyRequestMapper.addRequestToTechnology(request));
        return ResponseEntity.status(HttpStatus.CREATED).body("The technology has been successfully recorded");
    }

    @Operation(summary = "Get all technologies with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Technologies retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Technology.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping("/")
    public ResponseEntity<List<TechnologyResponse>> getAllTechnologies(
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
            @Min(value = 1) @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "true") boolean ascendingOrder) {
        return ResponseEntity.ok(technologyResponseMapper.
                toTechnologyResponseList(technologyServicePort.getAllTechnologies(page, size, ascendingOrder)));
    }
}

