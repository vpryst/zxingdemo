package scaner;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.lowagie.text.PageSize;

public class SaverSubImage {
    private CoordinateTransformer scan;

    /**
     * Constructor of class initialize variable scan
     * 
     * @param fileName - Name and absolute path to image file.
     * @param dpi - DPI of image.
     * @param pageSizePt - Page size set in Pt.
     */
    public SaverSubImage(String fileName, int dpi, int pageSizePt, int left, int bottom, int top, int right) {
        try {
            scan = new CalculaterScanedCoordinate(fileName, dpi, pageSizePt, left, bottom, top, right);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SaverSubImage(BufferedImage img, int dpi, int pageSizePt, int left, int bottom, int top, int right) {
        scan = new CalculaterScanedCoordinate(img, dpi, pageSizePt, left, bottom, top, right);
    }

    /**
     * This method rotate and transform image
     * 
     * @param img - BufferedImage
     * @return - transformed BufferedImage
     */
    public BufferedImage transformRotateImage(BufferedImage img) {
        BufferedImage after = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        AffineTransform at = new AffineTransform();
        at.rotate(-scan.getAngle());
        at.scale(1/scan.getScale(), 1/scan.getScale());
        at.translate(-scan.getCornerMarkerPx().x, -scan.getCornerMarkerPx().y);
        

        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = op.filter(img, null);
        return after;
    }

    /**
     * Extract SubImage of image
     * 
     * @param img - BufferedImage
     * @param leftPt - Left side position in Pt
     * @param bottomPt - Bottom side position in Pt
     * @param rightPt - Right side position in Pt
     * @param topPt - Top side position in Pt
     * @return SubImage by provided coordinate
     */
    public Point2D.Double[] findCoordinate(BufferedImage img, double leftPt, double bottomPt, double rightPt, double topPt) {

        Point2D.Double leftBottom = new Point2D.Double(leftPt, bottomPt);
        Point2D.Double leftTop = new Point2D.Double(leftPt, topPt);
        Point2D.Double rightTop = new Point2D.Double(rightPt, topPt);
        Point2D.Double rightBottom = new Point2D.Double(rightPt, bottomPt);

        Point2D.Double pointLeftBottom = scan.convertPdfToImageRelativeCoordinate(leftBottom);
        Point2D.Double pointLeftTop = scan.convertPdfToImageRelativeCoordinate(leftTop);
        Point2D.Double pointRightTop = scan.convertPdfToImageRelativeCoordinate(rightTop);
        Point2D.Double pointRightBottom = scan.convertPdfToImageRelativeCoordinate(rightBottom);
        Point2D.Double size =
            new Point2D.Double(pointRightTop.getX() - pointLeftBottom.getX(), pointRightBottom.getY() - pointLeftTop.getY());

        pointLeftTop = new Point2D.Double(pointLeftBottom.x, pointLeftTop.y);
        pointRightBottom = new Point2D.Double(pointRightTop.x, pointRightBottom.y);

        return new Point2D.Double[]{size, pointLeftTop, pointRightBottom};
        // getSubImage(img, 0, 0, w, h, (int) pointLeftBottom.x, (int) pointLeftTop.y, (int) pointRightTop.x, (int) pointRightBottom.y);
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
    public BufferedImage getSubImage(BufferedImage img, Point2D.Double[] coordinate) {
        BufferedImage after =
            new BufferedImage((int) Math.round(coordinate[0].x), (int) Math.round(coordinate[0].y), BufferedImage.TYPE_INT_RGB);
        Graphics2D aff = after.createGraphics();
        System.out.println(aff.drawImage(transformRotateImage(img), 0, 0, (int) Math.round(coordinate[0].x),
            (int) Math.round(coordinate[0].y), (int) Math.round(coordinate[1].x), (int) Math.round(coordinate[1].y),
            (int) Math.round(coordinate[2].x), (int) Math.round(coordinate[2].y), null));
        aff.dispose();
        return after;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int pageDPI = 300;
        String fileName = "img/scaned_files/scaile/second_page_90_rt1.jpg";
        SaverSubImage draw = new SaverSubImage(fileName, pageDPI, Math.round(PageSize.A4.getHeight()), 36, 36, 559, 806);

        File imageFile = new File(fileName);

        BufferedImage img = ImageIO.read(imageFile);

        ImageIO.write(draw.getSubImage(img, draw.findCoordinate(img, 49.074997, 650.0, 545.925, 782.0)), "png", new File(
            "img/scaned_files/sub.png"));

        ImageIO.write(draw.getSubImage(img, draw.findCoordinate(img, 53.074997, 679.0, 61.074997, 687.0)), "png", new File(
            "img/scaned_files/sub1.png"));
        ImageIO.write(draw.getSubImage(img, draw.findCoordinate(img, 53.074997, 311.5, 61.074997, 319.5)), "png", new File(
            "img/scaned_files/sub2.png"));
        ImageIO.write(draw.getSubImage(img, draw.findCoordinate(img, 480.55, 73.69998, 532.85, 126.0)), "png", new File(
            "img/scaned_files/sub3.png"));

    }

}
