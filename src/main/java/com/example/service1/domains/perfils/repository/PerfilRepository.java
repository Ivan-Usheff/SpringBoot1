package com.example.service1.domains.perfils.repository;

import com.example.service1.domains.perfils.entities.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<PerfilEntity, Long> {
}
