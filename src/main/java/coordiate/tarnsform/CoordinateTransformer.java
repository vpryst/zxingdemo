package coordiate.tarnsform;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public interface CoordinateTransformer {

    int X = 0;
    int Y = 1;

    /**
     * Convert coordinates from PDF(pt) to Image(px)
     * 
     * @param src
     * @return array of point(x, y)
     */
    Point2D.Double convertPdfToImageCoordinate(Point2D.Double src);

    /**
     * Return relative coordinates from TopLeft marker
     * 
     * @param src
     * @return
     */
    Point2D.Double convertPdfToImageRelativeCoordinate(Point2D.Double src);

    /**
     * Provide transform coordinate by Affine Transform
     * 
     * @param src point coordinates before scan
     * @param center image shifted
     * @param angle image rotated
     * @return
     */
    Point2D.Double affineTransform(Point2D.Double src);

    double getAngle();

    Point2D.Double getTransform();
}
