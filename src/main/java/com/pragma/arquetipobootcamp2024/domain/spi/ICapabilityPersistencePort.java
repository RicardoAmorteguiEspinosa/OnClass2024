package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Capability;

import java.util.List;

public interface ICapabilityPersistencePort {
    void saveCapability(Capability capability);

    List<Capability> getAllCapabilities(Integer page, Integer size, String order);
}
