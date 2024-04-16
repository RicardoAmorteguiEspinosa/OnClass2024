package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;

import java.util.List;

public interface IBootCampPersistencePort {
    void saveBootCamp(BootCamp bootCamp);
    List<BootCamp> getAllBootCamps(Integer page, Integer size, String order);
}
