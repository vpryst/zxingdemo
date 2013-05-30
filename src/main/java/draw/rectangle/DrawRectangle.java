package draw.rectangle;

import static coordiate.tarnsform.UnitConv.mm2px;
import static coordiate.tarnsform.UnitConv.pt2mm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import coordiate.tarnsform.CoordinateTransformer;
import coordiate.tarnsform.FindMarkerAfterScanDemo;

public class DrawRectangle {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String fileName = "img/scaned_files/first_page_gs_ed_rt15.png";
        CoordinateTransformer scan = new FindMarkerAfterScanDemo(fileName, 300, 841);
        Point2D.Double point = new Point2D.Double(101, 584);
        Point2D.Double result = null;
        int size = 35;
        double angle = scan.getAngle();
        

        File imageFile = new File(fileName);
        BufferedImage img = ImageIO.read(imageFile);

        Graphics2D graph = img.createGraphics();
        graph.setColor(Color.RED);
        graph.setStroke(new BasicStroke(2f));
        // graph.drawRect(100, 100, 10, 10);

        result = scan.affineTransform(scan.convertPdfToImageRelativeCoordinate(point), scan.getTransform(), scan.getAngle());
        graph.drawRect((int) (result.x), (int) (result.y), 100, 100);
        System.out.println(mm2px(pt2mm(8), 300));

        graph.dispose();

        ImageIO.write(img, "png", new File("img/template.png"));

    }

}
