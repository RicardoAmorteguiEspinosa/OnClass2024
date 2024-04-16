package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import com.pragma.arquetipobootcamp2024.domain.util.DomainConstants;

import javax.validation.constraints.NotBlank;

public record AddCapabilityByBootCampRequest(
        @NotBlank(message = DomainConstants.FIELD_NAME_NULL_MESSAGE)
        Long id) {
}
