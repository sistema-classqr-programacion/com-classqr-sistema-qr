/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link EstudianteEntity}.
 * Proporciona acceso a las operaciones CRUD para los registros de estudiantes.
 */
package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteQrRepository extends JpaRepository<EstudianteEntity, String> {
}
