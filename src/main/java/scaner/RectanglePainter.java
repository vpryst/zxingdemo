package scaner;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lowagie.text.PageSize;

public class RectanglePainter {

    private CoordinateTransformer scan;

    /**
     * Constructor of class initialize variable scan
     * 
     * @param fileName - Name and absolute path to image file.
     * @param dpi - DPI of image.
     * @param pageSizePt - Page size set in Pt.
     */
    public RectanglePainter(String fileName, int dpi, int pageSizePt, int left, int bottom, int top, int right) {
        try {
            scan = new CalculaterScanedCoordinate(fileName, dpi, pageSizePt, left, bottom, top, right);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RectanglePainter(BufferedImage img, int dpi, int pageSizePt, int left, int bottom, int top, int right) {
        scan = new CalculaterScanedCoordinate(img, dpi, pageSizePt, left, bottom, top, right);
    }

    /**
     * Find relative coordinates on image
     * 
     * @param img - source image for find coordinates
     * @param leftPt - Left position in Pt
     * @param bottomPt - Bottom position in Pt
     * @param rightPt - Right position in Pt
     * @param topPt - Top position in Pt
     */
    public Point2D.Double[] calculateRectangleCoordinate(double leftPt, double bottomPt, double rightPt, double topPt) {
        Point2D.Double topLeft = new Point2D.Double(leftPt, topPt);
        Point2D.Double bottomRight = new Point2D.Double(rightPt, bottomPt);

        Point2D.Double pointLeftTop = scan.convertPdfToImageRelativeCoordinate(topLeft);
        Point2D.Double pointRightBottom = scan.convertPdfToImageRelativeCoordinate(bottomRight);
        Point2D.Double size = new Point2D.Double(pointRightBottom.x - pointLeftTop.x, pointRightBottom.y - pointLeftTop.y);
        return new Point2D.Double[] {pointLeftTop, size};
    }

    /**
     * Draw rectangle on image
     * 
     * @param XY - Left Top relative position on image
     * @param size - rectangle size
     * @param img - Image to draw rectangles
     */
    public void drawRectangle(BufferedImage img, Point2D.Double[] point) {
        Graphics2D graph = img.createGraphics();
        graph.setColor(Color.RED);
        graph.setStroke(new BasicStroke(4f));

        graph.translate(scan.getCornerMarkerPx().x, scan.getCornerMarkerPx().y);
        graph.rotate(scan.getAngle());

        graph.drawRect((int) Math.round(point[0].x), (int) Math.round(point[0].y), (int) Math.round(point[1].x), (int) Math.round(point[1].y));
        graph.dispose();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int pageDPI = 300;
        int[] marker = new int[4];
        // left
        marker[0] = 36;
        // bottom
        marker[1] = 36;
        // top
        marker[2] = 559;
        // right
        marker[3] = 806;

        String fileName = "img/scaned_files/sc/second_page9.jpg";
        RectanglePainter draw =
            new RectanglePainter(fileName, pageDPI, Math.round(PageSize.A4.getHeight()), marker[0], marker[1], marker[2], marker[3]);

        File imageFile = new File(fileName);

        BufferedImage img = ImageIO.read(imageFile);

        double[] element = new double[4];
        //leftPt 
        element[0] = 49.074997;
        //bottomPt
        element[1] = 650.0;
        //rightPt
        element[2] = 545.925;
        //topPt
        element[3] = 782.0;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));
        
      //leftPt 
        element[0] = 53.074997;
        //bottomPt
        element[1] = 679.0;
        //rightPt
        element[2] = 61.074997;
        //topPt
        element[3] = 687.0;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));
      //leftPt 
        element[0] = 53.074997;
        //bottomPt
        element[1] = 657.0;
        //rightPt
        element[2] = 61.074997;
        //topPt
        element[3] = 665.0;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));

      //leftPt 
        element[0] = 49.074997;
        //bottomPt
        element[1] = 240.0;
        //rightPt
        element[2] = 545.925;
        //topPt
        element[3] = 632.0;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));
      //leftPt 
        element[0] = 53.074997;
        //bottomPt
        element[1] = 311.5;
        //rightPt
        element[2] = 61.074997;
        //topPt
        element[3] = 319.5;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));
      //leftPt 
        element[0] = 53.074997;
        //bottomPt
        element[1] = 289.5;
        //rightPt
        element[2] = 61.074997;
        //topPt
        element[3] = 297.5;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));
      //leftPt 
        element[0] = 53.074997;
        //bottomPt
        element[1] = 267.5;
        //rightPt
        element[2] = 61.074997;
        //topPt
        element[3] = 275.5;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));
      //leftPt 
        element[0] = 53.074997;
        //bottomPt
        element[1] = 245.5;
        //rightPt
        element[2] = 61.074997;
        //topPt
        element[3] = 253.5;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));

      //leftPt 
        element[0] = 480;
        //bottomPt
        element[1] = 73;
        //rightPt
        element[2] = 532;
        //topPt
        element[3] = 126;
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0], element[1], element[2], element[3]));

        ImageIO.write(img, "png", new File("img/scaned_files/template.png"));

        System.out.println("Finish");
    }

}
