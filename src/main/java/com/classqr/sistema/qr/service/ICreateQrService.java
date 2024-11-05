package com.classqr.sistema.qr.service;

import com.classqr.sistema.qr.dto.GenerarQrDTO;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface ICreateQrService {


    GenerarQrDTO generateQRCode(int width, int height, String codigoQr) throws IOException, WriterException;


}
