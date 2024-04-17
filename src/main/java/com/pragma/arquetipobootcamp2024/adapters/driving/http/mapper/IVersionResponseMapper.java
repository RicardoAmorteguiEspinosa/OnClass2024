package com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.VersionResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Version;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVersionResponseMapper {
    VersionResponse toVersionResponse(Version version);

    List<VersionResponse> toVersionResponseList(List<Version> versions);
}
