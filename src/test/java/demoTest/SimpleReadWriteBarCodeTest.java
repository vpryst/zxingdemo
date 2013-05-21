package demoTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import static demo.SimpleReadWriteBarCode.*;

public class SimpleReadWriteBarCodeTest {

    @Test
    public void getTextDM() {

        Map<DecodeHintType, Object> hintsRead = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hintsRead.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.DATA_MATRIX));
        //hintsRead.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hintsRead.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        for (int i = 1; i < 10; i++) {
            try {
                File file = new File("img/" + i + ".png");
                System.out.println(file.getAbsolutePath());
                String data = readCode(file, hintsRead);
                System.out.println(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
   @Test
    public void getTextDMWithoutText() {

        Map<DecodeHintType, Object> hintsRead = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hintsRead.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.DATA_MATRIX));
        //hintsRead.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hintsRead.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        for (int i = 21; i < 24; i++) {
            try {
                File file = new File("img/" + i + ".png");
                System.out.println(file.getAbsolutePath());
                String data = readCode(file, hintsRead);
                System.out.println(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void getTextQr() {
        Map<DecodeHintType, Object> decodeHints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        decodeHints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
        for (int i = 11; i < 20; i++) {
            try {
            File file = new File("img/" + i + ".png");
            System.out.println(file.getAbsolutePath());
            String data = readCode(file, decodeHints);
            System.out.println(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
