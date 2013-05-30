package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.mm2px;
import static coordiate.tarnsform.UnitConv.pt2mm;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import com.google.zxing.qrcode.detector.FinderMarkerDemo;

public class FindMarkerAfterScanDemo implements CoordinateTransformer {
    private int dpi;
    private int pageSizeHeight;
    private int[][] marker = new int[3][3];

    private FinderMarkerDemo finderMarker;

    private AffineTransform relative = new AffineTransform();

    /**
     * @param fileName - source file to take markers
     * @param dpiPx - set dpi of image
     * @param pageSizeHeightPt - Set size page in pt
     */
    public FindMarkerAfterScanDemo(String fileName, int dpiPx, int pageSizeHeightPt) {
        this.dpi = dpiPx;
        this.pageSizeHeight = pageSizeHeightPt;

        finderMarker = new FinderMarkerDemo(fileName);// "img/scaned_files/first_page.png"
        // BottomLeft
        marker[0][X] = (int) mm2px(pt2mm(36), dpi); // 150px
        marker[0][Y] = (int) mm2px(pt2mm(pageSizeHeight - 36), dpi); // 3354px
        // TopLeft
        marker[1][X] = (int) mm2px(pt2mm(36), dpi); // 150px
        marker[1][Y] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi); // 146px
        // TopRight
        marker[2][X] = (int) mm2px(pt2mm(559), dpi); // 2329
        marker[2][Y] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi); // 146px
    }

    // Find relative coordinate by pixel at image
    public Point2D.Double findRelativePx(int etalonXPx, int etalonYPx) {
        double relativeX = etalonXPx - marker[1][X];
        double relativeY = etalonYPx - marker[1][Y];
        Point2D transformCoordinate = returnPoint(relativeX, relativeY);
        return (Double) transformCoordinate;
    }

    // Find relative coordinate by point at image
    public Point2D.Double findRelativePt(int etalonXPt, int etalonYPt) {
        double relativeX = mm2px(pt2mm(etalonXPt), dpi) - marker[1][X];
        double relativeY = mm2px(pt2mm(pageSizeHeight - etalonYPt), dpi) - marker[1][Y];
        Point2D transformCoordinate = returnPoint(relativeX, relativeY);
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

    // find angle use marker
    public double findMarkerAngle() {
        Point p1 = new Point((int) (finderMarker.getBottomLeft().getX()), (int) (finderMarker.getBottomLeft().getY()));
        Point p2 = new Point((int) (finderMarker.getTopLeft().getX()), (int) (finderMarker.getTopLeft().getY()));
        return calcRotationAngleInDegrees(p1, p2) * Math.PI / 180;
    }

    public Point2D.Double returnPoint(double pX, double pY) {
        Point2D.Double pointBefore = new Point2D.Double(pX, pY);
        Point2D.Double pointAfter = (Double) relative.transform(pointBefore, null);
        return pointAfter;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        FindMarkerAfterScanDemo demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page_gs_ed_rt-15.jpg", 300, 841);

        Point2D.Double temp = demo.findRelativePx(421, 1071);
        System.out.println("Relative Cover bottom left X:" + temp.x + " Y:" + temp.y);
        temp = demo.findRelativePx(2054, 696);
        System.out.println("Relative Cover top right X:" + temp.x + " Y:" + temp.y);
        temp = demo.findRelativePt(480, 73);
        System.out.println("Relative footer bottom left X:" + temp.x + " Y:" + temp.y);
        temp = demo.findRelativePt(532, 126);
        System.out.println("Relative footer top right X:" + temp.x + " Y:" + temp.y);
    }

    @Override
    public Double convertPdfToImageCoordinate(Point2D.Double src) {
        double relativeX = mm2px(pt2mm(src.x), dpi);
        double relativeY = mm2px(pt2mm(pageSizeHeight - src.y), dpi);
        return new Point2D.Double(relativeX, relativeY);
    }

    @Override
    public Double affineTransform(Double src, Double center, double angle) {
        relative.setToTranslation(center.x, center.y);
        relative.rotate(angle);
        Point2D dst = relative.transform(src, null);
        return (Double) dst;
    }

    @Override
    public Double convertPdfToImageRelativeCoordinate(Double src) {
        double relativeX = convertPdfToImageCoordinate(src).x - marker[1][X];
        double relativeY = convertPdfToImageCoordinate(src).y - marker[1][Y];
        return new Point2D.Double(relativeX, relativeY);
    }

    @Override
    public double getAngle() {
        return findMarkerAngle();
    }

    @Override
    public Double getTransform() {
        Point2D.Double transform =
            new Double(finderMarker.getTopLeft().getX() - marker[1][X], finderMarker.getTopLeft().getY() - marker[1][Y]);
        return transform;
    }

}
