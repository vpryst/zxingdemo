package scaner;

import static scaner.UnitConv.mm2px;
import static scaner.UnitConv.pt2mm;
import static scaner.zxing.FinderPatternFinderEx.find;

import java.awt.Point;
import java.awt.geom.Point2D;
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

        List<FinderPatternEx> findMarker = null;

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
        //System.out.println(findMarker.toString());
        if (findMarker.size() == 1) {
            setBottomLeft(findMarker.get(0));
        }
        findMarker.clear();
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
        File file = null;
        BufferedImage img = null;
        String[] files =
            {"SKMBT_22313060714040_0001.jpg", "SKMBT_22313060714040_0002.jpg", "SKMBT_22313060714040_0003.jpg",
                "SKMBT_22313060714040_0005.jpg", "SKMBT_22313060714040_0007.jpg", "SKMBT_22313060714040_0009.jpg",
                "SKMBT_22313060714040_0010.jpg", "SKMBT_22313060714040_0011.jpg", "SKMBT_22313060714040_0013.jpg",
                "SKMBT_22313060714040_0015.jpg", "SKMBT_22313060714040_0016.jpg", "SKMBT_22313060714040_0017.jpg",
                "SKMBT_22313060714040_0019.jpg", "SKMBT_22313060714040_0020.jpg", "SKMBT_22313060714040_0021.jpg",
                "SKMBT_22313060714040_0023.jpg", "SKMBT_22313060714040_0024.jpg", "SKMBT_22313060714040_0025.jpg",
                "SKMBT_22313060714040_0026.jpg", "SKMBT_22313060714040_0027.jpg", "SKMBT_22313060714040_0028.jpg",
                "SKMBT_22313060714040_0029.jpg", "SKMBT_22313060714040_0031.jpg", "SKMBT_22313060714040_0032.jpg",
                "SKMBT_22313060714040_0033.jpg", "SKMBT_22313060714040_0034.jpg", "SKMBT_22313060714040_0035.jpg",
                "SKMBT_22313060714040_0037.jpg", "SKMBT_22313060714040_0038.jpg", "SKMBT_22313060714040_0039.jpg",};
        System.out.println(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                file = new File("img/scaned_files/gray_scan/" + files[i]);
                img = ImageIO.read(file);
                demo = new MarkerFinder(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(files[i] + " " + demo.getBottomLeft());
            System.out.println(i + " " + demo.getTopLeft());
            System.out.println(i + " " + demo.getTopRight());
            System.out.println();
            img.flush();
        }
        // try {
        // file = new File("img/scaned_files/gray_scan/SKMBT_22313060714040_0026.jpg");
        // img = ImageIO.read(file);
        // demo = new MarkerFinder(img);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // System.out.println(demo.getBottomLeft());
        // System.out.println(demo.getTopLeft());
        // System.out.println(demo.getTopRight());
    }

}
