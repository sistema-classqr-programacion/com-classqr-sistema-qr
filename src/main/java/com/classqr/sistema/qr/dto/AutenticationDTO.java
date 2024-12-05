/**
 * DTO que encapsula los datos necesarios para la autenticación basada en un código QR.
 * Contiene un token y la información del QR asociado.
 */
package com.classqr.sistema.qr.dto;

import com.classqr.sistema.commons.dto.QrDTO;
import lombok.Data;

@Data
public class AutenticationDTO {

    /**
     * Token de autenticación utilizado para validar la solicitud.
     */
    private String token;

    /**
     * Objeto {@link QrDTO} que contiene los datos del código QR asociado.
     */
    private QrDTO qrDTO;

}
