package demo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.detector.Detector;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;

public class ScanImageRectangle {

    /**
     * @param args
     * @throws IOException
     * @throws NotFoundException
     * @throws FormatException
     */
    public static void main(String[] args) throws IOException, FormatException {
        File file = new File("img/scaned_files/first_page.jpg");
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        Detector det = null;
        DetectorResult res = null;
        try {
            det = new Detector(binaryBitmap.getBlackMatrix());
            res = det.detect(hints);

            System.out.println(res.getPoints().length);
            for (int i = 0; i < res.getPoints().length; i++) {
                System.out.println(res.getPoints()[i]);
            }
            System.out.println(res.getBits());
        } catch (NotFoundException e) {
            System.out.println("ScanImageRectangle.main()");
            e.printStackTrace();
        }
        try {
            ResultPointCallback ress = null;
            FinderPatternFinder find = new FinderPatternFinder(binaryBitmap.getBlackMatrix());
            
            
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
