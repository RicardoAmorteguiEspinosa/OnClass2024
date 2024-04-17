package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.IllegalArgException;
import com.pragma.arquetipobootcamp2024.domain.api.IVersionServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Version;
import com.pragma.arquetipobootcamp2024.domain.spi.IVersionPersistencePort;

import java.util.List;

public class VersionUseCase implements IVersionServicePort {
    private final IVersionPersistencePort versionPersistencePort;

    public VersionUseCase(IVersionPersistencePort versionPersistencePort) {
        this.versionPersistencePort = versionPersistencePort;
    }

    @Override
    public void saveVersion(Version version) {
        if (version.getEndDate().isBefore(version.getStartDate())) {
            throw new IllegalArgException("The end date must be after the start date.");
        }
        versionPersistencePort.saveVersion(version);
    }

    @Override
    public List<Version> getAllVersions(Integer page, Integer size, String order) {
        return versionPersistencePort.getAllVersions(page, size, order);
    }

    @Override
    public List<Version> getAllVersionsByBootCamp(Integer page, Integer size, String order, long id) {
        return versionPersistencePort.getAllVersionsByBootCamp(page, size, order, id);
    }
}
