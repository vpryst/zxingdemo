package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.*;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;

import org.jfree.util.Rotation;

import com.google.zxing.qrcode.detector.FinderMarkerDemo;

public class FindMarkerAfterScanDemo {
    private int dpi;
    private int pageSizeHeight;
    private int[][] marker = new int[3][3];

    private FinderMarkerDemo finderMarker;

    private AffineTransform relative = new AffineTransform();
    private int relativeX;
    private int relativeY;

    public FindMarkerAfterScanDemo(String fileName, int dpiPx, int pageSizeHeightPt) {
        this.dpi = dpiPx;
        this.pageSizeHeight = pageSizeHeightPt;
        finderMarker = new FinderMarkerDemo(fileName);// "img/scaned_files/first_page.png"
        marker[0][0] = (int) mm2px(pt2mm(36), dpi); // 150px
        marker[0][1] = (int) mm2px(pt2mm(36), dpi); // 150px
        marker[0][2] = (int) mm2px(pt2mm(559), dpi); // 2329
        marker[1][0] = (int) mm2px(pt2mm(pageSizeHeight - 36), dpi); // 3354px
        marker[1][1] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi); // 146px
        marker[1][2] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi); // 146px

        Point p1 = new Point((int) (finderMarker.getBottomLeft().getX()), (int) (finderMarker.getBottomLeft().getY()));
        Point p2 = new Point((int) (finderMarker.getTopLeft().getX()), (int) (finderMarker.getTopLeft().getY()));
        relative.rotate(calcRotationAngleInDegrees(p1, p2)*Math.PI/180);
        System.out.println(calcRotationAngleInDegrees(p1, p2)*Math.PI/180);
        
        /*Point p3 = new Point(marker[0][1], marker[1][1]);
        Point p4 = new Point((int) (finderMarker.getTopLeft().getX()), (int)(finderMarker.getTopLeft().getY()));
        relative.transform(p3, p2);*/
        
        if (calcRotationAngleInDegrees(p1, p2) <= 180) {
            relative.setToTranslation(finderMarker.getTopLeft().getX() - marker[0][1], finderMarker.getTopLeft().getY() - marker[1][1]);
        } else {
            relative.setToTranslation(finderMarker.getTopLeft().getX() - marker[0][1], finderMarker.getTopLeft().getY() - marker[1][1]);
        }
        //System.out.println(relative.transform(p3, p2));
        
        System.out.println(relative.getTranslateX());
        System.out.println(relative.getTranslateY());
        
        System.out.println(finderMarker.getTopLeft().getX() - marker[0][1]);
        System.out.println(finderMarker.getTopLeft().getY() - marker[1][1]);
    }

    public int[] findRelativePx(int etalonXPx, int etalonYPx) {
        relativeX = (int) (etalonXPx + relative.getTranslateX());
        relativeY = (int) (etalonYPx + relative.getTranslateY());
        return new int[]{relativeX, relativeY};
    }

    public int[] findRelativePt(int etalonXPt, int etalonYPt) {
        relativeX = (int) (mm2px(pt2mm(etalonXPt), dpi) + relative.getShearX());
        relativeY = (int) (mm2px(pt2mm(pageSizeHeight - etalonYPt), dpi) + relative.getShearY());
        return new int[]{relativeX, relativeY};
    }

    public void afintr() {
        AffineTransform temp = new AffineTransform();
        temp.rotate(0.5);

        System.out.println(temp.getShearX());
        System.out.println(temp.getShearY());
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

    public double getShaer(int etalonX, int etalonY, int realX, int realY) {

        return 1.0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        FindMarkerAfterScanDemo demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page_gs_ed_rt15.jpg", 300, 841);
        int[] temp = demo.findRelativePx(421, 1071);
        System.out.println("Relative Cover bottom left X:" + temp[0] + " Y:" + temp[1]);
        temp = demo.findRelativePx(2054, 696);
        System.out.println("Relative Cover top right X:" + temp[0] + " Y:" + temp[1]);
        temp = demo.findRelativePt(480, 73);
        System.out.println("Relative footer bottom left X:" + temp[0] + " Y:" + temp[1]);
        temp = demo.findRelativePt(532, 126);
        System.out.println("Relative footer top right X:" + temp[0] + " Y:" + temp[1]);

        
        
        Point p1 = new Point(1031, 163);
        Point p2 = new Point(3133, 729);
  System.out.println(FindMarkerAfterScanDemo.calcRotationAngleInDegrees(p1, p2));      
        
        // demo = new FindMarkerAfterScanDemo("img/scaned_files/second_page_.png", 300, 841);
        // System.out.println("Relative singleChoice bottom left X:" + demo.findRelativePt(53, 679)[0] + " Y:" + demo.findRelativePt(53,
        // 679)[1]);
        // System.out.println("Relative singleChoice top right X:" + demo.findRelativePt(61, 687)[0] + " Y:" + demo.findRelativePt(61,
        // 687)[1]);

        // demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page.png", 300, 841);
        // System.out.println("Relative Cover bottom left X:" + demo.findRelativePt(421, 1071)[0] + " Y:" + demo.findRelativePt(421,
        // 1071)[1]);
        // System.out.println("Relative Cover top right X:" + demo.findRelativePt(2054, 696)[0] + " Y:" + demo.findRelativePt(2054,
        // 696)[1]);
        //
        // demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page.png", 300, 841);
        // System.out.println("Relative Cover bottom left X:" + demo.findRelativePt(421, 1071)[0] + " Y:" + demo.findRelativePt(421,
        // 1071)[1]);
        // System.out.println("Relative Cover top right X:" + demo.findRelativePt(2054, 696)[0] + " Y:" + demo.findRelativePt(2054,
        // 696)[1]);
        //
        // demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page.png", 300, 841);
        // System.out.println("Relative Cover bottom left X:" + demo.findRelativePt(421, 1071)[0] + " Y:" + demo.findRelativePt(421,
        // 1071)[1]);
        // System.out.println("Relative Cover top right X:" + demo.findRelativePt(2054, 696)[0] + " Y:" + demo.findRelativePt(2054,
        // 696)[1]);
    }

}
