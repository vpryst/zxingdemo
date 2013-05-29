package coordiate.tarnsform;

import java.awt.Desktop;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lowagie.text.Image;

public class AffineTransformBufImageDemo {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        File file = new File("img/scaned_files/first_page_gs_ed.png");
        BufferedImage before = ImageIO.read(file); // getBufferedImage(encoded);
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        System.out.println(at.getShearX());
        //at.scale(1.0, 1.0);
        //at.setToTranslation(0, 2000);
        double angle = 30*Math.PI/180;
        System.out.println(angle);
        at.rotate(angle);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, null);
        /*File out = new File("sav.png");
        ImageIO.write(after, "png", out);
        Desktop.getDesktop().open(out);*/
    }

}
