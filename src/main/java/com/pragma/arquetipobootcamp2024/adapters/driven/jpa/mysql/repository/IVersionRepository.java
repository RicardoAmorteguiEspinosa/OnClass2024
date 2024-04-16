package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.VersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVersionRepository extends JpaRepository<VersionEntity, Long> {
}
