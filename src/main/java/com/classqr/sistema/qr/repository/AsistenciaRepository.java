package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.AsistenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciaRepository extends JpaRepository<AsistenciaEntity, String> {
}
