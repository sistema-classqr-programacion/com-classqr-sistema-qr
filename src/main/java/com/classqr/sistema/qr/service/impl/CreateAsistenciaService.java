package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.util.mapper.AsistenciaMapper;
import com.classqr.sistema.qr.repository.AsistenciaQrRepository;
import com.classqr.sistema.qr.service.ICreateAsistenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Log4j2
public class CreateAsistenciaService implements ICreateAsistenciaService {

    private final AsistenciaQrRepository asistenciaQrRepository;

    private final AsistenciaMapper asistenciaMapper;
    private final SecurityProperties securityProperties;

    @Override
    @Transactional
    public RespuestaGeneralDTO saveAsistencia(AsistenciaDTO asistenciaDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
        try{
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
