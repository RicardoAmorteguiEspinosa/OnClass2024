package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.ElementAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.spi.ICapabilityPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CapabilityAdapter implements ICapabilityPersistencePort {
    private final ICapabilityRepository capabilityRepository;
    private final ICapabilityEntityMapper capabilityEntityMapper;

    @Override
    public void saveCapability(Capability capability) {
        if (capabilityRepository.findByName(capability.getName()).isPresent()) {
            throw new ElementAlreadyExistsException(capability.getName());
        }

        capabilityRepository.save(capabilityEntityMapper.toEntity(capability));
    }
}
