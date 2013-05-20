package demo;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;

public class SampleReadWriteDMCode {
    public static void main(String[] args) throws IOException {
        Map<EncodeHintType,Object> hints = new EnumMap<EncodeHintType,Object>(EncodeHintType.class);
        hints.put(EncodeHintType.DATA_MATRIX_SHAPE, SymbolShapeHint.FORCE_SQUARE);

        int bigEnough = 1020;
        DataMatrixWriter writer = new DataMatrixWriter();
        BitMatrix matrix = writer.encode("Orest Ivasiv 50e8400-e29b-41d4-a716-446655440000", BarcodeFormat.DATA_MATRIX, bigEnough, bigEnough, hints);

        
        String fileName = "code.png";
        File file = createTempFile(fileName);
        MatrixToImageWriter.writeToFile(matrix, "PNG", file);
        System.out.println("printing to " + file.getAbsolutePath());
        Desktop.getDesktop().open(file);
        
    
        Map<DecodeHintType,Object> hintsRead = new EnumMap<DecodeHintType,Object>(DecodeHintType.class);
        hintsRead.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.DATA_MATRIX));
          hintsRead.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        try {
            String data = readDMCode(file.getAbsolutePath(),  hintsRead);
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    

    
    }
    public static String readDMCode(String filename,
             Map<DecodeHintType,?> hints)
            throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filename)))));
        Result result =null;
        try {
            result = new DataMatrixReader().decode(binaryBitmap, hints);
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

        return result.getText();
    }
    
    
    

     private static File createTempFile(String fileName) throws IOException {
            File file = File.createTempFile("DataMatrix", ".png");
            return file;
     }
        
}
