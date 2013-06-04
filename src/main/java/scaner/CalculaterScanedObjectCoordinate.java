package scaner;

import static scaner.UnitConv.mm2px;
import static scaner.UnitConv.pt2mm;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;

import com.google.zxing.qrcode.detector.FinderMarkerDemo;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;

public class CalculaterScanedObjectCoordinate implements CoordinateTransformer {
	private static final int AXIS_AMOUNT = 2; // X, Y 
	private int dpi;
    private float pageSizeHeight;
    
    /**
     * marker[1] is a corner 
     */
    private double[][] marker = new double[3][AXIS_AMOUNT];

    private FinderMarkerDemo finderMarker;

    private AffineTransform relative = new AffineTransform();
	
    private double left;
	private double bottom;
	private double top;
	private double right;

    /**
     * @param fileName - source file to take markers
     * @param dpiPx - set dpi of image
     * @param pageSizeHeightPt - Set size page in pt
     */
    public CalculaterScanedObjectCoordinate(String fileName, int dpiPx, int pageSizeHeightPt) {
    	initDefaultMarkersCoordinates();
        finderMarker = new FinderMarkerDemo(fileName);// "img/scaned_files/first_page.png"
        setDpi(dpiPx);
        setPageHeight(pageSizeHeightPt);
        
        calculateMarkerPositionPt2Px();
        
        relative.setToTranslation(getCornerMarkerPx().x, getCornerMarkerPx().y);
        relative.rotate(getAngle());
    }

    public CalculaterScanedObjectCoordinate(BufferedImage img, int dpiPx, int pageSizeHeightPt) {
	    finderMarker = new FinderMarkerDemo(img);
	    setDpi(dpiPx);
        setPageHeight(pageSizeHeightPt);
        
	    calculateMarkerPositionPt2Px();
	    
	    relative.setToTranslation(getCornerMarkerPx().x, getCornerMarkerPx().y);
	    relative.rotate(getAngle());
	}

    /**
     * Size insert in Pt
     * @param f
     */
	public void setPageHeight(float f) {
    	this.pageSizeHeight = f;
		 
	}
	/**
	 * Insert in Px
	 * @param dpiPx
	 */
	public void setDpi(int dpiPx) {
		this.dpi = dpiPx;
		
	}

	private void initDefaultMarkersCoordinates() {
     left = 36 ;
     bottom = 36 ;
     top = 806;
     right = 559;
		
	}

	public void calculateMarkerPositionPt2Px() {
        // BottomLeft
        marker[0][X] = mm2px(pt2mm(left), dpi);
        marker[0][Y] = mm2px(pt2mm(pageSizeHeight - bottom), dpi); 
        // TopLeft
        marker[1][X] = mm2px(pt2mm(left), dpi);
        marker[1][Y] = mm2px(pt2mm(pageSizeHeight - top), dpi);
        // TopRight
        marker[2][X] = mm2px(pt2mm(right), dpi);
        marker[2][Y] = mm2px(pt2mm(pageSizeHeight - top), dpi);
    }

    /** 
     * Find relative coordinate by pixel at image
     * @param etalonXPx
     * @param etalonYPx
     * @return
     */
    public Point2D.Double calculateRelativeCoordinateMarkerObjectPx(int etalonXPx, int etalonYPx) {
        double relativeX = etalonXPx - marker[1][X];
        double relativeY = etalonYPx - marker[1][Y];
        Point2D transformCoordinate = transformedCoordinatePoint(relativeX, relativeY);
        return (Double) transformCoordinate;
    }

     /**
      * Find relative coordinate by point at image
      * @param etalonXPt
      * @param etalonYPt
      * @return
      */
    public Point2D.Double calculateRelativeCoordinateMarkerObjectPt(int etalonXPt, int etalonYPt) {
        double relativeX = mm2px(pt2mm(etalonXPt), dpi) - marker[1][X];
        double relativeY = mm2px(pt2mm(pageSizeHeight - etalonYPt), dpi) - marker[1][Y];
        Point2D transformCoordinate = transformedCoordinatePoint(relativeX, relativeY);
        return (Double) transformCoordinate;
    }

