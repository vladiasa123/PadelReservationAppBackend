package com.example.demo.Controller;

import com.example.demo.Model.Reservation;
import com.example.demo.Model.ReservationService;
import io.nayuki.qrcodegen.QrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    public static BufferedImage generateQrcode(String barcodeText) throws Exception {
        QrCode qrCode = QrCode.encodeText(barcodeText, QrCode.Ecc.MEDIUM);
        BufferedImage img = toImage(qrCode, 4, 10);
        File outputFile = new File("qrcode.png");
        return img;
    }

    public static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    public static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        BufferedImage result = new BufferedImage(
                (qr.size + border * 2) * scale,
                (qr.size + border * 2) * scale,
                BufferedImage.TYPE_INT_RGB
        );
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }

    @PostMapping(value = "/req/Reservation", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> reservationEntry(@RequestBody Reservation reservation) throws Exception {
        System.out.println(reservation.getDate() + " " + reservation.getHour() + " " + reservation.getDayId());
        reservation = reservationService.saveReservation(reservation);
      String qrCodeText = "day" + reservation.getHour() + "hour" + reservation.getDate();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecretKey myDesKey = keyGenerator.generateKey();

        Cipher desCipher;
        desCipher = Cipher.getInstance("DES");

        byte[] text = qrCodeText.getBytes("UTF8");

        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
        byte[] encryptedText = desCipher.doFinal(text);

        String encryptedString = new String(encryptedText);

        BufferedImage qrCodeImage = generateQrcode(encryptedString);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, "PNG", byteArrayOutputStream);
        byte[] qrCodeBytes = byteArrayOutputStream.toByteArray();

        String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);

        Map<String, String> response = new HashMap<>();
        response.put("message", qrCodeBase64);
        System.out.println(reservation.getDate() + " " + reservation.getHour() + " " + reservation.getDayId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
