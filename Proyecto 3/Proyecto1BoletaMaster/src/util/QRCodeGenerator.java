package util;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import javax.swing.ImageIcon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {

    private QRCodeGenerator() {}

    public static String buildPayload(String nombreEvento, int idT,
                                      LocalDate fechaEvento, LocalDateTime fechaImpresion) {

        return  "Evento:" + nombreEvento +
                "\nID:" + idT +
                "\nF.Evento:" + fechaEvento +
                "\nF.Impresion:" + fechaImpresion;
    }

    public static BufferedImage generate(String payload, int size) {
        try {
            var hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.MARGIN, 1);

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(payload, BarcodeFormat.QR_CODE, size, size, hints);

            BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    img.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return img;

        } catch (WriterException e) {
            throw new RuntimeException("Error generando QR: " + e.getMessage(), e);
        }
    }

    public static ImageIcon toIcon(BufferedImage img) {
        return new ImageIcon(img);
    }
}
