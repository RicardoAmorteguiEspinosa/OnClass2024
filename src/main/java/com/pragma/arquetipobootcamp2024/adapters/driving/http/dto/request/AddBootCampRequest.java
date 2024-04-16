package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record AddBootCampRequest(
        Long id,
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,
        @NotBlank(message = "Description cannot be blank")
        @Size(min = 2, max = 90, message = "Description must be between 2 and 90 characters")
        String description,
        @NotNull(message = "Capabilities list cannot be null")
        @Size(min = 1, max = 4, message = "Capabilities list must contain between 1 and 4 elements")
        List<AddCapabilityByBootCampRequest> capabilitiesList) {
}

