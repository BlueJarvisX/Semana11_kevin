package com.andres.pasaportecafeovalle.utils;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

import com.andres.pasaportecafeovalle.data.repository.ClientesRepositoty;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    private final ClientesRepositoty clientesRepository;

    public Utils(ClientesRepositoty clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    public static String obtenerFechaActual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generando hash de contraseña", e);
        }
    }

    public static String generarHashQR(int id_cliente, int id_sucursal) {

        String data = id_cliente + "-" + id_sucursal + "-" + System.currentTimeMillis();
        return data;
    }

    public static Bitmap generarQRBitmap(String contenido, int ancho, int alto) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(contenido, BarcodeFormat.QR_CODE, ancho, alto);
            Bitmap bmp = Bitmap.createBitmap(ancho, alto, Bitmap.Config.RGB_565);

            for (int x = 0; x < ancho; x++) {
                for (int y = 0; y < alto; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
