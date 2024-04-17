package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.RepeatedItemsInTheListException;
import com.pragma.arquetipobootcamp2024.domain.api.ICapabilityServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;
import com.pragma.arquetipobootcamp2024.domain.spi.ICapabilityPersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CapabilityUseCase implements ICapabilityServicePort {

    private final ICapabilityPersistencePort capabilityPersistencePort;

    public CapabilityUseCase(ICapabilityPersistencePort capabilityPersistencePort) {
        this.capabilityPersistencePort = capabilityPersistencePort;
    }

    @Override
    public void saveCapability(Capability capability) {
        List<Technology> technologies = capability.getTechnologiesList();

        validateUniqueTechnologyIds(technologies);

        this.capabilityPersistencePort.saveCapability(capability);
    }

    @Override
    public List<Capability> getAllCapabilities(Integer page, Integer size, String order) {
        return this.capabilityPersistencePort.getAllCapabilities(page, size, order);
    }

    private void validateUniqueTechnologyIds(List<Technology> technologies) {
        Set<Long> technologyIds = new HashSet<>();

        for (Technology technology : technologies) {
            Long techId = technology.getId();
            if (!technologyIds.add(techId)) {
                throw new RepeatedItemsInTheListException(techId.toString());
            }
        }
    }


}
