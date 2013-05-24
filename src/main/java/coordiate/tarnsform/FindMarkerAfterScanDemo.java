package coordiate.tarnsform;

import static coordiate.tarnsform.UnitConv.*;

import com.google.zxing.qrcode.detector.FinderMarkerDemo;

public class FindMarkerAfterScanDemo {
    private int dpi;
    private int pageSizeHeight;
    private int[][] marker = new int[3][3];

    private FinderMarkerDemo finderMarker;
    private int relativeStepHorizontal;
    private int relativeStepVertical;

    private int relativeX;
    private int relativeY;

    public FindMarkerAfterScanDemo(String fileName, int dpiPx, int pageSizeHeightPt) {
        this.dpi = dpiPx;
        this.pageSizeHeight = pageSizeHeightPt;
        finderMarker = new FinderMarkerDemo(fileName);// "img/scaned_files/first_page.png"
        marker[0][0] = (int) mm2px(pt2mm(36), dpi);
        marker[0][1] = (int) mm2px(pt2mm(36), dpi);
        marker[0][2] = (int) mm2px(pt2mm(559), dpi);
        marker[1][0] = (int) mm2px(pt2mm(pageSizeHeight - 36), dpi);
        marker[1][1] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi);
        marker[1][2] = (int) mm2px(pt2mm(pageSizeHeight - 806), dpi);

        relativeStepHorizontal =
            (int) (((marker[0][0] - finderMarker.getBottomLeft().getX()) + (marker[0][1] - finderMarker.getTopLeft().getX()) + (marker[0][2] - finderMarker
                .getTopRight().getX())) / 3);
        relativeStepVertical =
            (int) (((marker[1][0] - finderMarker.getBottomLeft().getY()) + (marker[1][1] - finderMarker.getTopLeft().getY()) + (marker[1][2] - finderMarker
                .getTopRight().getY())) / 3);
    }

    public int[] findRelativePx(int etalonXPx, int etalonYPx) {
        relativeX = etalonXPx - relativeStepHorizontal;
        relativeY = etalonYPx - relativeStepVertical;
        return new int[]{relativeX, relativeY};
    }

    public int[] findRelativePt(int etalonXPt, int etalonYPt) {
        relativeX = mm2px(pt2mm(etalonXPt), dpi) - relativeStepHorizontal;
        relativeY = mm2px(pt2mm(pageSizeHeight - etalonYPt), dpi) - relativeStepVertical;
        return new int[]{relativeX, relativeY};
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        FindMarkerAfterScanDemo demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page.png", 300, 841);
//        System.out.println("Relative Cover bottom left X:" + demo.findRelativePx(421, 1071)[0] + " Y:" + demo.findRelativePx(421, 1071)[1]);
//        System.out.println("Relative Cover top right X:" + demo.findRelativePx(2054, 696)[0] + " Y:" + demo.findRelativePx(2054, 696)[1]);
//        System.out.println("Relative footer bottom left X:" + demo.findRelativePt(480, 73)[0] + " Y:" + demo.findRelativePx(480, 73)[1]);
//        System.out.println("Relative footer top right X:" + demo.findRelativePt(532, 126)[0] + " Y:" + demo.findRelativePx(532, 126)[1]);
        
        

        demo = new FindMarkerAfterScanDemo("img/scaned_files/second_page.png", 300, 841);
        System.out.println("Relative singleChoice bottom left X:" + demo.findRelativePt(53, 679)[0] + " Y:" + demo.findRelativePt(53, 679)[1]);
        System.out.println("Relative singleChoice top right X:" + demo.findRelativePt(61, 687)[0] + " Y:" + demo.findRelativePt(61, 687)[1]);
        
        
//        demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page.png", 300, 841);
//        System.out.println("Relative Cover bottom left X:" + demo.findRelativePt(421, 1071)[0] + " Y:" + demo.findRelativePt(421, 1071)[1]);
//        System.out.println("Relative Cover top right X:" + demo.findRelativePt(2054, 696)[0] + " Y:" + demo.findRelativePt(2054, 696)[1]);
//        
//        demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page.png", 300, 841);
//        System.out.println("Relative Cover bottom left X:" + demo.findRelativePt(421, 1071)[0] + " Y:" + demo.findRelativePt(421, 1071)[1]);
//        System.out.println("Relative Cover top right X:" + demo.findRelativePt(2054, 696)[0] + " Y:" + demo.findRelativePt(2054, 696)[1]);
//        
//        demo = new FindMarkerAfterScanDemo("img/scaned_files/first_page.png", 300, 841);
//        System.out.println("Relative Cover bottom left X:" + demo.findRelativePt(421, 1071)[0] + " Y:" + demo.findRelativePt(421, 1071)[1]);
//        System.out.println("Relative Cover top right X:" + demo.findRelativePt(2054, 696)[0] + " Y:" + demo.findRelativePt(2054, 696)[1]);
    }

}
