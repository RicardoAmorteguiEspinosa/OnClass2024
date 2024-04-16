package com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddBootCampRequest;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBootCampRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "capabilitiesList", target = "capabilitiesList")
    BootCamp addRequestToBootCamp(AddBootCampRequest addBootCampRequest);

}
