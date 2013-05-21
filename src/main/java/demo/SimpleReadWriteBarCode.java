package demo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class SimpleReadWriteBarCode {

    private static final String CHARSET_NAME = "UTF-8";
    private static int width = 100;
    private static int height = 100;
    
    private static String encodeData = "sgjhkjghsdkjg 50e8400-e29b-41d4-a716-446655440000|4";
    
    public SimpleReadWriteBarCode(String text) {
        encodeData = text;
    }


    /**
     * @param args
     * @throws IOException
     * @throws NotFoundException
     * @throws WriterException
     */
    public static void main(String[] args) throws IOException, NotFoundException, WriterException {
        

        Map<EncodeHintType, Object> hintsQR = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintsQR.put(EncodeHintType.CHARACTER_SET, CHARSET_NAME);
        hintsQR.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);

        File fileQR = createFile("QRCode");
        writeCode(fileQR, encodeData, BarcodeFormat.QR_CODE, width, height, hintsQR);
        showFile(fileQR);

        Map<DecodeHintType, Object> decodeHints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        decodeHints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
        String dataOut = readCode(fileQR, decodeHints);
        System.out.println(dataOut);

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Map<EncodeHintType, Object> hintsDM = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintsDM.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);

        File fileDM = createFile("DataMatrix");
        writeCode(fileDM, encodeData, BarcodeFormat.DATA_MATRIX, width, height, hintsDM);
        showFile(fileDM);

        // ///////////////////////////////////////////////////////////////////////////////////////////////
        Map<DecodeHintType, Object> hintsRead = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hintsRead.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.DATA_MATRIX));
        hintsRead.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

        String data = readCode(fileDM, hintsRead);
        System.out.println(data);
    }

    public static void writeCode(File file, String data, BarcodeFormat barcode, int width, int height, Map<EncodeHintType, Object> hints)
        throws IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(data, barcode, width, height, hints);
        MatrixToImageWriter.writeToFile(matrix, "PNG", file);
    }

    public static String readCode(File file, Map<DecodeHintType, ?> hints) throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);
        return result.getText();
    }

    public static File createFile(String fileName) throws IOException {
        File file = File.createTempFile(fileName, ".png");
        return file;
    }

    private static void showFile(File file) throws IOException {
        System.out.println("printing to " + file.getAbsolutePath());
        Desktop.getDesktop().open(file);
    }
}
