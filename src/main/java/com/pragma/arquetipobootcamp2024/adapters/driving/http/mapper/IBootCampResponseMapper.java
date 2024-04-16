package com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.BootCampResponse;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootCampResponseMapper {
    @Mapping(source = "capabilitiesList", target = "capabilitiesList")
    BootCampResponse toCapabilityResponse(BootCamp bootcamp);

    List<BootCampResponse> toBootCampResponseList(List<BootCamp> bootcamp);
}
