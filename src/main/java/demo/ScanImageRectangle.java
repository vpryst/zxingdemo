package demo;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.detector.Detector;

public class ScanImageRectangle {

    /**
     * @param args
     * @throws IOException
     * @throws NotFoundException 
     * @throws FormatException 
     */
    public static void main(String[] args) throws IOException, NotFoundException, FormatException {
        File file = new File("img/rectangle_qr_rot.png");
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        
        Detector det = new Detector(binaryBitmap.getBlackMatrix());
        DetectorResult res = det.detect();
        System.out.println(res.getPoints().length);
        for (int i = 0; i<res.getPoints().length; i++) {
            System.out.println(res.getPoints()[i]);
        }
        System.out.println(res.getBits());
    }

}
