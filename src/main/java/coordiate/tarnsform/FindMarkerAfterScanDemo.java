package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.*;

import com.google.zxing.qrcode.detector.FinderMarkerDemo;

public class FindMarkerAfterScanDemo {
    private final static int PAGE_SIZE_WIDTH = (int) mm2pt(in2mm(8.26)); // 594pt
    private final static int PAGE_SIZE_HEIGHT = (int) mm2pt(in2mm(11.69)); // 841pt
    
    private final static int COVER_LEFT = (int) mm2pt(in2mm(101));
    private final static int COVER_RIGHT = (int) mm2pt(in2mm(101));
    
    private final static FinderMarkerDemo demo = new FinderMarkerDemo("img/scaned_files/first_page.png");
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int[][] marker = new int[3][3];
        marker[0][0] = (int) mm2px(pt2mm(36), 300);
        marker[0][1] = (int) mm2px(pt2mm(36), 300);
        marker[0][2] = (int) mm2px(pt2mm(559), 300);
        marker[1][0] = (int) mm2px(pt2mm(PAGE_SIZE_HEIGHT-36), 300);
        marker[1][1] = (int) mm2px(pt2mm(PAGE_SIZE_HEIGHT-806), 300);
        marker[1][2] = (int) mm2px(pt2mm(PAGE_SIZE_HEIGHT-806), 300);
        
                                
        
           
        
        System.out.println("Etalon mareker bottom left X:" + marker[0][0] + " Y:" + marker[1][0]);
        System.out.println("Real mareker bottom left X:" + demo.getBottomLeft().getX() + " Y:" + demo.getBottomLeft().getY());
        
        System.out.println("Etalon mareker top left X:" + marker[0][1] + " Y:" + marker[1][1]);
        System.out.println("Real mareker top left X:" + demo.getTopLeft().getX() + " Y:" + demo.getTopLeft().getY());
        
        System.out.println("Etalon mareker top right X:" + marker[0][2] + " Y:" + marker[1][2]);
        System.out.println("Real mareker top roght X:" + demo.getTopRight().getX() + " Y:" + demo.getTopRight().getY());
        
        
        
        System.out.println(PAGE_SIZE_HEIGHT);
       

        System.out.println(mm2px(pt2mm(36), 300));
        System.out.println(mm2px(pt2mm(559), 300));
        
        System.out.println();
        System.out.println(mm2px(pt2mm(101), 300));
        System.out.println(mm2px(pt2mm(493), 300));
        System.out.println(mm2px(pt2mm(382), 300));
        System.out.println();
        
        System.out.println(mm2px(pt2mm(480), 300));
        System.out.println(mm2px(pt2mm(841), 300));
        
        
        
        
        
    }

}
