package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.ElementAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IBootCampEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.IBootCampRepository;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import com.pragma.arquetipobootcamp2024.domain.spi.IBootCampPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BootCampAdapter implements IBootCampPersistencePort{
    private final IBootCampRepository bootCampRepository;
    private final IBootCampEntityMapper bootCampEntityMapper;
    @Override
    public void saveBootCamp(BootCamp bootCamp) {
        if (bootCampRepository.findByName(bootCamp.getName()).isPresent()) {
            throw new ElementAlreadyExistsException(bootCamp.getName());
        }
        bootCampRepository.save(bootCampEntityMapper.toEntity(bootCamp));
    }

}
