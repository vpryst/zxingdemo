package scaner;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class CopyImage {
    private CoordinateTransformer scan;
    
    /**
     * Constructor initialize variable scan
     * @param fileName - source image
     * @param dpi - DPI of image
     * @param pageSizePt - page size image
     */
    public CopyImage(String fileName, int dpi, int pageSizePt) {
        scan = new FindMarkerAfterScanDemo(fileName, dpi, pageSizePt);
    }

    /**
     * 
     * @param img
     * @param leftPt
     * @param bottomPt
     * @param rightPt
     * @param topPt
     * @return
     */
    public BufferedImage getPartImage(BufferedImage img, double leftPt, double bottomPt, double rightPt, double topPt) {

        Point2D.Double leftBottom = new Point2D.Double(leftPt, bottomPt);
        Point2D.Double leftTop = new Point2D.Double(leftPt, topPt);
        Point2D.Double rightTop = new Point2D.Double(rightPt, topPt);
        Point2D.Double rightBottom = new Point2D.Double(rightPt, bottomPt);

        Point2D.Double pointLeftBottom = scan.affineTransform(scan.convertPdfToImageRelativeCoordinate(leftBottom));
        Point2D.Double pointLeftTop = scan.affineTransform(scan.convertPdfToImageRelativeCoordinate(leftTop));
        Point2D.Double pointRightTop = scan.affineTransform(scan.convertPdfToImageRelativeCoordinate(rightTop));
        Point2D.Double pointRightBottom = scan.affineTransform(scan.convertPdfToImageRelativeCoordinate(rightBottom));
        Point2D.Double size =
            new Point2D.Double(pointRightTop.getX() - pointLeftBottom.getX(), pointRightBottom.getY() - pointLeftTop.getY());

        int w = (int) size.getX();// before.getWidth();
        int h = (int) size.getY();// before.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D aff = after.createGraphics();
        System.out.println(aff.drawImage(img, 0, 0, w, h, (int) pointLeftBottom.x, (int) pointLeftTop.y, (int) pointRightTop.x,
            (int) pointRightBottom.y, null));
        aff.dispose();
        return after;
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String fileName = "img/scaned_files/second_page_foxit_rt5.png";
        CopyImage draw = new CopyImage(fileName, 300, 841);

        File imageFile = new File(fileName);

        BufferedImage img = ImageIO.read(imageFile);

        ImageIO.write(draw.getPartImage(img, 49.074997, 650.0, 545.925, 782.0), "png", new File("img/scaned_files/sub.png"));
        ImageIO.write(draw.getPartImage(img, 53.074997, 679.0, 61.074997, 687.0), "png", new File("img/scaned_files/sub1.png"));
        ImageIO.write(draw.getPartImage(img, 53.074997, 311.5, 61.074997, 319.5), "png", new File("img/scaned_files/sub2.png"));

    }

}
