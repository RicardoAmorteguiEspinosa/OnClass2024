package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.api.ITechnologyServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;
import com.pragma.arquetipobootcamp2024.domain.spi.ITechnologyPersistencePort;


public class TechnologyUseCase implements ITechnologyServicePort {
    private final ITechnologyPersistencePort productPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void saveTechnology(Technology technology) {
        productPersistencePort.saveTechnology(technology);
    }

}
