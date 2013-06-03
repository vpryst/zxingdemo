package scaner;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;

import org.apache.pdfbox.util.ImageIOUtil;

import com.google.zxing.qrcode.detector.FinderMarkerDemo;
import com.lowagie.text.Image;


public class FinderHiderQRCode {
    private CoordinateTransformer scan;

    public FinderHiderQRCode(String fileName, int dpi, int pageSizePt) {
        scan = new FindMarkerAfterScanDemo(fileName, dpi, pageSizePt);
    }

    public FinderHiderQRCode(BufferedImage img, int dpi, int pageSizePt) {
        scan = new FindMarkerAfterScanDemo(img, dpi, pageSizePt);
    }

    public BufferedImage findCoordinate(BufferedImage img, double leftPt, double bottomPt, double rightPt, double topPt) {

        Point2D.Double leftBottom = new Point2D.Double(leftPt, bottomPt);
        Point2D.Double leftTop = new Point2D.Double(leftPt, topPt);
        Point2D.Double rightTop = new Point2D.Double(rightPt, topPt);
        Point2D.Double rightBottom = new Point2D.Double(rightPt, bottomPt);

        Point2D.Double pointLeftBottom = scan.convertPdfToImageCoordinate(leftBottom);
        Point2D.Double pointLeftTop = scan.convertPdfToImageCoordinate(leftTop);
        Point2D.Double pointRightTop = scan.convertPdfToImageCoordinate(rightTop);
        Point2D.Double pointRightBottom = scan.convertPdfToImageCoordinate(rightBottom);
        Point2D.Double size =
            new Point2D.Double(pointRightBottom.getX() - pointLeftTop.getX(), pointRightBottom.getY() - pointLeftTop.getY());

        int w = (int) size.getX();
        int h = (int) size.getY();
        return getPartImage(img, 0, 0, w, h, (int) pointLeftBottom.x, (int) pointLeftTop.y, (int) pointRightTop.x, (int) pointRightBottom.y);
    }

    /**
     * Extract SubImage of image
     * 
     * @param img - source image
     * @param dstX - destination X
     * @param dstY - destination Y
     * @param dstW - destination width
     * @param dstH - destination height
     * @param srcLTX - source left top X coordinate
     * @param srcLTY - source left top Y coordinate
     * @param srcRBX - source bottom right X
     * @param srcRBY - source bottom right Y
     * @return - BufferedImage
     */
    public BufferedImage getPartImage(BufferedImage img, int dstX, int dstY, int dstW, int dstH, int srcLTX, int srcLTY, int srcRBX,
        int srcRBY) {

        Graphics2D aff = img.createGraphics();
        aff.setBackground(Color.BLACK);
        aff.setColor(Color.WHITE);
        aff.translate(scan.getTransform().x, scan.getTransform().y);
        aff.rotate(scan.getAngle());
        // System.out.println(aff.drawImage(img, 0, 0, dstW, dstH, srcLTX, srcLTY, srcRBX, srcRBY, null));
        // aff.drawRect(0, 0, 150, 150);
        aff.clearRect(-20, -20, 150, 150);
        aff.dispose();
        return img;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String fileName = "img/scaned_files/second_page_foxit.png";
        FinderHiderQRCode demo = new FinderHiderQRCode(fileName, 300, 841);

        File imageFile = new File(fileName);

        BufferedImage img = ImageIO.read(imageFile);

        // ImageIO.write(demo.findCoordinate(img, 480.55, 73.69998, 532.85, 126.0), "png", new File("img/scaned_files/sub3.png"));
        BufferedImage imgChenged = demo.findCoordinate(img, 0, 0, 100, 100);
        //ImageIO.write(imgChenged, "png", new File("img/scaned_files/sub4.png"));
        
        ImageIOUtil.writeImage(imgChenged, "png", "img/scaned_files/sub4", BufferedImage.TYPE_BYTE_GRAY, 300);
        
        System.out.println(imgChenged.getHeight());
        System.out.println(imgChenged.getWidth());
        
        FinderMarkerDemo dd = new FinderMarkerDemo(imgChenged);
        System.out.println(dd.getBottomLeft());
        System.out.println(dd.getTopLeft());
        System.out.println(dd.getTopRight());

        demo = new FinderHiderQRCode(imgChenged, 300, 841);
        // CopyImageTransformBack getImage = new CopyImageTransformBack(imgChenged, 300, 841);

        DrawRectangle draw = new DrawRectangle(imgChenged, 300, 841);

        draw.findRectangleCoordinate(imgChenged, 49.074997, 650.0, 545.925, 782.0);
        draw.findRectangleCoordinate(imgChenged, 53.074997, 679.0, 61.074997, 687.0);
        draw.findRectangleCoordinate(imgChenged, 53.074997, 657.0, 61.074997, 665.0);

        draw.findRectangleCoordinate(imgChenged, 49.074997, 240.0, 545.925, 632.0);
        draw.findRectangleCoordinate(imgChenged, 53.074997, 311.5, 61.074997, 319.5);
        draw.findRectangleCoordinate(imgChenged, 53.074997, 289.5, 61.074997, 297.5);
        draw.findRectangleCoordinate(imgChenged, 53.074997, 267.5, 61.074997, 275.5);
        draw.findRectangleCoordinate(imgChenged, 53.074997, 245.5, 61.074997, 253.5);

        draw.findRectangleCoordinate(imgChenged, 480, 73, 532, 126);

        ImageIO.write(imgChenged, "png", new File("img/scaned_files/template.png"));

        // ImageIO.write(getImage.findCoordinate(img, 53.074997, 311.5, 61.074997, 319.5), "png", new File("img/scaned_files/sub5.png"));
    }

}
