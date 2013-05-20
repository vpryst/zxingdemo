package org.halyph.zxing.demo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;

public class CheckDM {
	public static void main(String[] args) throws IOException {
		Map<EncodeHintType,Object> hints = new EnumMap<EncodeHintType,Object>(EncodeHintType.class);
	    hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);

	    int bigEnough = 1000;
	    DataMatrixWriter writer = new DataMatrixWriter();
	    BitMatrix matrix = writer.encode("Orest Ivasiv 50e8400-e29b-41d4-a716-446655440000", BarcodeFormat.DATA_MATRIX, bigEnough, bigEnough, hints);
	    
	    String fileName = "code.png";
		File file = createTempFile(fileName);
		MatrixToImageWriter.writeToFile(matrix, "PNG", file);
		System.out.println("printing to " + file.getAbsolutePath());
		Desktop.getDesktop().open(file);
		
	}
	

	 private static File createTempFile(String fileName) throws IOException {
	        File file = File.createTempFile("DataMatrix", ".png");
	        return file;
	 }
	    
}
