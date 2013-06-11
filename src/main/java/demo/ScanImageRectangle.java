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
        String[] fileName = {
         /*   "SKMBT_22313061009380.pdf1.jpg",
            "SKMBT_22313061009380.pdf101.jpg",
            "SKMBT_22313061009380.pdf105.jpg",
            "SKMBT_22313061009380.pdf109.jpg",
            "SKMBT_22313061009380.pdf113.jpg",
            "SKMBT_22313061009380.pdf121.jpg",
            "SKMBT_22313061009380.pdf125.jpg",
            "SKMBT_22313061009380.pdf129.jpg",
            "SKMBT_22313061009380.pdf133.jpg",
            "SKMBT_22313061009380.pdf137.jpg",
            "SKMBT_22313061009380.pdf145.jpg",
            "SKMBT_22313061009380.pdf149.jpg",
            "SKMBT_22313061009380.pdf153.jpg",
            "SKMBT_22313061009380.pdf17.jpg",
            "SKMBT_22313061009380.pdf25.jpg",
            "SKMBT_22313061009380.pdf33.jpg",
            //"SKMBT_22313061009380.pdf37_fl.jpg",    //convert to grey 4 bu result the same
            "SKMBT_22313061009380.pdf41.jpg",
            "SKMBT_22313061009380.pdf49.jpg",
            "SKMBT_22313061009380.pdf5.jpg",
            "SKMBT_22313061009380.pdf57.jpg",
            "SKMBT_22313061009380.pdf61.jpg",
            "SKMBT_22313061009380.pdf65.jpg",
            //"SKMBT_22313061009380.pdf73.jpg", //expected drow ractangle
            "SKMBT_22313061009380.pdf77.jpg",
            "SKMBT_22313061009380.pdf81.jpg",
            //"SKMBT_22313061009380.pdf89_fl.jpg",      //convert to grey 4 bu result the same
            "SKMBT_22313061009380.pdf9.jpg",
            "SKMBT_22313061009380.pdf93.jpg",
            "SKMBT_22313061009380.pdf97.jpg",
            "SKMBT_22313061010260_0001.pdf1.jpg",
            "SKMBT_22313061010260_0002.pdf1.jpg",
            //"SKMBT_22313061010260_0003.pdf1_fl.jpg",      //convert to grey 4 bu result the same
            "SKMBT_22313061010260_0005.pdf1.jpg",
            "SKMBT_22313061010260_0007.pdf1.jpg",
            "SKMBT_22313061010260_0009.pdf1.jpg",
            "SKMBT_22313061010260_0010.pdf1_fl.jpg",       //convert to grey 4 bu result the same
            "SKMBT_22313061010260_0011.pdf1.jpg",
            "SKMBT_22313061010260_0013.pdf1.jpg",
            "SKMBT_22313061010260_0015.pdf1.jpg",
            "SKMBT_22313061010260_0016.pdf1.jpg",
            "SKMBT_22313061010260_0017.pdf1.jpg",
            //"SKMBT_22313061010260_0019.pdf1.jpg",           //expected drow ractangle
            "SKMBT_22313061010260_0020.pdf1.jpg",
            "SKMBT_22313061010260_0021.pdf1.jpg",
            "SKMBT_22313061010260_0023.pdf1.jpg",
            "SKMBT_22313061010260_0024.pdf1.jpg",
            "SKMBT_22313061010260_0025.pdf1.jpg",
            "SKMBT_22313061010260_0026.pdf1_fl.jpg",        //fixed convert to grey 4
            "SKMBT_22313061010260_0027.pdf1_fl.jpg",        //fixed convert to grey 4
            "SKMBT_22313061010260_0028.pdf1.jpg",
            "SKMBT_22313061010260_0029.pdf1.jpg",
            "SKMBT_22313061010260_0031.pdf1.jpg",
            "SKMBT_22313061010260_0032.pdf1.jpg",
            "SKMBT_22313061010260_0033.pdf1.jpg",
            "SKMBT_22313061010260_0034.pdf1.jpg",
            "SKMBT_22313061010260_0035.pdf1.jpg",
            "SKMBT_22313061010260_0037.pdf1.jpg",
            "SKMBT_22313061010260_0038.pdf1.jpg",
            "SKMBT_22313061010260_0039.pdf1.jpg"
          */
            "SimpleImages.pdf1.jpg",
            "SimpleImages.pdf101_fl.jpg", //fixed convert grey 4
            "SimpleImages.pdf104.jpg",
            "SimpleImages.pdf107.jpg",
            "SimpleImages.pdf113.jpg",
            //"SimpleImages.pdf116_fl.jpg", //convert buyt result the same
            "SimpleImages.pdf119.jpg",
            "SimpleImages.pdf14_fl.jpg",  //fixed convert grey 4
            "SimpleImages.pdf20.jpg",
            "SimpleImages.pdf26.jpg",
            "SimpleImages.pdf29_fl.jpg",    //fixed convert grey 4
            "SimpleImages.pdf32.jpg",
            "SimpleImages.pdf39.jpg",
            "SimpleImages.pdf45.jpg",
            "SimpleImages.pdf48.jpg",
            "SimpleImages.pdf5.jpg",
            "SimpleImages.pdf51.jpg",
            //"SimpleImages.pdf57.jpg", //expected
            "SimpleImages.pdf60.jpg",
            "SimpleImages.pdf63.jpg",
            "SimpleImages.pdf70.jpg",
            "SimpleImages.pdf73.jpg",
            "SimpleImages.pdf76.jpg",
            "SimpleImages.pdf79.jpg",
            "SimpleImages.pdf8.jpg",
            "SimpleImages.pdf82_fl.jpg",    //fixed convert grey 4
            "SimpleImages.pdf85.jpg",
            "SimpleImages.pdf88.jpg",
            "SimpleImages.pdf94.jpg",
            "SimpleImages.pdf98.jpg"
            /*"SKMBT_22313060714040_0001.jpg", "SKMBT_22313060714040_0002.jpg", "SKMBT_22313060714040_0003.jpg",
            "SKMBT_22313060714040_0005.jpg", "SKMBT_22313060714040_0007.jpg", "SKMBT_22313060714040_0009.jpg",
            "SKMBT_22313060714040_0010.jpg", "SKMBT_22313060714040_0011.jpg", "SKMBT_22313060714040_0013.jpg",
            "SKMBT_22313060714040_0015.jpg", "SKMBT_22313060714040_0016.jpg", "SKMBT_22313060714040_0017.jpg",
            "SKMBT_22313060714040_0019.jpg", "SKMBT_22313060714040_0020.jpg", "SKMBT_22313060714040_0021.jpg",
            "SKMBT_22313060714040_0023.jpg", "SKMBT_22313060714040_0024.jpg", "SKMBT_22313060714040_0025.jpg",
            "SKMBT_22313060714040_0026.jpg", "SKMBT_22313060714040_0027.jpg", "SKMBT_22313060714040_0028.jpg",
            "SKMBT_22313060714040_0029.jpg", "SKMBT_22313060714040_0031.jpg", "SKMBT_22313060714040_0032.jpg",
            "SKMBT_22313060714040_0033.jpg", "SKMBT_22313060714040_0034.jpg", "SKMBT_22313060714040_0035.jpg",
            "SKMBT_22313060714040_0037.jpg", "SKMBT_22313060714040_0038.jpg", "SKMBT_22313060714040_0039.jpg"*/
        };
        
        
        
        
        for (int j = 0; j < fileName.length; j++) {
            File file = new File("img/scaned_files/pdf_scan/img_temp/" + fileName[j]);
            //File file = new File("img/11.png");
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file).getSubimage(1100, 2700, 1150, 600))));
            Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

            Detector det = null;
            DetectorResult res = null;

            /*try {
                det = new Detector(binaryBitmap.getBlackMatrix());
                res = det.detect(hints);

                System.out.println(res.getPoints().length);
                for (int i = 0; i < res.getPoints().length; i++) {
                    System.out.println(res.getPoints()[i]);
                }
                //System.out.println(res.getBits());
            } catch (NotFoundException e) {
                System.out.println("ScanImageRectangle.main()");
                e.printStackTrace();
            }*/
            try {
                System.out.println("File " +fileName[j] + " " + SimpleReadWriteBarCode.readCode(file, hints));
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
