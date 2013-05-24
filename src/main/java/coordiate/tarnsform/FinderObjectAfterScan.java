package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.in2mm;
import static coordiate.tarnsform.UnitConv.mm2pt;
import static coordiate.tarnsform.UnitConv.mm2px;
import static coordiate.tarnsform.UnitConv.pt2mm;

public class FinderObjectAfterScan {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int PAGE_SIZE_WIDTH = (int) mm2pt(in2mm(8.27)); // 594pt
        int PAGE_SIZE_HEIGHT = (int) mm2pt(in2mm(11.69)); // 841pt
        int dpi = 300;
        
        int COVER_LEFT = (int) mm2px(pt2mm(101), dpi);
        int COVER_RIGHT = (int) mm2px(pt2mm(493), dpi);
        int COVER_TOP = (int) mm2px(pt2mm(PAGE_SIZE_HEIGHT - 674), dpi);
        int COVER_BOTTOM = (int) mm2px(pt2mm(PAGE_SIZE_HEIGHT - 584), dpi);
        
        System.out.println(COVER_LEFT);
        System.out.println(COVER_RIGHT);
        System.out.println(COVER_TOP);
        System.out.println(COVER_BOTTOM);
        
    }

}
