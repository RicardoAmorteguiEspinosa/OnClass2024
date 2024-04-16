package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IVersionRequestMapper;
import com.pragma.arquetipobootcamp2024.domain.api.IVersionServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
public class VersionRestControllerAdapter {
    private final IVersionServicePort versionServicePort;
    private final IVersionRequestMapper versionRequestMapper;
    @Operation(summary = "Add a new Version the BootCamp to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Version de Boot camp added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<String> addVersion(@Valid @RequestBody AddVersionRequest request) {
        versionServicePort.saveVersion(versionRequestMapper.addRequestToVersion(request));
        return ResponseEntity.status(HttpStatus.CREATED).body("The boot camp has been successfully recorded");
    }

}
