package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.VersionEntity;
import com.pragma.arquetipobootcamp2024.domain.model.Version;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVersionEntityMapper {
    @Mapping(source = "bootCamp.id", target = "idBootCamp")
    Version toModel(VersionEntity versionEntity);
    @Mapping(source = "idBootCamp", target = "bootCamp.id")
    VersionEntity toEntity(Version version);
    List<Version> toModelList(List<VersionEntity> versionEntities);
}
