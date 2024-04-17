package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapabilityEntityMapper {
    @Mapping(source = "technologiesList", target = "technologiesList")
    Capability toModel(CapabilityEntity capabilityEntity);

    @Mapping(source = "technologiesList", target = "technologiesList")
    CapabilityEntity toEntity(Capability capability);

    List<Capability> toModelList(List<CapabilityEntity> capabilitiesEntities);
}
