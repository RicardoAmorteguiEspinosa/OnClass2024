package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Technology;

import java.util.List;

public interface ITechnologyPersistencePort {
    void saveTechnology(Technology technology);

    List<Technology> getAllTechnologies(Integer page, Integer size, boolean ascendingOrder);

}
