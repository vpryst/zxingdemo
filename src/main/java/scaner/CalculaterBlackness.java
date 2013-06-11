package scaner;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import scaner.zxing.HybridBinarizerEx;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;

public class CalculaterBlackness {

    private BitMatrix bitmatrix;

    public CalculaterBlackness(String fileName) throws IOException {
        this(ImageIO.read(new File(fileName)));
    }

    public CalculaterBlackness(BufferedImage image) {
        try {
            bitmatrix = new BinaryBitmap(new HybridBinarizerEx(new BufferedImageLuminanceSource(image))).getBlackMatrix();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    public double calculateBlacknesRectangle() {
        int width = bitmatrix.getWidth();
        int height = bitmatrix.getHeight();
        int count = 0;
        double persent = width * height;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (bitmatrix.get(i, j)) {
                    count++;
                }
            }
        }
//        System.out.println("Square: " + persent);

        persent = count / persent;

//        System.out.println("Count: " + count);
//        System.err.println("Persent: " + persent);
        System.out.println(bitmatrix);
        return persent;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(4>>3);
        CalculaterBlackness calculate = null;
        try {
            calculate = new CalculaterBlackness("img/bugs/error.png");
            //calculate = new CalculaterBlackness("img/1.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(calculate.calculateBlacknesRectangle());
    }

}
