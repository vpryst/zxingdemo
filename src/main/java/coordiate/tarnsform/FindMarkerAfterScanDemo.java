package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.*;

public class FindMarkerAfterScanDemo {
    private final static int PAGE_SIZE_WIDTH = (int) mm2pt(in2mm(8.26));
    private final static int PAGE_SIZE_HEIGHT = (int) mm2pt(in2mm(11.69));
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        int[][] marker = new int[3][3];
        marker[0][0] = (int) pt2mm(mm2px(36, 300));
        marker[0][1] = (int) pt2mm(mm2px(36, 300));
        marker[0][2] = (int) pt2mm(mm2px(559, 300));
        marker[1][0] = (int) pt2mm(mm2px(PAGE_SIZE_HEIGHT-36, 300));
        marker[1][1] = (int) pt2mm(mm2px(PAGE_SIZE_HEIGHT-559, 300));
        marker[1][2] = (int) pt2mm(mm2px(PAGE_SIZE_HEIGHT-806, 300));
        
                                
        
        double mm = pt2mm(36);
        double px = mm2px(mm, 300);

        System.out.println(mm);
        System.out.println(px);

        System.out.println();
        System.out.println(mm2pt(in2mm(11.69)));
        System.out.println(mm2px(pt2mm(16), 300));
        System.out.println(mm2px(pt2mm(28), 300));
    }

}
