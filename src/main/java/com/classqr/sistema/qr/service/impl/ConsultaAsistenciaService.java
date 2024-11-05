package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.EstudianteDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.entity.AsistenciaEntity;
import com.classqr.sistema.commons.repository.EstudianteRepository;
import com.classqr.sistema.commons.util.mapper.AsistenciaMapper;
import com.classqr.sistema.commons.util.mapper.EstudianteMapper;
import com.classqr.sistema.qr.repository.AsistenciaQrRepository;
import com.classqr.sistema.qr.service.IConsultaAsistenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ConsultaAsistenciaService implements IConsultaAsistenciaService {

    private final AsistenciaQrRepository asistenciaQrRepository;

    private final AsistenciaMapper asistenciaMapper;

    private final EstudianteRepository estudianteRepository;

    private final EstudianteMapper estudianteMapper;

    @Override
    public RespuestaGeneralDTO consultarTodasAsistencias() {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
        try{
            List<EstudianteDTO> estudiantes = estudianteMapper.listEntityTolistDto(estudianteRepository.findAll());
            for(EstudianteDTO estudianteDTO : estudiantes){
                estudianteDTO.setAsistio(asistenciaQrRepository.existsByCodigoEstudianteFk_CodigoEstudiante(estudianteDTO.getCodigoEstudiante()));
            }
            respuestaGeneralDTO.setMessage("Se consultaron correctamente las asistencias");
            respuestaGeneralDTO.setStatus(HttpStatus.OK);
            respuestaGeneralDTO.setData(estudiantes);
        }catch (Exception e){
            log.error("Error ", e);
            respuestaGeneralDTO.setMessage("Hubo un error en la consulta de las asistencias");
            respuestaGeneralDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return respuestaGeneralDTO;
    }
}
