package com.google.zxing.qrcode.detector;

import java.awt.image.BufferedImage;
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

/**
 * This class search marker at page and and return coordinate
 * 
 * @author vpryst
 */

public class MarkerFinder {
    /**
     * @param args
     * @throws IOException
     * @throws FormatException
     * @throws ChecksumException
     * @throws NotFoundException
     */
    private FinderPattern bottomLeft;
    private FinderPattern topLeft;
    private FinderPattern topRight;

    public MarkerFinder(String fileName) {
        /**
         * Settings of hints not important
         */
        Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        /**
         * Use FinderPatternFinder from QR-Code
         */
        File file = new File(fileName);
        BinaryBitmap binaryBitmap = null;
        FinderPatternFinder find = null;
        FinderPatternInfo findInfo = null;
        try {
            binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
            find = new FinderPatternFinder(binaryBitmap.getBlackMatrix());
            findInfo = find.find(hints);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        /**
         * Set found 3 markers.
         */
        setBottomLeft(findInfo.getBottomLeft());
        setTopLeft(findInfo.getTopLeft());
        setTopRight(findInfo.getTopRight());
    }

    public MarkerFinder(BufferedImage img) {
        /**
         * Settings of hints not important
         */
        Map<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, Arrays.asList(BarcodeFormat.QR_CODE));
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        /**
         * Use FinderPatternFinder from QR-Code
         */
        BinaryBitmap binaryBitmap = null;
        FinderPatternFinder find = null;
        FinderPatternInfo findInfo = null;
        try {
            binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
            find = new FinderPatternFinder(binaryBitmap.getBlackMatrix());
            findInfo = find.find(hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        /**
         * Set found 3 markers.
         */
        setBottomLeft(findInfo.getBottomLeft());
        setTopLeft(findInfo.getTopLeft());
        setTopRight(findInfo.getTopRight());
    }

    public FinderPattern getBottomLeft() {
        return bottomLeft;
    }

    public FinderPattern getTopLeft() {
        return topLeft;
    }

    public FinderPattern getTopRight() {
        return topRight;
    }

    public void setBottomLeft(FinderPattern bottomleft) {
        this.bottomLeft = bottomleft;
    }

    public void setTopLeft(FinderPattern topLeft) {
        this.topLeft = topLeft;
    }

    public void setTopRight(FinderPattern topRight) {
        this.topRight = topRight;
    }

    public static void main(String[] args) {
        MarkerFinder demo = new MarkerFinder("img/scaned_files/second_page_foxit_ed.png");
        System.out.println(demo.getBottomLeft());
        System.out.println(demo.getTopLeft());
        System.out.println(demo.getTopRight());
    }
}
