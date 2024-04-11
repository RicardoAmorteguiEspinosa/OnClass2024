package com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CapabilityResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ICapabilityResponseMapper {
    @Mapping(source = "technologiesList", target = "technologiesList")
    CapabilityResponse toCapabilityResponse(Capability capability);

    List<CapabilityResponse> toCapabilityResponseList(List<Capability> capability);
}
