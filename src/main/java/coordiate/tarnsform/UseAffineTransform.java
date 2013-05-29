package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.mm2px;
import static coordiate.tarnsform.UnitConv.pt2mm;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;

public class UseAffineTransform {
    private int dpi;
    private int pageSizeHeight;
    private int[][] marker = new int[3][3];
    /**
     * @param args
     */
    public static void main(String[] args) {
        Magic magic = new Magic();
        magic.ttt();

    }
    
    public void ttt() {
        this.dpi = 300;
        this.pageSizeHeight = 841;

        // pt - px
        marker[0][0] = (int) mm2px(pt2mm(36), dpi); // 150px
        marker[0][1] = (int) mm2px(pt2mm(36), dpi); // 150px
        marker[0][2] = (int) mm2px(pt2mm(559), dpi); // 2329
        marker[1][0] = (int) mm2px(pt2mm(pageSizeHeight - 36), dpi); // 3354px
        marker[1][1] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi); // 146px
        marker[1][2] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi); // 146px
        
        
        System.out.println(Arrays.toString(marker[0]));
        System.out.println(Arrays.toString(marker[1]));
        
        
        // get point coords PDF to Img 
        int[] p = transformCoordPDFToImg(101, 584);
        System.out.println("Point = " + Arrays.toString(p));
        
        // relative coord to top left marker
        int[] p_new = {p[0] - marker[0][1], p[1] - marker[1][1]};
        System.out.println("New Rel Point = " + Arrays.toString(p_new));
        
        

//      int [] m = {1807, 202};
      int [] m = {marker[0][1], marker[1][1]};
      System.out.println(">>Marker=  "+ Arrays.toString(m));

        Point2D pp =  affinetr(m, 1753, 0, Math.PI / 6);
        
        System.out.println();
        System.out.println("Super New" + pp);
    }
    public Point2D affinetr(int [] m, int centerX, int centerY, double angle) {
        AffineTransform rat = new AffineTransform();       
        rat.setToTranslation(1753, 0); // set shift
        rat.rotate(Math.PI / 6); // set angle
        Double superNewPoint = new Point2D.Double(m[0], m[1]);
        Point2D p = rat.transform(superNewPoint, null);
        
        return p;
        
        
    }
    
    public int[] transformCoordPDFToImg(int etalonXPt, int etalonYPt) {
        int  relativeX = (int) (mm2px(pt2mm(etalonXPt), dpi) );
        int relativeY = (int) (mm2px(pt2mm(pageSizeHeight - etalonYPt), dpi));
        return new int[]{relativeX, relativeY};
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
}
