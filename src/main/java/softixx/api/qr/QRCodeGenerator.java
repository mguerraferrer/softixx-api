package softixx.api.qr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import lombok.val;

public class QRCodeGenerator {
	
	public static BufferedImage generateQRCodeImage(String qrText, int width, int height) throws Exception {
		val qrCodeWriter = new QRCodeWriter();
		val bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
	
	public static void generateQRCodeImage(String qrText, int width, int height, String filePath) throws WriterException, IOException {
		val qrCodeWriter = new QRCodeWriter();
		val bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

		val path = FileSystems.getDefault().getPath(filePath);
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
	}
	
	public static byte[] getQRCodeImage(String qrText, int width, int height) throws WriterException, IOException {
		val qrCodeWriter = new QRCodeWriter();
		val bitMatrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, width, height);

		val pngOutputStream = new ByteArrayOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, ImageFormat.PNG.name(), pngOutputStream);
		return pngOutputStream.toByteArray();
	}
	
	public enum ImageFormat {
		JPG, PNG
	}

}