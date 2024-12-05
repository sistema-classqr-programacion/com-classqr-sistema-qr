/**
 * DTO que encapsula los datos generados para un código QR.
 * Contiene la imagen del QR y un código asociado.
 */
package com.classqr.sistema.qr.dto;

import lombok.Data;

@Data
public class GenerarQrDTO {

    /**
     * Representación de la imagen del código QR en formato Base64.
     */
    private String imagen;

    /**
     * Código asociado al QR generado, que puede ser utilizado para identificarlo.
     */
    private String codigo;

}
