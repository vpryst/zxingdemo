package scaner;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class MarkerFinderTest {

   // @Test
    public void grayTest() {
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
            System.out.println(demo.getBottomLeft());
            System.out.println(demo.getTopLeft());
            System.out.println(demo.getTopRight());
            System.out.println();
            assertNotNull(demo.getBottomLeft());
            assertNotNull(demo.getTopLeft());
            assertNotNull(demo.getTopRight());
        }
    }

   // @Test
    public void colorTest() {
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
                file = new File("img/scaned_files/color_scan/" + files[i]);
                img = ImageIO.read(file);
                demo = new MarkerFinder(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(demo.getBottomLeft());
            System.out.println(demo.getTopLeft());
            System.out.println(demo.getTopRight());
            System.out.println();

            assertNotNull(demo.getBottomLeft());
            assertNotNull(demo.getTopLeft());
            assertNotNull(demo.getTopRight());
        }
    }
    @Test
    public void PdfToJpgTest() {
        MarkerFinder demo = null;
        File file = null;
        BufferedImage img = null;
        String[] files =
            {
            "SKMBT_22313061009380.pdf1.jpg",
            "SKMBT_22313061009380.pdf101.jpg",
            "SKMBT_22313061009380.pdf105.jpg",
            "SKMBT_22313061009380.pdf109.jpg",
            "SKMBT_22313061009380.pdf113.jpg",
            "SKMBT_22313061009380.pdf121.jpg",
            "SKMBT_22313061009380.pdf125.jpg",
            "SKMBT_22313061009380.pdf129.jpg",
            "SKMBT_22313061009380.pdf133.jpg",
            "SKMBT_22313061009380.pdf137.jpg",
            "SKMBT_22313061009380.pdf145.jpg",
            "SKMBT_22313061009380.pdf149.jpg",
            "SKMBT_22313061009380.pdf153.jpg",
            "SKMBT_22313061009380.pdf17.jpg",
            "SKMBT_22313061009380.pdf25.jpg",
            "SKMBT_22313061009380.pdf33.jpg",
            //"SKMBT_22313061009380.pdf37_fl.jpg",    //convert to grey 4 bu result the same
            "SKMBT_22313061009380.pdf41.jpg",
            "SKMBT_22313061009380.pdf49.jpg",
            "SKMBT_22313061009380.pdf5.jpg",
            "SKMBT_22313061009380.pdf57.jpg",
            "SKMBT_22313061009380.pdf61.jpg",
            "SKMBT_22313061009380.pdf65.jpg",
            //"SKMBT_22313061009380.pdf73.jpg", //expected drow ractangle
            "SKMBT_22313061009380.pdf77.jpg",
            "SKMBT_22313061009380.pdf81.jpg",
            //"SKMBT_22313061009380.pdf89_fl.jpg",      //convert to grey 4 bu result the same
            "SKMBT_22313061009380.pdf9.jpg",
            "SKMBT_22313061009380.pdf93.jpg",
            "SKMBT_22313061009380.pdf97.jpg",
            "SKMBT_22313061010260_0001.pdf1.jpg",
            "SKMBT_22313061010260_0002.pdf1.jpg",
            //"SKMBT_22313061010260_0003.pdf1_fl.jpg",      //convert to grey 4 bu result the same
            "SKMBT_22313061010260_0005.pdf1.jpg",
            "SKMBT_22313061010260_0007.pdf1.jpg",
            "SKMBT_22313061010260_0009.pdf1.jpg",
            "SKMBT_22313061010260_0010.pdf1_fl.jpg",       //convert to grey 4 bu result the same
            "SKMBT_22313061010260_0011.pdf1.jpg",
            "SKMBT_22313061010260_0013.pdf1.jpg",
            "SKMBT_22313061010260_0015.pdf1.jpg",
            "SKMBT_22313061010260_0016.pdf1.jpg",
            "SKMBT_22313061010260_0017.pdf1.jpg",
            //"SKMBT_22313061010260_0019.pdf1.jpg",           //expected drow ractangle
            "SKMBT_22313061010260_0020.pdf1.jpg",
            "SKMBT_22313061010260_0021.pdf1.jpg",
            "SKMBT_22313061010260_0023.pdf1.jpg",
            "SKMBT_22313061010260_0024.pdf1.jpg",
            "SKMBT_22313061010260_0025.pdf1.jpg",
            "SKMBT_22313061010260_0026.pdf1_fl.jpg",        //fixed convert to grey 4
            "SKMBT_22313061010260_0027.pdf1_fl.jpg",        //fixed convert to grey 4
            "SKMBT_22313061010260_0028.pdf1.jpg",
            "SKMBT_22313061010260_0029.pdf1.jpg",
            "SKMBT_22313061010260_0031.pdf1.jpg",
            "SKMBT_22313061010260_0032.pdf1.jpg",
            "SKMBT_22313061010260_0033.pdf1.jpg",
            "SKMBT_22313061010260_0034.pdf1.jpg",
            "SKMBT_22313061010260_0035.pdf1.jpg",
            "SKMBT_22313061010260_0037.pdf1.jpg",
            "SKMBT_22313061010260_0038.pdf1.jpg",
            "SKMBT_22313061010260_0039.pdf1.jpg"
            
            
            
            };

        System.out.println(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                file = new File("img/scaned_files/pdf_scan/img/" + files[i]);
                img = ImageIO.read(file);
                demo = new MarkerFinder(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(files[i] + " " + demo.getBottomLeft());
            System.out.println(demo.getTopLeft());
            System.out.println(demo.getTopRight());
            System.out.println();

            assertNotNull(demo.getBottomLeft());
            assertNotNull(demo.getTopLeft());
            assertNotNull(demo.getTopRight());
        }
    }
    @Test
    public void TiffTest() {
        MarkerFinder demo = null;
        File file = null;
        BufferedImage img = null;
        String[] files =
            {
            
            "SKMBT_22313061009380.pdf1.png",
            "SKMBT_22313061009380.pdf10.png",
            "SKMBT_22313061009380.pdf11.png",
            "SKMBT_22313061009380.pdf13.png",
            "SKMBT_22313061009380.pdf15.png",
            "SKMBT_22313061009380.pdf16.png",
            "SKMBT_22313061009380.pdf17.png",
            //"SKMBT_22313061009380.pdf19.png", //////expected drow ractangle
            "SKMBT_22313061009380.pdf2.png",
            "SKMBT_22313061009380.pdf20.png",
            "SKMBT_22313061009380.pdf21.png",
            //"SKMBT_22313061009380.pdf23_fl.png",    //convert to grey 4 but result the same
            "SKMBT_22313061009380.pdf24.png",
            "SKMBT_22313061009380.pdf25.png",
            "SKMBT_22313061009380.pdf26.png",
            "SKMBT_22313061009380.pdf27.png",
            "SKMBT_22313061009380.pdf28.png",    
            "SKMBT_22313061009380.pdf29.png",
            "SKMBT_22313061009380.pdf3.png",
            "SKMBT_22313061009380.pdf31.png",
            "SKMBT_22313061009380.pdf32.png",
            "SKMBT_22313061009380.pdf33_fl.png",  //fixed convert to gray4
            "SKMBT_22313061009380.pdf34.png",
            "SKMBT_22313061009380.pdf35.png", 
            "SKMBT_22313061009380.pdf37.png",
            "SKMBT_22313061009380.pdf38.png",
            "SKMBT_22313061009380.pdf39.png",      
            "SKMBT_22313061009380.pdf5.png",
            "SKMBT_22313061009380.pdf7.png",
            "SKMBT_22313061009380.pdf9.png",
            "SKMBT_22313061010260_0001.pdf1.png",
            "SKMBT_22313061010260_0002.pdf1.png",
            //"SKMBT_22313061010260_0003.pdf1_fl.png",      //convert to grey 4 but result the same
            "SKMBT_22313061010260_0005.pdf1.png",
            "SKMBT_22313061010260_0007.pdf1.png",
            "SKMBT_22313061010260_0009.pdf1.png",
            //"SKMBT_22313061010260_0010.pdf1_fl.png",       //convert to grey 4 but result the same
            "SKMBT_22313061010260_0011.pdf1.png",
            "SKMBT_22313061010260_0013.pdf1.png",
            "SKMBT_22313061010260_0015.pdf1.png",
            "SKMBT_22313061010260_0016.pdf1.png",
            "SKMBT_22313061010260_0017.pdf1.png",
            //"SKMBT_22313061010260_0019.pdf1.png",           //expected drow ractangle
            "SKMBT_22313061010260_0020.pdf1.png",
            "SKMBT_22313061010260_0021.pdf1.png",
            "SKMBT_22313061010260_0023.pdf1.png",
            "SKMBT_22313061010260_0024.pdf1.png",
            "SKMBT_22313061010260_0025.pdf1_fl.png",    //fixed convert to grey 4
            "SKMBT_22313061010260_0026.pdf1.png",        
            "SKMBT_22313061010260_0027.pdf1.png",        
            "SKMBT_22313061010260_0028.pdf1.png",
            "SKMBT_22313061010260_0029.pdf1.png",
            "SKMBT_22313061010260_0031.pdf1.png",
            "SKMBT_22313061010260_0032.pdf1.png",
            "SKMBT_22313061010260_0033.pdf1.png",
            "SKMBT_22313061010260_0034.pdf1.png",
            //"SKMBT_22313061010260_0035.pdf1_fl.png",      //convert to grey 4 but result the same
            "SKMBT_22313061010260_0037.pdf1_fl.png",    //fixed convert to grey 4
            "SKMBT_22313061010260_0038.pdf1.png",
            "SKMBT_22313061010260_0039.pdf1.png"
            
            
            
            };

        System.out.println(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                file = new File("img/scaned_files/pdf_scan/img_pdfbox/" + files[i]);
                img = ImageIO.read(file);
                demo = new MarkerFinder(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(files[i] + " " + demo.getBottomLeft());
            System.out.println(demo.getTopLeft());
            System.out.println(demo.getTopRight());
            System.out.println();

            assertNotNull(demo.getBottomLeft());
            assertNotNull(demo.getTopLeft());
            assertNotNull(demo.getTopRight());
        }
    }
    
    @Test
    public void PdfBoxTest() {
        MarkerFinder demo = null;
        File file = null;
        BufferedImage img = null;
        String[] files =
            {
            "SimpleImages.pdf1.jpg",
            "SimpleImages.pdf101_fl.jpg", //fixed convert grey 4
            "SimpleImages.pdf104.jpg",
            "SimpleImages.pdf107.jpg",
            "SimpleImages.pdf113.jpg",
            //"SimpleImages.pdf116_fl.jpg", //convert buyt result the same
            "SimpleImages.pdf119.jpg",
            "SimpleImages.pdf14_fl.jpg",  //fixed convert grey 4
            "SimpleImages.pdf20.jpg",
            "SimpleImages.pdf26.jpg",
            "SimpleImages.pdf29_fl.jpg",    //fixed convert grey 4
            "SimpleImages.pdf32.jpg",
            "SimpleImages.pdf39.jpg",
            "SimpleImages.pdf45.jpg",
            "SimpleImages.pdf48.jpg",
            "SimpleImages.pdf5.jpg",
            "SimpleImages.pdf51.jpg",
            //"SimpleImages.pdf57.jpg", //expected
            "SimpleImages.pdf60.jpg",
            "SimpleImages.pdf63.jpg",
            "SimpleImages.pdf70.jpg",
            "SimpleImages.pdf73.jpg",
            "SimpleImages.pdf76.jpg",
            "SimpleImages.pdf79.jpg",
            "SimpleImages.pdf8.jpg",
            "SimpleImages.pdf82_fl.jpg",    //fixed convert grey 4
            "SimpleImages.pdf85.jpg",
            "SimpleImages.pdf88.jpg",
            "SimpleImages.pdf94.jpg",
            "SimpleImages.pdf98.jpg",
            
            };

        System.out.println(files.length);
        for (int i = 0; i < files.length; i++) {
            try {
                file = new File("img/scaned_files/pdf_scan/img_temp/" + files[i]);
                img = ImageIO.read(file);
                demo = new MarkerFinder(img);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(files[i] + " " + demo.getBottomLeft());
            System.out.println(demo.getTopLeft());
            System.out.println(demo.getTopRight());
            System.out.println();

            assertNotNull(demo.getBottomLeft());
            assertNotNull(demo.getTopLeft());
            assertNotNull(demo.getTopRight());
        }
    }
}
