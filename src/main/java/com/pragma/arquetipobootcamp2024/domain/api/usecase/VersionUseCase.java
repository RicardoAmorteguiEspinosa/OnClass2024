package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.IllegalArgException;
import com.pragma.arquetipobootcamp2024.domain.api.IVersionServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Version;
import com.pragma.arquetipobootcamp2024.domain.spi.IVersionPersistencePort;

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

}
