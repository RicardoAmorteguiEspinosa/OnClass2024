package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BootCampEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface IBootCampRepository extends JpaRepository<BootCampEntity, Long>{
    Optional<BootCampEntity> findByName(String name);

}
