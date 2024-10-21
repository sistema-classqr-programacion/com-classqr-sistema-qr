package com.classqr.sistema.qr.controller;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.AsistenciaSeguridadDTO;
import com.classqr.sistema.commons.dto.RespuestaGeneralDTO;
import com.classqr.sistema.commons.service.IJwtService;
import com.classqr.sistema.qr.dto.AutenticationDTO;
import com.classqr.sistema.qr.service.ICreateQrService;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/qr")
@Log4j2
@RequiredArgsConstructor
public class QrController {

    private final ICreateQrService iCreateQrService;

    private final IJwtService iJwtService;

    @GetMapping("/asistencia")
    public ResponseEntity<byte[]> getQrAsistencia() {
        try {
            // Generar código QR
            byte[] qrImage = iCreateQrService.generateQRCode(300, 300);

            // Configurar los encabezados para devolver una imagen PNG
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            // Devolver la imagen con código de estado 200 OK
            return new ResponseEntity<>(qrImage, headers, HttpStatus.OK);
        } catch (IOException | WriterException e) {
            log.error("Error ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/autenticacion")
    public ResponseEntity<RespuestaGeneralDTO> postAutenticacion(@RequestBody AutenticationDTO autenticationDTO) {
        try {
            RespuestaGeneralDTO respuestaGeneralDTO = new RespuestaGeneralDTO();
            respuestaGeneralDTO.setStatus(HttpStatus.OK);
            respuestaGeneralDTO.setData(iJwtService.isTokenValid(autenticationDTO.getToken(), new AsistenciaSeguridadDTO(autenticationDTO.getAsistenciaDTO())));
            respuestaGeneralDTO.setMessage("Se valido correctamente");
            // Devolver la imagen con código de estado 200 OK
            return ResponseEntity.status(HttpStatus.OK.value()).body(respuestaGeneralDTO);
        } catch (Exception e) {
            log.error("Error ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
