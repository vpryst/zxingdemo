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
            // TODO Auto-generated catch block
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
    public void calculateRectangleCoordinate(BufferedImage img, double leftPt, double bottomPt, double rightPt, double topPt) {
        Point2D.Double topLeft = new Point2D.Double(leftPt, topPt);
        Point2D.Double bottomRight = new Point2D.Double(rightPt, bottomPt);

        Point2D.Double pointLeftTop = scan.convertPdfToImageRelativeCoordinate(topLeft);
        Point2D.Double pointRightBottom = scan.convertPdfToImageRelativeCoordinate(bottomRight);
        Point2D.Double size = new Point2D.Double(pointRightBottom.x - pointLeftTop.x, pointRightBottom.y - pointLeftTop.y);
        drawRectangle(pointLeftTop, size, img);
    }

    /**
     * Draw rectangle on image
     * 
     * @param XY - Left Top relative position on image
     * @param size - rectangle size
     * @param img - Image to draw rectangles
     */
    public void drawRectangle(Point2D.Double XY, Point2D.Double size, BufferedImage img) {
        Graphics2D graph = img.createGraphics();
        graph.setColor(Color.RED);
        graph.setStroke(new BasicStroke(4f));

        graph.translate(scan.getCornerMarkerPx().x, scan.getCornerMarkerPx().y);
        graph.rotate(scan.getAngle());

        graph.drawRect((int) Math.round(XY.x), (int) Math.round(XY.y), (int) Math.round(size.x), (int) Math.round(size.y));
        graph.dispose();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int pageDPI = 300;
        String fileName = "img/scaned_files/sc/second_page9.jpg";
        RectanglePainter draw = new RectanglePainter(fileName, pageDPI, Math.round(PageSize.A4.getHeight()), 36, 36, 559, 806);

        File imageFile = new File(fileName);

        BufferedImage img = ImageIO.read(imageFile);

        draw.calculateRectangleCoordinate(img, 49.074997, 650.0, 545.925, 782.0);
        draw.calculateRectangleCoordinate(img, 53.074997, 679.0, 61.074997, 687.0);
        draw.calculateRectangleCoordinate(img, 53.074997, 657.0, 61.074997, 665.0);

        draw.calculateRectangleCoordinate(img, 49.074997, 240.0, 545.925, 632.0);
        draw.calculateRectangleCoordinate(img, 53.074997, 311.5, 61.074997, 319.5);
        draw.calculateRectangleCoordinate(img, 53.074997, 289.5, 61.074997, 297.5);
        draw.calculateRectangleCoordinate(img, 53.074997, 267.5, 61.074997, 275.5);
        draw.calculateRectangleCoordinate(img, 53.074997, 245.5, 61.074997, 253.5);

        draw.calculateRectangleCoordinate(img, 480, 73, 532, 126);

        ImageIO.write(img, "png", new File("img/scaned_files/template.png"));

        System.out.println("Finish");
    }

}
