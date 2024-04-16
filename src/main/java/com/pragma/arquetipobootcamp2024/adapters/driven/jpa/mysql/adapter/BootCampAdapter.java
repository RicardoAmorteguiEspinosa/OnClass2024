package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BootCampEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.ElementAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IBootCampEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.IBootCampRepository;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import com.pragma.arquetipobootcamp2024.domain.spi.IBootCampPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class BootCampAdapter implements IBootCampPersistencePort{
    private final IBootCampRepository bootCampRepository;
    private final IBootCampEntityMapper bootCampEntityMapper;
    @Override
    public void saveBootCamp(BootCamp bootCamp) {
        if (bootCampRepository.findByName(bootCamp.getName()).isPresent()) {
            throw new ElementAlreadyExistsException(bootCamp.getName());
        }
        bootCampRepository.save(bootCampEntityMapper.toEntity(bootCamp));
    }

    @Override
    public List<BootCamp> getAllBootCamps(Integer page, Integer size, String order) {
        Pageable pagination;

        if (order.equalsIgnoreCase("QUANTITY")) {
            pagination = PageRequest.of(page, size);
        } else {
            Sort sortingOrder = order.equalsIgnoreCase("DESC") ? Sort.by("name").descending() : Sort.by("name").ascending();
            pagination = PageRequest.of(page, size, sortingOrder);
        }

        return getAllBootCampsOrdered(pagination);
    }

    private List<BootCamp> getAllBootCampsOrdered(Pageable pagination) {
        Page<BootCampEntity> bootCampPage;

        if (pagination.getSort().isSorted()) {
            bootCampPage = bootCampRepository.findAll(pagination);
        } else {
            bootCampPage = bootCampRepository.findAllOrderByBootCamps(pagination);
        }

        return bootCampEntityMapper.toModelList(bootCampPage.getContent());
    }

}
