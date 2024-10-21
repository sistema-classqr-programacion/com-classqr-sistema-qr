package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.AsistenciaSeguridadDTO;
import com.classqr.sistema.commons.dto.AuthResponseDTO;
import com.classqr.sistema.commons.service.impl.JwtService;
import com.classqr.sistema.commons.util.enums.CodigoUsuarioEnum;
import com.classqr.sistema.commons.util.helper.Utilidades;
import com.classqr.sistema.commons.util.mapper.AsistenciaMapper;
import com.classqr.sistema.qr.repository.AsistenciaRepository;
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

@Service
@RequiredArgsConstructor
public class CreateQrService implements ICreateQrService {

    private final JwtService jwtService;

    private final AsistenciaRepository asistenciaRepository;

    private final AsistenciaMapper asistenciaMapper;

    // Generar código QR y devolver como bytes (imagen en formato PNG)
    @Override
    @Transactional
    public byte[] generateQRCode(int width, int height) throws IOException, WriterException {
        AsistenciaDTO asistenciaDTO = new AsistenciaDTO();
        asistenciaDTO.setCodigoAsistencia(Utilidades.generarCodigo(CodigoUsuarioEnum.ASISTENCIA));
        asistenciaRepository.save(asistenciaMapper.dtoToEntity(asistenciaDTO));
        AuthResponseDTO authResponseDTO = jwtService.generarToken(new AsistenciaSeguridadDTO(asistenciaDTO));
        String qrUrl = "http://localhost:4200/asistencia?token=" + authResponseDTO.getToken();
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }


}
