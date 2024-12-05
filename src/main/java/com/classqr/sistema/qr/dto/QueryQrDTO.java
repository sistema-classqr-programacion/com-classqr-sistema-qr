/**
 * DTO que encapsula los datos necesarios para realizar consultas relacionadas con códigos QR.
 * Contiene información del profesor y del curso asociado.
 */
package com.classqr.sistema.qr.dto;

import lombok.Data;

@Data
public class QueryQrDTO {

    /**
     * Código único del profesor asociado al QR.
     */
    private String codigoProfesor;

    /**
     * Código único del curso asociado al QR.
     */
    private String codigoCurso;

}
