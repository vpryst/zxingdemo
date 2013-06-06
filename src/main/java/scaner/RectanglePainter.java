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
        Point2D.Double topLeft = new Point2D.Double(Math.round(leftPt), Math.round(topPt));
        Point2D.Double bottomRight = new Point2D.Double(Math.round(rightPt), Math.round(bottomPt));
        
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
        graph.setStroke(new BasicStroke(8f));

        graph.translate(scan.getCornerMarkerPx().x, scan.getCornerMarkerPx().y);
        graph.scale(scan.getScale(), scan.getScale());
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

        String fileName = "img/scaned_files/scaile/second_page_90_rt.jpg";
        int basePageHeigh = Math.round(PageSize.A4.getHeight());
        RectanglePainter draw =
            new RectanglePainter(fileName, pageDPI, basePageHeigh, marker[0], marker[1], marker[2], marker[3]);

        File imageFile = new File(fileName);

        BufferedImage img = ImageIO.read(imageFile);
        //leftPt, bottomPt, rightPt, topPt
        double [][] element = {
            {49.074997, 650.0, 545.925, 782.0},
            {53.074997, 679.0, 61.074997, 687.0},
            {53.074997, 657.0, 61.074997, 665.0},
            {49.074997, 240.0, 545.925, 632.0},
            {53.074997, 311.5, 61.074997, 319.5},
            {53.074997, 289.5, 61.074997, 297.5},
            {53.074997, 267.5, 61.074997, 275.5},
            {53.074997, 245.5, 61.074997, 253.5},
            {480, 73, 532, 126}
        };
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[0][0], element[0][1], element[0][2], element[0][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[1][0], element[1][1], element[1][2], element[1][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[2][0], element[2][1], element[2][2], element[2][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[3][0], element[3][1], element[3][2], element[3][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[4][0], element[4][1], element[4][2], element[4][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[5][0], element[5][1], element[5][2], element[5][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[6][0], element[6][1], element[6][2], element[6][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[7][0], element[7][1], element[7][2], element[7][3]));
        draw.drawRectangle(img, draw.calculateRectangleCoordinate(element[8][0], element[8][1], element[8][2], element[8][3]));
        

        ImageIO.write(img, "png", new File("img/scaned_files/template.png"));

        System.out.println("Finish");
    }

}
