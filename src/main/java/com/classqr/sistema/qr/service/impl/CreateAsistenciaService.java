package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.*;
import com.classqr.sistema.commons.util.enums.CodigoUsuarioEnum;
import com.classqr.sistema.commons.util.helper.Utilidades;
import com.classqr.sistema.commons.util.mapper.AsistenciaMapper;
import com.classqr.sistema.commons.util.mapper.CursoMapper;
import com.classqr.sistema.commons.util.mapper.QrMapper;
import com.classqr.sistema.qr.repository.AsistenciaQrRepository;
import com.classqr.sistema.qr.repository.EstudianteCursoRepository;
import com.classqr.sistema.qr.repository.EstudianteQrRepository;
import com.classqr.sistema.qr.repository.QrRepository;
import com.classqr.sistema.qr.service.ICreateAsistenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreateAsistenciaService implements ICreateAsistenciaService {

    private final AsistenciaQrRepository asistenciaQrRepository;
    private final @Qualifier("qr") QrRepository  qrRepository;
    private final QrMapper qrMapper;
    private final AsistenciaMapper asistenciaMapper;
    private final EstudianteCursoRepository estudianteCursoRepository;

    @Override
    @Transactional
    public RespuestaGeneralDTO saveAsistencia(AsistenciaDTO asistenciaDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
        try{
            QrDTO qrDto = qrMapper.entityToDto(qrRepository.findByCodigoQr(asistenciaDTO.getCodigoQrFk().getCodigoQr()));
            if(!estudianteCursoRepository.existsByCodigoEstudianteEntityFk_CodigoEstudianteAndCodigoCursoEntityFk_CodigoCurso(asistenciaDTO.getCodigoEstudianteFk().getCodigoEstudiante(), qrDto.getCursoFk().getCodigoCurso())){
                respuestaGeneralDTO.setStatus(HttpStatus.BAD_REQUEST);
                respuestaGeneralDTO.setMessage("El estudiante no es de este curso");
                return respuestaGeneralDTO;
            }
            if(asistenciaQrRepository.existsByCodigoEstudianteFk_CodigoEstudiante(asistenciaDTO.getCodigoEstudianteFk().getCodigoEstudiante())){
                respuestaGeneralDTO.setStatus(HttpStatus.BAD_REQUEST);
                respuestaGeneralDTO.setMessage("Ya se realizo la asistencia con este estudiante");
                return respuestaGeneralDTO;
            }
            if(asistenciaDTO.getCodigoAsistencia() == null){
                asistenciaDTO.setCodigoAsistencia(Utilidades.generarCodigo(CodigoUsuarioEnum.ASISTENCIA));
            }
            asistenciaDTO.setCodigoProfesorFk(qrDto.getCodigoProfesorFk());
            asistenciaDTO.setCodigoCursoFk(qrDto.getCursoFk());
            asistenciaQrRepository.save(asistenciaMapper.dtoToEntity(asistenciaDTO));
            respuestaGeneralDTO.setMessage("Se guardo correctamente");
            respuestaGeneralDTO.setStatus(HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Error ", e);
            respuestaGeneralDTO.setMessage("Hubo un error en registrar la asistencia");
            respuestaGeneralDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respuestaGeneralDTO;
    }
}
