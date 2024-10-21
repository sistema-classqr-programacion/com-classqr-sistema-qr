package com.classqr.sistema.qr.service;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;

public interface ICreateAsistenciaService {

    RespuestaGeneralDTO saveAsistencia(AsistenciaDTO asistenciaDTO);

}
