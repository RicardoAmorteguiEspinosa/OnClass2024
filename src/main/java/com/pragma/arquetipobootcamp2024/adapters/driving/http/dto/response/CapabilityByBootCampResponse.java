package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CapabilityByBootCampResponse {
    private final Long id;
    private final String name;
    private List<TechnologyByCapabilityResponse> technologiesList;
}
