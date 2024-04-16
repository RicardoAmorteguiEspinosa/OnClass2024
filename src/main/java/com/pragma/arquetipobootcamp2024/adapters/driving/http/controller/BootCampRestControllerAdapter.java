package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddBootCampRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IBootCampRequestMapper;
import com.pragma.arquetipobootcamp2024.domain.api.IBootCampServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
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
@RestController
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
public class BootCampRestControllerAdapter {

    private final IBootCampServicePort bootCampServicePort;
    private final IBootCampRequestMapper bootCampRequestMapper;

    @Operation(summary = "Add a new boot camp to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Boot camp added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/")
    public ResponseEntity<String> addBootCamp(@Valid @RequestBody AddBootCampRequest request) {
        bootCampServicePort.saveBootCamp(bootCampRequestMapper.addRequestToBootCamp(request));
        return ResponseEntity.status(HttpStatus.CREATED).body("The boot camp has been successfully recorded");
    }

 }
