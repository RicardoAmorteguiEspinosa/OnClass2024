package com.pragma.arquetipobootcamp2024.domain.api;

import com.pragma.arquetipobootcamp2024.domain.model.Version;

public interface IVersionServicePort {
    void saveVersion(Version version);
}
