package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.util.mapper.AsistenciaMapper;
import com.classqr.sistema.qr.repository.AsistenciaRepository;
import com.classqr.sistema.qr.service.ICreateAsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAsistenciaService implements ICreateAsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    private final AsistenciaMapper asistenciaMapper;

    @Override
    @Transactional
    public RespuestaGeneralDTO saveAsistencia(AsistenciaDTO asistenciaDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
        asistenciaRepository.save(asistenciaMapper.dtoToEntity(asistenciaDTO));
        respuestaGeneralDTO.setMessage("Se guardo correctamente");
        respuestaGeneralDTO.setStatus(HttpStatus.CREATED);
        return respuestaGeneralDTO;

    }
}
