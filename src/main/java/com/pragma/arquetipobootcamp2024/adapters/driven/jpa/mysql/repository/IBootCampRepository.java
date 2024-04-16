package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BootCampEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface IBootCampRepository extends JpaRepository<BootCampEntity, Long>{
    Optional<BootCampEntity> findByName(String name);

    @Query("SELECT b FROM BootCampEntity b " +
            "LEFT JOIN b.capabilitiesList c " +
            "LEFT JOIN c.technologiesList t " +
            "GROUP BY b.id, b.name, b.description " +
            "ORDER BY COUNT(c) ASC")
    Page<BootCampEntity> findAllOrderByBootCamps(Pageable pagination);
}
