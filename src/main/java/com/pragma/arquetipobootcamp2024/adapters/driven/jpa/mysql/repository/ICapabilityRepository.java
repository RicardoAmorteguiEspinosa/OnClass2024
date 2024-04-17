package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICapabilityRepository extends JpaRepository<CapabilityEntity, Long> {
    Optional<CapabilityEntity> findByName(String name);

    @Query("SELECT c FROM CapabilityEntity c LEFT JOIN c.technologiesList t GROUP BY c " +
            "ORDER BY COUNT(t) ASC")
    Page<CapabilityEntity> findAllOrderByTechnologies(Pageable pagination);

}
