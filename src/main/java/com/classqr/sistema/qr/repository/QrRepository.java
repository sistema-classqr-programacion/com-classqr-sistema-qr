/**
 * Repositorio JPA para gestionar las operaciones relacionadas con la entidad {@link QrEntity}.
 * Proporciona métodos personalizados para consultar códigos QR activos y buscar por código.
 */
package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.QrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("qr")
public interface QrRepository extends JpaRepository<QrEntity, String> {

    /**
     * Consulta si existe un código QR activo (no vencido) para un profesor y curso específicos.
     * Un QR se considera activo si su fecha de vencimiento es mayor o igual al momento actual.
     *
     * @param codigoProfesorFk el código único del profesor asociado al QR.
     * @param codigoCursoFk el código único del curso asociado al QR.
     * @return un objeto {@link QrEntity} que representa el QR activo, o {@code null} si no existe.
     */
    @Query("""
        SELECT qrEntity FROM QrEntity qrEntity
        WHERE qrEntity.fechaFinQr >= CURRENT_TIMESTAMP
          AND qrEntity.codigoProfesorFk.codigoProfesor = :codigoProfesorFk
          AND qrEntity.cursoFk.codigoCurso = :codigoCursoFk
    """)
    QrEntity existenciaQrNoVencido(
            @Param("codigoProfesorFk") String codigoProfesorFk,
            @Param("codigoCursoFk") String codigoCursoFk
    );

    /**
     * Busca un código QR específico por su identificador único.
     *
     * @param codigoQr el identificador único del código QR.
     * @return un objeto {@link QrEntity} que representa el QR encontrado, o {@code null} si no existe.
     */
    QrEntity findByCodigoQr(String codigoQr);
}
