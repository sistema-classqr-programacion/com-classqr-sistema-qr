package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.AsistenciaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciaQrRepository extends JpaRepository<AsistenciaEntity, String> {

    Boolean existsByCodigoEstudianteFk_CodigoEstudiante(String codigoEstudianteFk);

}
