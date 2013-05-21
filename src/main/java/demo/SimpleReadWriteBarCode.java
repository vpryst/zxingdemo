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

    /**
     * important if specified char set
     */
    private static final String CHARSET_NAME = "UTF-8";
    /**
     * important for image size;
     */
    private static int width = 100;
    private static int height = 100;

    /**
     * String to encode
     */
    private static String encodeData = "sgjhkjghsdkjg 50e8400-e29b-41d4-a716-446655440000|4";

    /**
     * constructor to change text encode.
     * 
     * @param text
     */
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

        /**
         * Settings for QRCode. Write to image
         */
        Map<EncodeHintType, Object> hintsQR = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintsQR.put(EncodeHintType.CHARACTER_SET, CHARSET_NAME);
        hintsQR.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);

        /**
         * write to image
         */
        File fileQR = createFile("QRCode");
        writeCode(fileQR, encodeData, BarcodeFormat.QR_CODE, width, height, hintsQR);
        showFile(fileQR);
        /**
         * Settings QRCode to read from image.
         */
        Map<DecodeHintType, Object> decodeHints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        decodeHints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
        /**
         * read QRCode
         */
        String dataOut = readCode(fileQR, decodeHints);
        System.out.println(dataOut);

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * Settings for DataMatrix
         */
        Map<EncodeHintType, Object> hintsDM = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hintsDM.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);
        /**
         * write dataMatrix to image
         */
        File fileDM = createFile("DataMatrix");
        writeCode(fileDM, encodeData, BarcodeFormat.DATA_MATRIX, width, height, hintsDM);
        showFile(fileDM);

        /**
         * Settings for read DataMatrix
         */
        Map<DecodeHintType, Object> hintsRead = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hintsRead.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.DATA_MATRIX));
        // hintsRead.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        /**
         * read DataMatrix
         */
        String data = readCode(fileDM, hintsRead);
        System.out.println(data);
    }

    /**
     * Method to write BarCode to image
     * 
     * @param file - file name image
     * @param data - string to encode into BarCode
     * @param barcode - type of BarCode QRcode, DataMatrix
     * @param width - width of image
     * @param height - height of image
     * @param hints - settings of encode BarCode to image
     * @throws IOException
     * @throws WriterException
     */
    public static void writeCode(File file, String data, BarcodeFormat barcode, int width, int height, Map<EncodeHintType, Object> hints)
        throws IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(data, barcode, width, height, hints);
        MatrixToImageWriter.writeToFile(matrix, "PNG", file);
    }

    /**
     * Method to read BarCode from image
     * 
     * @param file - image file name
     * @param hints - settings of read image
     * @return - Decoded string
     * @throws IOException
     * @throws NotFoundException
     */
    public static String readCode(File file, Map<DecodeHintType, ?> hints) throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        Result result = new MultiFormatReader().decode(binaryBitmap, hints);
        return result.getText();
    }

    /**
     * Create temporary file in temp directory
     * 
     * @param fileName
     * @return - file
     * @throws IOException
     */
    public static File createFile(String fileName) throws IOException {
        File file = File.createTempFile(fileName, ".png");
        return file;
    }

    /**
     * Open file
     * 
     * @param file - file name
     * @throws IOException
     */
    private static void showFile(File file) throws IOException {
        System.out.println("printing to " + file.getAbsolutePath());
        Desktop.getDesktop().open(file);
    }
}
