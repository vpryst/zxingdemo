package FindCircle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class FinderCircle {
    
    public FinderCircle(String fileName) throws Exception {
        this(ImageIO.read(new File(fileName)));
    }
    public FinderCircle(BufferedImage img) throws Exception {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
        System.out.println(binaryBitmap.getBlackMatrix());
    }

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String filename = "img/Circle/circle.png";
        
        FinderCircle find = new FinderCircle(filename);
        
    }

}
