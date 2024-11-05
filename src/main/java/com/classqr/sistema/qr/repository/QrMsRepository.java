package com.classqr.sistema.qr.repository;

import com.classqr.sistema.commons.entity.QrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QrMsRepository extends JpaRepository<QrEntity, String> {

    @Query("""
        SELECT qrEntity from QrEntity qrEntity where qrEntity.fechaFinQr >= CURRENT_TIMESTAMP AND qrEntity.codigoQr = :codigoQr
    """)
    QrEntity existenciaQrNoVencido(@Param("codigoQr") String codigoQr);

}
