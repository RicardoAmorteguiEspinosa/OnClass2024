package com.pragma.arquetipobootcamp2024.configuration;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter.BootCampAdapter;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter.CapabilityAdapter;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter.TechnologyAdapter;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter.VersionAdapter;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IBootCampEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IVersionEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.IBootCampRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.IVersionRepository;
import com.pragma.arquetipobootcamp2024.domain.api.ITechnologyServicePort;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.BootCampUseCase;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.CapabilityUseCase;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.TechnologyUseCase;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.VersionUseCase;
import com.pragma.arquetipobootcamp2024.domain.spi.IBootCampPersistencePort;
import com.pragma.arquetipobootcamp2024.domain.spi.ICapabilityPersistencePort;
import com.pragma.arquetipobootcamp2024.domain.spi.ITechnologyPersistencePort;
import com.pragma.arquetipobootcamp2024.domain.spi.IVersionPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;
    private final ICapabilityEntityMapper capabilityEntityMapper;
    private final ICapabilityRepository capabilityRepository;
    private final IBootCampRepository bootCampRepository;
    private final IBootCampEntityMapper bootCampEntityMapper;
    private final IVersionRepository versionRepository;
    private final IVersionEntityMapper versionEntityMapper;



    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyAdapter(technologyRepository, technologyEntityMapper);
    }

    @Bean
    public ITechnologyServicePort technologyServicePort() {
        return new TechnologyUseCase(technologyPersistencePort());
    }

    @Bean
    public ICapabilityPersistencePort capabilityPersistencePort() {
        return new CapabilityAdapter(capabilityRepository, capabilityEntityMapper);
    }

    @Bean
    public CapabilityUseCase capabilityUseCase() {
        return new CapabilityUseCase(capabilityPersistencePort());
    }

    @Bean
    public IBootCampPersistencePort bootCampPersistencePort() {
        return new BootCampAdapter(bootCampRepository, bootCampEntityMapper);
    }

    @Bean
    public BootCampUseCase bootCampServicePort() {
        return new BootCampUseCase(bootCampPersistencePort());
    }
    @Bean
    public IVersionPersistencePort versionPersistencePort() {
        return new VersionAdapter(versionRepository, versionEntityMapper);
    }

    @Bean
    public VersionUseCase versionServicePort() {
        return new VersionUseCase(versionPersistencePort());
    }

}
