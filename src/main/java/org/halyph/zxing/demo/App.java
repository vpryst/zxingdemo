package org.halyph.zxing.demo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.*;

public class App {

	public static void main(String[] args) {
		Charset charset = Charset.forName("UTF-8");
		CharsetEncoder encoder = charset.newEncoder();
		byte[] b = null;
		try {
			// Convert a string to UTF-8 bytes in a ByteBuffer
			ByteBuffer bbuf = encoder
					.encode(CharBuffer
							.wrap("utf 8 characters - i used hebrew, but you should write some of your own language characters"));
			b = bbuf.array();
		} catch (CharacterCodingException e) {
			System.out.println(e.getMessage());
		}

		String data;
		try {
            // get a byte matrix for the data
            data = new String(b, "UTF-8");
            BitMatrix matrix = null;
			int h = 100;
			int w = 100;
			com.google.zxing.Writer writer = new MultiFormatWriter();
			try {
				Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>(
						2);
				hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
				matrix = writer.encode(data,
						BarcodeFormat.QR_CODE, w, h, hints);
			} catch (com.google.zxing.WriterException e) {
				System.out.println(e.getMessage());
			}

			// change this path to match yours (this is my mac home folder, you
			// can use: c:\\qr_png.png if you are on windows)
			String fileName = "qr_png.png";
			File file = createTempFile(fileName);
//			System.out.format("File location: %s", file.getAbsolutePath());
			
			try {
				MatrixToImageWriter.writeToFile(matrix, "PNG", file);
				System.out.println("printing to " + file.getAbsolutePath());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	 private static File createTempFile(String fileName) throws IOException {
	        File file = File.createTempFile("QRCode", ".png");
//	        file.deleteOnExit();
	        return file;
	    }

}