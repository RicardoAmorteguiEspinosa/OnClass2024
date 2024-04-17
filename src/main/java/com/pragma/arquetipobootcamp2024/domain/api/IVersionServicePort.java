package com.pragma.arquetipobootcamp2024.domain.api;

import com.pragma.arquetipobootcamp2024.domain.model.Version;

import java.util.List;

public interface IVersionServicePort {
    void saveVersion(Version version);

    List<Version> getAllVersions(Integer page, Integer size, String order);

    List<Version> getAllVersionsByBootCamp(Integer page, Integer size, String order, long id);
}
