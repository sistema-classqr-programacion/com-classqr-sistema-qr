/**
 * Controlador REST para gestionar las operaciones relacionadas con códigos QR.
 * Proporciona endpoints para generar códigos QR y validar autenticaciones a través de ellos.
 */
package com.classqr.sistema.qr.controller;

import com.classqr.sistema.commons.dto.QrSeguridadDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.service.IJwtService;
import com.classqr.sistema.qr.dto.AutenticationDTO;
import com.classqr.sistema.qr.dto.GenerarQrDTO;
import com.classqr.sistema.qr.dto.QueryQrDTO;
import com.classqr.sistema.qr.service.ICreateQrService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/qr")
@Log4j2
@RequiredArgsConstructor
public class QrController {

    /**
     * Servicio para gestionar la creación de códigos QR.
     */
    private final ICreateQrService iCreateQrService;

    /**
     * Servicio para manejar y validar tokens JWT.
     */
    private final IJwtService iJwtService;

    /**
     * Endpoint para generar un código QR relacionado con la asistencia.
     *
     * @param qrDTO objeto {@link QueryQrDTO} que contiene los datos necesarios para generar el QR.
     * @return un objeto {@link ResponseEntity} que contiene un {@link GenerarQrDTO} con la imagen del código QR
     *         generado o un código de error en caso de fallo.
     */
    @PostMapping("/asistencia")
    public ResponseEntity<GenerarQrDTO> getQrAsistencia(@RequestBody QueryQrDTO qrDTO) {
        try {
            // Generar código QR
            GenerarQrDTO qrImage = iCreateQrService.generateQRCode(300, 300, qrDTO);
            return new ResponseEntity<>(qrImage, HttpStatus.OK);
        } catch (IOException | WriterException e) {
            log.error("Error al generar el código QR: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para validar la autenticación basada en un código QR y un token.
     *
     * @param autenticationDTO objeto {@link AutenticationDTO} que contiene el token y los datos del QR.
     * @return un objeto {@link ResponseEntity} que contiene un {@link RespuestaGeneralDTO} con el resultado de la
     *         validación o un código de error en caso de fallo.
     */
    @PostMapping("/autenticacion")
    public ResponseEntity<RespuestaGeneralDTO> postAutenticacion(@RequestBody AutenticationDTO autenticationDTO) {
        try {
            RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
            respuestaGeneralDTO.setStatus(HttpStatus.OK);
            respuestaGeneralDTO.setData(iJwtService.isTokenValid(autenticationDTO.getToken(), new QrSeguridadDTO(autenticationDTO.getQrDTO())));
            respuestaGeneralDTO.setMessage("Se validó correctamente");
            return ResponseEntity.status(HttpStatus.OK.value()).body(respuestaGeneralDTO);
        } catch (Exception e) {
            log.error("Error al validar autenticación: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
