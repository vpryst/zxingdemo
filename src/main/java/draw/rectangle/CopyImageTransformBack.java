package draw.rectangle;

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

    public CopyImageTransformBack(String fileName, int dpi, int pageSizePt) {
        scan = new FindMarkerAfterScanDemo(fileName, dpi, pageSizePt);
    }

    public BufferedImage transformRotateImage(BufferedImage img) {
        BufferedImage after = new BufferedImage(5000, 5000, BufferedImage.TYPE_INT_RGB);
        AffineTransform at = new AffineTransform();
        at.rotate(-scan.getAngle());
        at.translate(-scan.getTransform().x, -scan.getTransform().y);
        
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = op.filter(img, null);
        return after;
    }

    public BufferedImage getPartImage(BufferedImage img, double leftPt, double bottomPt, double rightPt, double topPt) {

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

        int w = (int) size.getX();// before.getWidth();
        int h = (int) size.getY();// before.getHeight();
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D aff = after.createGraphics();
        System.out.println(aff.drawImage(transformRotateImage(img), 0, 0, w, h, (int) pointLeftBottom.x, (int) pointLeftTop.y,
            (int) pointRightTop.x, (int) pointRightBottom.y, null));

        aff.dispose();
        return after;//transformRotateImage(img);//
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

        ImageIO.write(draw.getPartImage(img, 49.074997, 650.0, 545.925, 782.0), "png", new File("img/scaned_files/sub.png"));
        ImageIO.write(draw.getPartImage(img, 53.074997, 679.0, 61.074997, 687.0), "png", new File("img/scaned_files/sub1.png"));
        ImageIO.write(draw.getPartImage(img, 53.074997, 311.5, 61.074997, 319.5), "png", new File("img/scaned_files/sub2.png"));
    }

}
