package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.AuthResponseDTO;
import com.classqr.sistema.commons.dto.QrDTO;
import com.classqr.sistema.commons.dto.QrSeguridadDTO;
import com.classqr.sistema.commons.entity.QrEntity;
import com.classqr.sistema.commons.repository.QrRepository;
import com.classqr.sistema.commons.service.impl.JwtService;
import com.classqr.sistema.commons.util.enums.CodigoUsuarioEnum;
import com.classqr.sistema.commons.util.helper.Utilidades;
import com.classqr.sistema.commons.util.mapper.QrMapper;
import com.classqr.sistema.qr.dto.GenerarQrDTO;
import com.classqr.sistema.qr.repository.QrMsRepository;
import com.classqr.sistema.qr.service.ICreateQrService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CreateQrService implements ICreateQrService {

    private final JwtService jwtService;

    private final QrMsRepository qrRepository;

    private final QrMapper qrMapper;

    // Generar código QR y devolver como bytes (imagen en formato PNG)
    @Override
    @Transactional
    public GenerarQrDTO generateQRCode(int width, int height, String codigoQr) throws IOException, WriterException {
        QrDTO qrDTO;
        QrEntity qrEntity = qrRepository.existenciaQrNoVencido(codigoQr);
        if(qrEntity == null){
            qrDTO = new QrDTO(Utilidades.generarCodigo(CodigoUsuarioEnum.ASISTENCIA), LocalDateTime.now(), LocalDateTime.now().plusDays(1));
            qrRepository.save(qrMapper.dtoToEntity(qrDTO));
        }else{
            qrDTO = qrMapper.entityToDto(qrEntity);
        }
        GenerarQrDTO generarQrDTO = new GenerarQrDTO();
        AuthResponseDTO authResponseDTO = jwtService.generarToken(new QrSeguridadDTO(qrDTO));
        String qrUrl = "http://localhost:4200/asistencia?token=" + authResponseDTO.getToken();
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        generarQrDTO.setImagen(Base64.getEncoder().encodeToString(pngOutputStream.toByteArray()));
        generarQrDTO.setCodigo(qrDTO.getCodigoQr());
        return generarQrDTO;
    }


}
