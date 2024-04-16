package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddCapabilityRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CapabilityResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ICapabilityRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ICapabilityResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.ICapabilityServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
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
import java.util.List;


@RestController
@RequestMapping("/capability")
@RequiredArgsConstructor
public class CapabilityRestControllerAdapter {
    private final ICapabilityServicePort capabilityServicePort;
    private final ICapabilityRequestMapper capabilityRequestMapper;
    private final ICapabilityResponseMapper capabilityResponseMapper;

    @Operation(summary = "Add a new capability to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Capability added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<String> addCapability(@Valid @RequestBody AddCapabilityRequest request) {
        capabilityServicePort.saveCapability(capabilityRequestMapper.addRequestToCapability(request));
        return ResponseEntity.status(HttpStatus.CREATED).body("The technology has been successfully recorded");
    }

    @Operation(summary = "Get all capabilities with pagination ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Capabilities retrieved successfully",
            content = @Content(schema = @Schema(implementation = Capability.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @GetMapping("/")
    public ResponseEntity<List<CapabilityResponse>> getCapabilities(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "ASC") String order) {

        return ResponseEntity.ok(capabilityResponseMapper.toCapabilityResponseList(capabilityServicePort.getAllCapabilities(page, size, order)));
    }

}
