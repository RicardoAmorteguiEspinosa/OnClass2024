package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.api.ITechnologyServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;
import com.pragma.arquetipobootcamp2024.domain.spi.ITechnologyPersistencePort;

import java.util.List;


public class TechnologyUseCase implements ITechnologyServicePort {
    private final ITechnologyPersistencePort productPersistencePort;

    public TechnologyUseCase(ITechnologyPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void saveTechnology(Technology technology) {
        productPersistencePort.saveTechnology(technology);
    }
    @Override
    public List<Technology> getAllTechnologies(Integer page, Integer size, boolean ascendingOrder) {
        return productPersistencePort.getAllTechnologies(page, size, ascendingOrder);
    }
}
