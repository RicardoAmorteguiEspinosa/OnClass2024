package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddVersionRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.VersionResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IVersionRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IVersionResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.IVersionServicePort;
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
@RequestMapping("/version")
@RequiredArgsConstructor
public class VersionRestControllerAdapter {
    private final IVersionServicePort versionServicePort;
    private final IVersionRequestMapper versionRequestMapper;
    private final IVersionResponseMapper versionResponseMapper;

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

    @Operation(summary = "Retrieve all versions with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Versions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = VersionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/")
    public ResponseEntity<List<VersionResponse>> getVersions(
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
            @Min(value = 1) @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "ASC") String order) {

        return ResponseEntity.ok(versionResponseMapper.toVersionResponseList(
                versionServicePort.getAllVersions(page, size, order)));
    }

    @Operation(summary = "Retrieve versions of a boot camp with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Versions retrieved successfully",
                    content = @Content(schema = @Schema(implementation = VersionResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search/")
    public ResponseEntity<List<VersionResponse>> getVersionsByBootCamp(
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
            @Min(value = 1) @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "ASC") String order,
            @RequestParam long id) {

        return ResponseEntity.ok(versionResponseMapper.toVersionResponseList(
                versionServicePort.getAllVersionsByBootCamp(page, size, order, id)));
    }

}
