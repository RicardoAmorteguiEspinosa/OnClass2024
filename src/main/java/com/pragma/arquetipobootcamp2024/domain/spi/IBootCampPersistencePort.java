package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;

public interface IBootCampPersistencePort {
    void saveBootCamp(BootCamp bootCamp);

}
