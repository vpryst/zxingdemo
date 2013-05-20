package org.halyph.zxing.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.HybridBinarizer;

public class AppDataMatrixReader {
	public static void main(String[] args) {

		String filename = "D:/Projects/Saba/sandbox/zxing.demo/out.png";
		
//		String charset = "UTF-8";

		Map<DecodeHintType,Object> hints = new EnumMap<DecodeHintType,Object>(DecodeHintType.class);
		hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.DATA_MATRIX));
		  hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
		try {
			String data = readQrCode(filename,  hints);
			System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readQrCode(String filename,
			 Map<DecodeHintType,?> hints)
			throws FileNotFoundException, IOException, NotFoundException {
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				new BufferedImageLuminanceSource(
						ImageIO.read(new FileInputStream(filename)))));
		Result result = new MultiFormatReader().decode(binaryBitmap, hints);

		return result.getText();
	}
}
