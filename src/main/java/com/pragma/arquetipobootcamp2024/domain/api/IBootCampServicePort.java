package com.pragma.arquetipobootcamp2024.domain.api;

import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;

import java.util.List;

public interface IBootCampServicePort {
    void saveBootCamp(BootCamp bootcamp);

    List<BootCamp> getAllBootCamps(Integer page, Integer size, String order);

}
