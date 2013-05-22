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
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

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
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        
        File file = new File("img/rectangle_qr_rot_r_60.jpg");
        BinaryBitmap binaryBitmap = 
            new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        
        FinderPatternFinder find = new FinderPatternFinder(binaryBitmap.getBlackMatrix());
        FinderPatternInfo findInfo = find.find(hints);
        System.out.println(findInfo.getBottomLeft());
        System.out.println(findInfo.getTopLeft());
        System.out.println(findInfo.getTopRight());

    }

}
