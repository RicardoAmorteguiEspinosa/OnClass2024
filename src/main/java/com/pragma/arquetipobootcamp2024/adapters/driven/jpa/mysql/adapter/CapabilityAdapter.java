package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.ElementAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.spi.ICapabilityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CapabilityAdapter implements ICapabilityPersistencePort {
    private final ICapabilityRepository capabilityRepository;
    private final ICapabilityEntityMapper capabilityEntityMapper;

    @Override
    public void saveCapability(Capability capability) {
        if (capabilityRepository.findByName(capability.getName()).isPresent()) {
            throw new ElementAlreadyExistsException(capability.getName());
        }

        capabilityRepository.save(capabilityEntityMapper.toEntity(capability));
    }

    @Override
    public List<Capability> getAllCapabilities(Integer page, Integer size, String order) {
        Pageable pagination;

        if (order.equalsIgnoreCase("quantity")) {
            pagination = PageRequest.of(page, size);
        } else {
            Sort sortingOrder = order.equalsIgnoreCase("desc") ? Sort.by("name").descending() : Sort.by("name").ascending();
            pagination = PageRequest.of(page, size, sortingOrder);
        }

        return getAllCapabilitiesOrdered(pagination);
    }

    private List<Capability> getAllCapabilitiesOrdered(Pageable pagination) {
        Page<CapabilityEntity> capabilityPage;

        if (pagination.getSort().isSorted()) {
            capabilityPage = capabilityRepository.findAll(pagination);
        } else {
            capabilityPage = capabilityRepository.findAllOrderByTechnologies(pagination);
        }

        return capabilityEntityMapper.toModelList(capabilityPage.getContent());
    }

}
