package com.classqr.sistema.qr.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface ICreateQrService {


    byte[] generateQRCode( int width, int height) throws IOException, WriterException;


}
