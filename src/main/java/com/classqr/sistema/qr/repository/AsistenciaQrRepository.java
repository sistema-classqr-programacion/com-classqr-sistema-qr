/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link AsistenciaEntity}.
 * Proporciona métodos personalizados para verificar la existencia de asistencia y eliminar registros específicos.
 */
package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.AsistenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface AsistenciaQrRepository extends JpaRepository<AsistenciaEntity, String> {

    /**
     * Verifica si existe un registro de asistencia asociado a un estudiante específico.
     *
     * @param codigoEstudianteFk el código único del estudiante.
     * @return {@code true} si existe un registro de asistencia para el estudiante, de lo contrario {@code false}.
     */
    Boolean existsByCodigoEstudianteFk_CodigoEstudianteAndCodigoCursoFk_CodigoCurso(String codigoEstudianteFk, String codigoCurso);

    /**
     * Elimina un registro de asistencia basado en el profesor, estudiante, curso y fecha especificados.
     *
     * @param codigoEstudiante el código único del estudiante.
     * @param codigoProfesor el código único del profesor.
     * @param codigoCurso el código único del curso.
     * @param fechaAsistencia la fecha específica de la asistencia.
     */
    @Query("""
        DELETE FROM AsistenciaEntity as a where
        a.codigoProfesorFk.codigoProfesor = :codigoProfesor
        and a.codigoEstudianteFk.codigoEstudiante = :codigoEstudiante
        and a.codigoCursoFk.codigoCurso = :codigoCurso
        and a.fechaAsistencia = :fechaAsistencia
    """)
    @Transactional
    @Modifying
    void eliminarAsistencia(
            @Param("codigoEstudiante") String codigoEstudiante,
            @Param("codigoProfesor") String codigoProfesor,
            @Param("codigoCurso") String codigoCurso,
            @Param("fechaAsistencia") LocalDate fechaAsistencia
    );
}
