package coordiate.tarnsform;

import java.awt.geom.Point2D;

public interface CoordinateTransformer {

	int X = 0;
	int Y = 1;
		
	/** 
	 * @param x
	 * @param y
	 * @return array of point(x, y)
	 */
	Point2D.Double convertPdfToImageCoordinate(double x, double y) ;
	
	
	/**
	 * @param src point coordinates before scan
	 * @param center image shifted
	 * @param angle image rotated
	 * @return
	 */
	Point2D.Double affineTransform(Point2D.Double src, Point2D.Double center, double angle);
	
}
