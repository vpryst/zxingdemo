package scaner;

import static com.google.zxing.qrcode.detector.FinderPatternFinderEx.find;
import static scaner.UnitConv.mm2px;
import static scaner.UnitConv.pt2mm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.detector.FinderPattern;

public class MarkerFinder {
    /**
     * @param args
     * @throws IOException
     * @throws FormatException
     * @throws ChecksumException
     * @throws NotFoundException
     */
    private FinderPattern bottomLeft;
    private FinderPattern topLeft;
    private FinderPattern topRight;

    private BufferedImage image;
    int width;
    int height;

    public MarkerFinder(String fileName) throws IOException {
        this(ImageIO.read(new File(fileName)));
    }

    public MarkerFinder(BufferedImage img) {
        this.image = img;
        getMarkerCoordinate();
    }

    public BufferedImage[] devideImage() {

        BufferedImage[] subImg = new BufferedImage[4];
        subImg[0] = image.getSubimage(0, 0, width, height);
        subImg[1] = image.getSubimage(width, 0, width, height);
        subImg[2] = image.getSubimage(0, height, width, height);
        subImg[3] = image.getSubimage(width, height, width, height);
        return subImg;
    }

    public void getMarkerCoordinate() {
        width = image.getWidth() / 2;
        height = image.getHeight() / 2;

        List<FinderPattern> findMarker;

        findMarker = find(0, 0, width, height, image);
        if (findMarker.size() == 1) {
            setTopLeft(findMarker.get(0));
        }

        findMarker.clear();
        findMarker = find(width, 0, image.getWidth(), height, image);
        if (findMarker.size() == 1) {
            setTopRight(findMarker.get(0));
        }

        findMarker.clear();
        findMarker = find(0, height, width, image.getHeight(), image);
        if (findMarker.size() == 1) {
            setBottomLeft(findMarker.get(0));
        }
    }

    public FinderPattern getBottomLeft() {
        return bottomLeft;
    }

    public FinderPattern getTopLeft() {
        return topLeft;
    }

    public FinderPattern getTopRight() {
        return topRight;
    }

    public void setBottomLeft(FinderPattern bottomleft) {
        this.bottomLeft = bottomleft;
    }

    public void setTopLeft(FinderPattern topLeft) {
        this.topLeft = topLeft;
    }

    public void setTopRight(FinderPattern topRight) {
        this.topRight = topRight;
    }

    public static void main(String[] args) {
        
        MarkerFinder demo = null;
        
        try {
            demo = new MarkerFinder("img/scaned_files/sc/second_page9.jpg");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(demo.getBottomLeft());
        System.out.println(demo.getTopLeft());
        System.out.println(demo.getTopRight());
        
        System.out.println(mm2px(pt2mm(36), 300));
        System.out.println(mm2px(pt2mm(36), 300));
        System.out.println(mm2px(pt2mm(559), 300));
        System.out.println(mm2px(pt2mm(806), 300));

    }

}
