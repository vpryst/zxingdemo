package scaner;

import static scaner.UnitConv.mm2px;
import static scaner.UnitConv.pt2mm;
import static scaner.zxing.FinderPatternFinderEx.find;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import scaner.zxing.FinderPatternEx;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;

public class MarkerFinder {
    /**
     * @param args
     * @throws IOException
     * @throws FormatException
     * @throws ChecksumException
     * @throws NotFoundException
     */
    private FinderPatternEx bottomLeft;
    private FinderPatternEx topLeft;
    private FinderPatternEx topRight;

    private BufferedImage image;
    int width;
    int height;

    public MarkerFinder(String fileName) throws IOException {
        this(ImageIO.read(new File(fileName)));
    }

    public MarkerFinder(BufferedImage img) {
        this.image = img;
        findMarker();
    }

    public void findMarker() {
        width = image.getWidth() / 2;
        height = image.getHeight() / 2;

        List<FinderPatternEx> findMarker;

        findMarker = find(0, 0, width, height, image);
        //if (findMarker.size() == 1) {
            setTopLeft(findMarker.get(0));
        //}

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

    public FinderPatternEx getBottomLeft() {
        return bottomLeft;
    }

    public FinderPatternEx getTopLeft() {
        return topLeft;
    }

    public FinderPatternEx getTopRight() {
        return topRight;
    }
    
    private void rotateToVertical(FinderPatternEx bottomleft, FinderPatternEx topLeft, FinderPatternEx topRight) {
        setBottomLeft(bottomleft);
        setTopLeft(topLeft);
        setTopRight(topRight);
    }

    private void setBottomLeft(FinderPatternEx bottomleft) {
        this.bottomLeft = bottomleft;
    }

    private void setTopLeft(FinderPatternEx topLeft) {
        this.topLeft = topLeft;
    }

    private void setTopRight(FinderPatternEx topRight) {
        this.topRight = topRight;
    }

    public static void main(String[] args) {
        
        MarkerFinder demo = null;
        
        try {
            demo = new MarkerFinder("img/scaned_files/sc/second_page3.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(demo.getBottomLeft());
        System.out.println(demo.getTopLeft());
        System.out.println(demo.getTopRight());
        
        System.out.println();

    }

}
