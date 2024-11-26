package com.classqr.sistema.qr.service;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;

public interface IEliminarAsistenciaService {

    RespuestaGeneralDTO eliminarAsistencia(AsistenciaDTO asistenciaDTO);

}
