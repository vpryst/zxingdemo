package blackness;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import scaner.zxing.HybridBinarizerEx;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;

public class CalculateTemp {
    HybridBinarizerEx hib;
    BufferedImageLuminanceSource lum;

    public CalculateTemp(String fileName) throws IOException {
        this(ImageIO.read(new File(fileName)));
    }

    public CalculateTemp(BufferedImage image) {
        byte[] source = new BufferedImageLuminanceSource(image).getMatrix();
        double avareg = 0;
        int count = 0;
        for (int i = 0; i < source.length; i++) {
            count += source[i] & 0xFF;
        }
        double area = image.getWidth() * image.getHeight();
        avareg = count / area;
        avareg = 1 - avareg / 255.0;
        System.out.println(avareg);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        CalculateTemp calculate = null;
        File dir = new File("img/blackness/");
        for (int i = 0; i < dir.listFiles().length; i++) {
            System.out.println(dir.listFiles()[i]);
            try {
                calculate = new CalculateTemp(dir.listFiles()[i].getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
