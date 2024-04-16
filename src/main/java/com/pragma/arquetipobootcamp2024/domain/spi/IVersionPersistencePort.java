package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Version;

public interface IVersionPersistencePort {
    void saveVersion(Version version);
}
