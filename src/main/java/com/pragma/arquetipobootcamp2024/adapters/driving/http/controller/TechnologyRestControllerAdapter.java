package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddTechnologyRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/technology")
@RequiredArgsConstructor
public class TechnologyRestControllerAdapter {
    private final ITechnologyServicePort technologyServicePort;
    private final ITechnologyRequestMapper technologyRequestMapper;
    private final ITechnologyResponseMapper technologyResponseMapper;

    @Operation(summary = "Add a new technology to the system.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Technology added successfully",
                    content = @Content(schema = @Schema(implementation = Technology.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request format",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<String> addTechnology(@Valid @RequestBody AddTechnologyRequest request) {
        technologyServicePort.saveTechnology(technologyRequestMapper.addRequestToTechnology(request));
        return ResponseEntity.status(HttpStatus.CREATED).body("The technology has been successfully recorded");
    }

}