    /**
     * Calculates the angle from centerPt to targetPt in degrees. The return should range from [0,360), rotating CLOCKWISE, 0 and 360
     * degrees represents NORTH, 90 degrees represents EAST, etc... Assumes all points are in the same coordinate space. If they are not,
     * you will need to call SwingUtilities.convertPointToScreen or equivalent on all arguments before passing them to this function.
     * 
     * @param centerPt Point we are rotating around.
     * @param targetPt Point we want to calcuate the angle to.
     * @return angle in degrees. This is the angle from centerPt to targetPt.
     */
    public static double calcRotationAngleInDegrees(Point centerPt, Point targetPt) {
        // calculate the angle theta from the deltaY and deltaX values
        // (atan2 returns radians values from [-PI,PI])
        // 0 currently points EAST.
        // NOTE: By preserving Y and X param order to atan2, we are expecting
        // a CLOCKWISE angle direction.
        double theta = Math.atan2(targetPt.y - centerPt.y, targetPt.x - centerPt.x);

        // rotate the theta angle clockwise by 90 degrees
        // (this makes 0 point NORTH)
        // NOTE: adding to an angle rotates it clockwise.
        // subtracting would rotate it counter-clockwise
        theta += Math.PI / 2.0;

        // convert from radians to degrees
        // this will give you an angle from [0->270],[-180,0]
        double angle = Math.toDegrees(theta);

        // convert to positive range [0-360)
        // since we want to prevent negative angles, adjust them now.
        // we can assume that atan2 will not return a negative value
        // greater than one partial rotation
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    /**
     * Transform input coordinate relative AffineTransform
     * @param pX - Etalone coordinate
     * @param pY - Etalone coordinate
     * @return - Transformed coordinate
     */
    public Point2D.Double transformedCoordinatePoint(double pX, double pY) {
        Point2D.Double pointBefore = new Point2D.Double(pX, pY);
        Point2D.Double pointAfter = (Double) relative.transform(pointBefore, null);
        return pointAfter;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        final int pageDPI = 300;
		CalculaterScanedObjectCoordinate demo = new CalculaterScanedObjectCoordinate("img/scaned_files/first_page_gs_ed_rt15.png", pageDPI, Math.round(PageSize.A4.getHeight()));
		
        Point2D.Double src = new Point2D.Double(101, 584);
//        Point2D.Double temp = demo.affineTransform(demo.convertPdfToImageRelativeCoordinate(src));
        Point2D.Double result = demo.transform(src);
        
        
        
//        System.out.println("Relative Cover bottom left X:" + temp.x + " Y:" + temp.y);
//        temp = demo.findRelativePx(2054, 696);
//        System.out.println("Relative Cover top right X:" + temp.x + " Y:" + temp.y);
//        temp = demo.findRelativePt(480, 73);
//        System.out.println("Relative footer bottom left X:" + temp.x + " Y:" + temp.y);
//        temp = demo.findRelativePt(532, 126);
//        System.out.println("Relative footer top right X:" + temp.x + " Y:" + temp.y);
    }
    /**
     * Return calculated object relative marker coordinate on image.
     */
    @Override
    public Double transform(Double src) {
		return affineTransform(convertPdfToImageRelativeCoordinate(src));
	}
    /**
     * Return calculated object coordinate on image.
     */
	@Override
    public Point2D.Double convertPdfToImageCoordinate(Point2D.Double src) {
        double relativeX = mm2px(pt2mm(src.x), dpi);
        double relativeY = mm2px(pt2mm(pageSizeHeight - src.y), dpi);
        return new Point2D.Double(relativeX, relativeY);
    }

    @Override
    public Point2D.Double affineTransform(Point2D.Double src) {
        Point2D dst = relative.transform(src, null);
        return (Double) dst;
    }
    
    /**
     * convert Object coordinate in Pt to relative marker coordinate in Px  
     */
    @Override
    public Point2D.Double convertPdfToImageRelativeCoordinate(Point2D.Double src) {
        double relativeX = convertPdfToImageCoordinate(src).x - marker[1][X];
        double relativeY = convertPdfToImageCoordinate(src).y - marker[1][Y];
        return new Point2D.Double(relativeX, relativeY);
    }

    /**
     * find angle use BottomLeft and TopLeft markers
     */
    @Override
    public double getAngle() {
        Point p1 = new Point(Math.round(finderMarker.getBottomLeft().getX()), Math.round(finderMarker.getBottomLeft().getY()));
        Point p2 = new Point(Math.round(finderMarker.getTopLeft().getX()), Math.round(finderMarker.getTopLeft().getY()));
        return calcRotationAngleInDegrees(p1, p2) * Math.PI / 180;
    }

    @Override
    public Point2D.Double getCornerMarkerPx() {
        Point2D.Double transform = new Double(finderMarker.getTopLeft().getX(), finderMarker.getTopLeft().getY());
        return transform;
    }
}
