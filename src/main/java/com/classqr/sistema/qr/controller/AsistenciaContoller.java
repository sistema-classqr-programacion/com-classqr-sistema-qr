package com.classqr.sistema.qr.controller;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.qr.service.IConsultaAsistenciaService;
import com.classqr.sistema.qr.service.ICreateAsistenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asistencia")
@RequiredArgsConstructor
public class AsistenciaContoller {

    private final ICreateAsistenciaService iCreateAsistenciaService;

    private final IConsultaAsistenciaService iConsultaAsistenciaService;

    @PostMapping("/guardar")
    public ResponseEntity<RespuestaGeneralDTO> guardarAsistenciaEstudiante(@RequestBody AsistenciaDTO asistenciaDTO){
        RespuestaGeneralDTO respuestaGeneralDTO = iCreateAsistenciaService.saveAsistencia(asistenciaDTO);
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }

    @GetMapping("/buscar-todas")
    public ResponseEntity<RespuestaGeneralDTO> obtenerAsistencias(){
        RespuestaGeneralDTO respuestaGeneralDTO = iConsultaAsistenciaService.consultarTodasAsistencias();
        return ResponseEntity.status(respuestaGeneralDTO.getStatus()).body(respuestaGeneralDTO);
    }

}
