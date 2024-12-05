/**
 * Controlador REST para gestionar las operaciones relacionadas con la asistencia de estudiantes.
 * Proporciona endpoints para guardar y eliminar registros de asistencia.
 */
package com.classqr.sistema.qr.controller;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.qr.service.ICreateAsistenciaService;
import com.classqr.sistema.qr.service.IEliminarAsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asistencia")
@RequiredArgsConstructor
public class AsistenciaContoller {

    /**
     * Servicio para gestionar la creación de registros de asistencia.
     */
    private final ICreateAsistenciaService iCreateAsistenciaService;

    /**
     * Servicio para gestionar la eliminación de registros de asistencia.
     */
    private final IEliminarAsistenciaService iEliminarAsistenciaService;

    /**
     * Endpoint para guardar un registro de asistencia de un estudiante.
     *
     * @param asistenciaDTO objeto {@link AsistenciaDTO} que contiene los datos de la asistencia a registrar.
     * @return un objeto {@link ResponseEntity} que contiene un {@link RespuestaGeneralDTO} con el resultado de la operación.
     */
    @PostMapping("/guardar")
    public ResponseEntity<RespuestaGeneralDTO> guardarAsistenciaEstudiante(@RequestBody AsistenciaDTO asistenciaDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = iCreateAsistenciaService.saveAsistencia(asistenciaDTO);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }

    /**
     * Endpoint para eliminar un registro de asistencia de un estudiante.
     *
     * @param asistenciaDTO objeto {@link AsistenciaDTO} que contiene los datos de la asistencia a eliminar.
     * @return un objeto {@link ResponseEntity} que contiene un {@link RespuestaGeneralDTO} con el resultado de la operación.
     */
    @PostMapping("/eliminar")
    public ResponseEntity<RespuestaGeneralDTO> eliminarAsistenciaEstudiante(@RequestBody AsistenciaDTO asistenciaDTO) {
        RespuestaGeneralDTO respuestaGeneralDTO = iEliminarAsistenciaService.eliminarAsistencia(asistenciaDTO);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }
}
