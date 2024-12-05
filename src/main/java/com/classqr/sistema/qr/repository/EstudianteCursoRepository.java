/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link CursoEstudianteEntity}.
 * Proporciona métodos personalizados para verificar la relación entre estudiantes y cursos.
 */
package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.CursoEstudianteEntity;
import com.classqr.sistema.commons.entity.embeddable.CursoEstudianteIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteCursoRepository extends JpaRepository<CursoEstudianteEntity, CursoEstudianteIdEntity> {

    /**
     * Verifica si existe una relación entre un estudiante y un curso específicos.
     *
     * @param codigoEstudiante el código único del estudiante.
     * @param codigoCurso el código único del curso.
     * @return {@code true} si existe la relación entre el estudiante y el curso, de lo contrario {@code false}.
     */
    Boolean existsByCodigoEstudianteEntityFk_CodigoEstudianteAndCodigoCursoEntityFk_CodigoCurso(
            String codigoEstudiante,
            String codigoCurso
    );

}
