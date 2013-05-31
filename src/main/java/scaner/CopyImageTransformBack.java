package scaner;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import coordiate.tarnsform.CoordinateTransformer;
import coordiate.tarnsform.FindMarkerAfterScanDemo;

public class CopyImageTransformBack {
    private CoordinateTransformer scan;

    /**
     * Constructor of class initialize variable scan
     * 
     * @param fileName - Name and absolute path to image file.
     * @param dpi - DPI of image.
     * @param pageSizePt - Page size set in Pt.
     */
    public CopyImageTransformBack(String fileName, int dpi, int pageSizePt) {
        scan = new FindMarkerAfterScanDemo(fileName, dpi, pageSizePt);
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
        at.translate(-scan.getTransform().x, -scan.getTransform().y);

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
    public BufferedImage findCoordinate(BufferedImage img, double leftPt, double bottomPt, double rightPt, double topPt) {

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
        BufferedImage after = new BufferedImage(dstW, dstH, BufferedImage.TYPE_INT_RGB);
        Graphics2D aff = after.createGraphics();
        System.out.println(aff.drawImage(transformRotateImage(img), 0, 0, dstW, dstH, srcLTX, srcLTY, srcRBX, srcRBY, null));

        aff.dispose();
        return after;
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String fileName = "img/scaned_files/second_page_foxit_rt5.png";
        CopyImageTransformBack draw = new CopyImageTransformBack(fileName, 300, 841);

        File imageFile = new File(fileName);

        BufferedImage img = ImageIO.read(imageFile);

        ImageIO.write(draw.findCoordinate(img, 49.074997, 650.0, 545.925, 782.0), "png", new File("img/scaned_files/sub.png"));
        ImageIO.write(draw.findCoordinate(img, 53.074997, 679.0, 61.074997, 687.0), "png", new File("img/scaned_files/sub1.png"));
        ImageIO.write(draw.findCoordinate(img, 53.074997, 311.5, 61.074997, 319.5), "png", new File("img/scaned_files/sub2.png"));
    }

}
