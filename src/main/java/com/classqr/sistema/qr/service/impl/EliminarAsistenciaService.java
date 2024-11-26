package com.classqr.sistema.qr.service.impl;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.util.enums.CodigoUsuarioEnum;
import com.classqr.sistema.commons.util.helper.Utilidades;
import com.classqr.sistema.commons.util.mapper.AsistenciaMapper;
import com.classqr.sistema.qr.repository.AsistenciaQrRepository;
import com.classqr.sistema.qr.service.IEliminarAsistenciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EliminarAsistenciaService implements IEliminarAsistenciaService {

    private final AsistenciaQrRepository asistenciaQrRepository;

    private final AsistenciaMapper asistenciaMapper;

    @Override
    @Transactional
    public RespuestaGeneralDTO eliminarAsistencia(AsistenciaDTO asistenciaDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
        try{
            asistenciaQrRepository.eliminarAsistencia(asistenciaDTO.getCodigoEstudianteFk().getCodigoEstudiante(),
                    asistenciaDTO.getCodigoProfesorFk().getCodigoProfesor(),
                    asistenciaDTO.getCodigoCursoFk().getCodigoCurso(),
                    asistenciaDTO.getFechaAsistencia());
            respuestaGeneralDTO.setMessage("Se elimino correctamente");
            respuestaGeneralDTO.setStatus(HttpStatus.OK);
        }catch (Exception e){
            log.error("Error ", e);
            respuestaGeneralDTO.setMessage("Hubo un error en eliminar la asistencia");
            respuestaGeneralDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return respuestaGeneralDTO;
    }
}
