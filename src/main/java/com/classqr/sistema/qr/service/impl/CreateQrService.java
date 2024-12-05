package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.*;
import com.classqr.sistema.commons.entity.QrEntity;
import com.classqr.sistema.commons.service.impl.JwtService;
import com.classqr.sistema.commons.util.enums.CodigoUsuarioEnum;
import com.classqr.sistema.commons.util.helper.Utilidades;
import com.classqr.sistema.commons.util.mapper.QrMapper;
import com.classqr.sistema.qr.dto.GenerarQrDTO;
import com.classqr.sistema.qr.dto.QueryQrDTO;
import com.classqr.sistema.qr.repository.QrRepository;
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

    private final QrRepository qrRepository;

    private final QrMapper qrMapper;

    // Generar código QR y devolver como bytes (imagen en formato PNG)
    @Override
    @Transactional
    public GenerarQrDTO generateQRCode(int width, int height, QueryQrDTO queryQrDTO) throws IOException, WriterException {
        QrDTO qrDTO;
        GenerarQrDTO generarQrDTO = new GenerarQrDTO();
        QrEntity qrEntity = qrRepository.existenciaQrNoVencido(queryQrDTO.getCodigoProfesor(), queryQrDTO.getCodigoCurso());
        if(qrEntity != null){
            qrDTO = qrMapper.entityToDto(qrEntity);
            generarQrDTO.setImagen(Base64.getEncoder().encodeToString(qrDTO.getImagenQr()));
            generarQrDTO.setCodigo(qrDTO.getCodigoQr());
            return generarQrDTO;
        }
        qrDTO = new QrDTO();
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setCodigoCurso(queryQrDTO.getCodigoCurso());
        ProfesorDTO profesorDTO = new ProfesorDTO();
        profesorDTO.setCodigoProfesor(queryQrDTO.getCodigoProfesor());
        qrDTO.setCodigoQr(Utilidades.generarCodigo(CodigoUsuarioEnum.ASISTENCIA));
        qrDTO.setFechaCreacionQr(LocalDateTime.now());
        qrDTO.setFechaFinQr(LocalDateTime.now().plusHours(4));
        AuthResponseDTO authResponseDTO = jwtService.generarToken(new QrSeguridadDTO(qrDTO));
        String qrUrl = "http://localhost:4200/asistencia?token=" + authResponseDTO.getToken();
        BitMatrix bitMatrix = new MultiFormatWriter().encode(qrUrl, BarcodeFormat.QR_CODE, width, height);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        qrDTO.setImagenQr(pngOutputStream.toByteArray());
        qrDTO.setCursoFk(cursoDTO);
        qrDTO.setCodigoProfesorFk(profesorDTO);
        qrRepository.save(qrMapper.dtoToEntity(qrDTO));
        generarQrDTO.setImagen(Base64.getEncoder().encodeToString(pngOutputStream.toByteArray()));
        generarQrDTO.setCodigo(qrDTO.getCodigoQr());
        return generarQrDTO;
    }


}
