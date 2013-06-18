package ImageDpi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.TiffImage;

public class ImagZxingDpi {

    /**
     * @param args
     * @throws Exception
     * @throws MalformedURLException
     * @throws BadElementException
     */
    public static void main(String[] args) throws BadElementException, MalformedURLException, Exception {
        File file = new File("img/scaned_files/tif_scan/");
        for (int i = 0; i < file.listFiles().length; i++) {
            System.out.println(file.listFiles()[i].getPath());
            System.out.println(getDpi(file.listFiles()[i].getPath())[0]);
            System.out.println(getDpi(file.listFiles()[i].getPath())[1]);
        }

        file = new File("img/scaned_files/tif_scan_pdf/");
        for (int i = 0; i < file.listFiles().length; i++) {
            System.out.println("File name" + file.listFiles()[i].getPath());
            System.out.println(getDpi(file.listFiles()[i].getPath())[0]);
            System.out.println(getDpi(file.listFiles()[i].getPath())[1]);
        }

        file = new File("img/scaned_files/pdf_scan_pdf/");
        for (int i = 0; i < file.listFiles().length; i++) {
            System.out.println(file.listFiles()[i].getPath());
            System.out.println(getDpi(file.listFiles()[i].getPath())[0]);
            System.out.println(getDpi(file.listFiles()[i].getPath())[1]);
        }
        /*
         * file = new File("img/scaned_files/"); for (int i = 0; i < file.listFiles().length; i++) {
         * System.out.println(file.listFiles()[i].getPath()); System.out.println(getDpi(file.listFiles()[i].getPath())[0]);
         * System.out.println(getDpi(file.listFiles()[i].getPath())[1]); }
         */

    }

    public static int[] getDpi(String fileName) throws Exception {
        int[] dpi = {0, 0};

        Demo demo = new Demo(fileName);
        demo.readAndDisplayMetadata();
        Image img = null;
        //
        if (demo.fileType() != null && demo.fileType().getFormatName() == "JPEG") {
            dpi = demo.getDpi();
            if (dpi[0] != 0 && dpi[1] != 0 && dpi != null) {
                return dpi;
            }
        } else if (demo.fileType() != null && 
            (demo.fileType().getFormatName() == "JPEG" || demo.fileType().getFormatName() == "PNG")) {
            img = Image.getInstance(fileName);
            dpi[0] = img.getDpiX();
            dpi[1] = img.getDpiY();
            if (dpi[0] != 0 && dpi[1] != 0) {
                return dpi;
            }
        } else {
            RandomAccessFileOrArray ra = new RandomAccessFileOrArray(fileName);
            int pages = TiffImage.getNumberOfPages(ra);
            for (int i = 1; i <= pages; i++) {
                dpi[0] = TiffImage.getTiffImage(ra, i).getDpiX();
                dpi[1] = TiffImage.getTiffImage(ra, i).getDpiY();
                // = img.getDpiX();
                // dpi[1] = img.getDpiY();
            }
            ra.close();
        }
        return dpi;

    }

}
