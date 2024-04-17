package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.VersionEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IVersionEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.IVersionRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Version;
import com.pragma.arquetipobootcamp2024.domain.spi.IVersionPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class VersionAdapter implements IVersionPersistencePort {
    private final IVersionRepository versionRepository;
    private final IVersionEntityMapper versionEntityMapper;

    @Override
    public void saveVersion(Version version) {
        versionRepository.save(versionEntityMapper.toEntity(version));
    }

    @Override
    public List<Version> getAllVersions(Integer page, Integer size, String order) {
        Pageable pagination = buildPageable(page, size, order);
        Page<VersionEntity> versionPage = versionRepository.findAll(pagination);
        List<VersionEntity> versions = versionPage.getContent();
        return versionEntityMapper.toModelList(versions);
    }

    @Override
    public List<Version> getAllVersionsByBootCamp(Integer page, Integer size, String order, long id) {
        Pageable pagination = buildPageable(page, size, order);
        Page<VersionEntity> versionPage = versionRepository.findByBootCampId(id, pagination);
        List<VersionEntity> versions = versionPage.getContent();
        return versionEntityMapper.toModelList(versions);
    }

    private Pageable buildPageable(Integer page, Integer size, String order) {
        String name = "bootCamp.name";
        Sort sort;
        switch (order.toLowerCase()) {
            case "quota":
                sort = Sort.by("quota");
                break;
            case "asc":
                sort = Sort.by(name).ascending();
                break;
            case "desc":
                sort = Sort.by(name).descending();
                break;
            case "startdate":
                sort = Sort.by("startDate");
                break;
            default:
                sort = Sort.by("endDate");
                break;
        }
        return PageRequest.of(page, size, sort);
    }
}
