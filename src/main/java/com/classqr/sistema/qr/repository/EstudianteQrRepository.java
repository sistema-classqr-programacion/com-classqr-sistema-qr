package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteQrRepository extends JpaRepository<EstudianteEntity, String> {
}
