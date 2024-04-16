package com.pragma.arquetipobootcamp2024.domain.api;

import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;

public interface IBootCampServicePort {
    void saveBootCamp(BootCamp bootcamp);
}
