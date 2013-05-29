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

/**
 * This class search marker at page and and return coordinate
 * 
 * @author vpryst
 */

public class FinderMarkerDemo {
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

    public FinderMarkerDemo(String fileName) {
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
        try {
            binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(file))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        FinderPatternFinder find = null;
        try {
            find = new FinderPatternFinder(binaryBitmap.getBlackMatrix());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        try {
            FinderPatternInfo findInfo = find.find(hints);

            setBottomLeft(findInfo.getBottomLeft());
            setTopLeft(findInfo.getTopLeft());
            setTopRight(findInfo.getTopRight());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

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

    public static void main(String[] args) throws IOException, NotFoundException, ChecksumException, FormatException {

        FinderMarkerDemo demo = new FinderMarkerDemo("img/scaned_files/oi/136981212428468.png");

        System.out.println(demo.getBottomLeft());
        System.out.println(demo.getTopLeft());
        System.out.println(demo.getTopRight());

    }

}
