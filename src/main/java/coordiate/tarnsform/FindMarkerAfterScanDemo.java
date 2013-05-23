package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.*;

public class FindMarkerAfterScanDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        double mm = pt2mm(36);
        double px = mm2px(mm, 300);

        System.out.println(mm);
        System.out.println(px);

        System.out.println(mm2px(in2mm(0.1), 300));
        System.out.println(in2mm(11.69));
        System.out.println(mm2px(pt2mm(16), 300));
        System.out.println(mm2px(pt2mm(28), 300));
    }

}
