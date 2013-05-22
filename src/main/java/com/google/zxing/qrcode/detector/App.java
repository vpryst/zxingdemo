package com.google.zxing.qrcode.detector;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

public class App {
    /**
     * @param args
     * @throws IOException 
     * @throws FormatException 
     * @throws ChecksumException 
     * @throws NotFoundException 
     */
    public static void main(String[] args) throws IOException, NotFoundException, ChecksumException, FormatException {
        Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
        hints.put(DecodeHintType.TRY_HARDER, Arrays.asList(BarcodeFormat.QR_CODE));
        
        File file = new File("img/rectangle_qr_rot.png");
        BinaryBitmap binaryBitmap = 
            new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        //Result result = new QRCodeReader().decode(binaryBitmap, hints);
        Detector det = new Detector(binaryBitmap.getBlackMatrix());
        DetectorResult res = det.detect();
        System.out.println(res.getPoints().length);
        for (int i = 0; i<res.getPoints().length; i++) {
            System.out.println(res.getPoints()[i]);
        }

    }

}
