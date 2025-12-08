package gui;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

	public class QRCodeGenerator {

	    public static String buildPayload(String nombreEvento, int idT, String fechaEvento, LocalDateTime fechaImpresion) {
	        StringBuilder sb = new StringBuilder();
	        sb.append("EVENTO: ").append(nombreEvento).append("\n");
	        sb.append("ID_TIQUETE: ").append(idT).append("\n");
	        sb.append("FECHA_EVENTO: ").append(fechaEvento).append("\n");
	        sb.append("FECHA_IMPRESION: ").append(
	                fechaImpresion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
	        );
	        return sb.toString();
	    }

	    public static BufferedImage generate(String text, int size) throws Exception {

	        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

	        BitMatrix matrix = new MultiFormatWriter()
	                .encode(text, BarcodeFormat.QR_CODE, size, size, hints);

	        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

	        for (int x = 0; x < size; x++) {
	            for (int y = 0; y < size; y++) {
	                int value = matrix.get(x, y) ? 0x000000 : 0xFFFFFF;
	                image.setRGB(x, y, value);
	            }
	        }

	        return image;
	}
}
