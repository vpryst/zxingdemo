package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.mm2px;
import static coordiate.tarnsform.UnitConv.pt2mm;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;

public class Magic {
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
    
}
