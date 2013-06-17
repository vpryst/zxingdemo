package blackness;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;

import org.w3c.dom.Element;

public class SetDPI {
    public static void main(String[] args) throws Exception {
        File infile = new File("img/scaned_files/subJpg.jpg");
        File outfile = new File("img/scaned_files/sub123.jpg");

        FileImageInputStream in = new FileImageInputStream(infile);
        Iterator<ImageReader> iterator = ImageIO.getImageReaders(in);
        ImageReader reader = iterator.next();
        reader.setInput(in);

        IIOMetadata data = reader.getImageMetadata(0);

        // metadata format names are: javax_imageio_jpeg_image_1.0 or javax_imageio_1.0

        Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
        Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
        String dpiX = jfif.getAttribute("Xdensity");
        String dpiY = jfif.getAttribute("Ydensity");

        System.out.println("  Density of source is " + dpiX + "x" + dpiY);

        BufferedImage image = ImageIO.read(infile);

        ImageWriter writer = ImageIO.getImageWriter(reader);
        FileImageOutputStream out = new FileImageOutputStream(outfile);
        writer.setOutput(out);

        ImageWriteParam writeParams = writer.getDefaultWriteParam();
        writeParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParams.setCompressionQuality(0.75f);

        data = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), writeParams);
        tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
        jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
        jfif.setAttribute("Xdensity", "300");
        jfif.setAttribute("Ydensity", "300");
        jfif.setAttribute("resUnits", "1"); // density is dots per inch

        writer.write(data, new IIOImage(image, null, null), writeParams);

        in.close();
        out.close();
    }
}