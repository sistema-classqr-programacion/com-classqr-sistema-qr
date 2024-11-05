package com.classqr.sistema.qr.dto;

import com.classqr.sistema.commons.dto.AsistenciaDTO;
import com.classqr.sistema.commons.dto.QrDTO;
import lombok.Data;

@Data
public class AutenticationDTO {

    private String token;

    private QrDTO qrDTO;

}
