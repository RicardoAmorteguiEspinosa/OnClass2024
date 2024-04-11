package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.ElementAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;
import com.pragma.arquetipobootcamp2024.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TechnologyAdapter implements ITechnologyPersistencePort {
    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Override
    public void saveTechnology(Technology technology) {
        if (technologyRepository.findByName(technology.getName()).isPresent()) {
            throw new ElementAlreadyExistsException(technology.getName());
        }

        technologyRepository.save(technologyEntityMapper.toEntity(technology));
    }

}
