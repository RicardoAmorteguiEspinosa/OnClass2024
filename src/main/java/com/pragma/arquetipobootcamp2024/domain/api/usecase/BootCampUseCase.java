package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.RepeatedItemsInTheListException;
import com.pragma.arquetipobootcamp2024.domain.api.IBootCampServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.spi.IBootCampPersistencePort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BootCampUseCase implements IBootCampServicePort {
    private final IBootCampPersistencePort bootCampPersistencePort;

    public BootCampUseCase(IBootCampPersistencePort bootCampPersistencePort) {
        this.bootCampPersistencePort = bootCampPersistencePort;
    }

    @Override
    public void saveBootCamp(BootCamp bootCamp) {
        List<Capability> capabilities = bootCamp.getCapabilitiesList();

        validateUniqueCapabilityIds(capabilities);

        this.bootCampPersistencePort.saveBootCamp(bootCamp);
    }

    private void validateUniqueCapabilityIds(List<Capability> capabilities) {
        Set<Long> capabilityIds = new HashSet<>();

        for (Capability capability : capabilities) {
            Long capId = capability.getId();
            if (!capabilityIds.add(capId)) {
                throw new RepeatedItemsInTheListException(capId.toString());
            }
        }
    }
}
