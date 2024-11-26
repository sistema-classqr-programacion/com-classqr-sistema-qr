package com.classqr.sistema.qr.service;

import com.classqr.sistema.qr.dto.GenerarQrDTO;
import com.classqr.sistema.qr.dto.QueryQrDTO;
import com.google.zxing.WriterException;

import java.io.IOException;

public interface ICreateQrService {


    GenerarQrDTO generateQRCode(int width, int height, QueryQrDTO codigoQr) throws IOException, WriterException;


}
