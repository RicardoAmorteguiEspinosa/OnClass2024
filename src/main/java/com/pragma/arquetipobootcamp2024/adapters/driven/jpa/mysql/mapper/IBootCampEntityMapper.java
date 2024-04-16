package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BootCampEntity;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface IBootCampEntityMapper {
    @Mapping(source = "capabilitiesList", target = "capabilitiesList")
    BootCampEntity toEntity(BootCamp bootCamp);

    List<BootCamp> toModelList(List<BootCampEntity> bootCampEntities);
}
