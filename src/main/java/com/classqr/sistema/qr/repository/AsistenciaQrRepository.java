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

    Boolean existsByCodigoEstudianteFk_CodigoEstudiante(String codigoEstudianteFk);

    @Query("""
        DELETE FROM AsistenciaEntity as a where
        a.codigoProfesorFk.codigoProfesor = :codigoProfesor
        and a.codigoEstudianteFk.codigoEstudiante = :codigoEstudiante
        and a.codigoCursoFk.codigoCurso = :codigoCurso
        and a.fechaAsistencia = :fechaAsistencia
    """)
    @Transactional
    @Modifying
    void eliminarAsistencia(@Param("codigoEstudiante") String codigoEstudiante,
                               @Param("codigoProfesor") String codigoProfesor,
                               @Param("codigoCurso") String codigoCurso,
                               @Param("fechaAsistencia")LocalDate fechaAsistencia);
}
