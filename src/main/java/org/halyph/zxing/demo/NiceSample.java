package org.halyph.zxing.demo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class NiceSample {
	
	private static final String CHARSET_NAME = "UTF-8";
	private static int width = 100;
	private static int height = 100;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws NotFoundException 
	 * @throws WriterException 
	 */
	public static void main(String[] args) throws IOException, NotFoundException, WriterException {
		String filename = "qrcode";
		String data = "50e8400-e29b-41d4-a716-446655440000|4";
		Map<EncodeHintType,Object> hints = new EnumMap<EncodeHintType,Object>(EncodeHintType.class);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET_NAME);
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
//		hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
		
		File file = createTempFile(filename);
		System.out.println("printing to " + file.getAbsolutePath());
		writeQrCode(file, "QR"+data, hints);		
		Desktop.getDesktop().open(file);
		
		Map<DecodeHintType,Object> decodeHints = new EnumMap<DecodeHintType,Object>(DecodeHintType.class);
		decodeHints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.DATA_MATRIX, BarcodeFormat.QR_CODE));
		String dataOut = readCode(file,  decodeHints);
		System.out.println(dataOut);
		
	}

//	private static void writeCode(File file, String data, Map<EncodeHintType,Object> hints) throws IOException  {
//        BitMatrix matrix = new DataMatrixWriter()
//        	.encode(new String(data.getBytes(CHARSET_NAME), CHARSET_NAME), 
//        		BarcodeFormat.DATA_MATRIX, width, height, hints);
//        MatrixToImageWriter.writeToFile(matrix, "PNG", file);
//	}
	
	private static void writeQrCode(File file, String data, Map<EncodeHintType,Object> hints) throws IOException, WriterException  {
        BitMatrix matrix = new MultiFormatWriter()
        	.encode(new String(data.getBytes(CHARSET_NAME), CHARSET_NAME), 
        		BarcodeFormat.QR_CODE, width, height, hints);
        MatrixToImageWriter.writeToFile(matrix, "PNG", file);
	}
	
	private static String readCode(File file, Map<DecodeHintType,Object> hints) throws IOException, NotFoundException  {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);
        return result.getText();   
	}
	
	
	
	private static File createTempFile(String fileName) throws IOException {
        File file = File.createTempFile("QRCode", ".png");
//        file.deleteOnExit();
        return file;
    }
}
