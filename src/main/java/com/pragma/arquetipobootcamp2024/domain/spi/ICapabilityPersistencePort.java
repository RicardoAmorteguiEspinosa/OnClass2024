package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Capability;

public interface ICapabilityPersistencePort {
    void saveCapability(Capability capability);

}
