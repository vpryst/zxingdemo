package demo;
import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class MarkerCircle {
    /**
     * This method creat Mark whit proporshin
     * 
     * @param content - where we write
     * @param x - lower left x
     * @param y - lower left y
     * @param size - size of mark
     */
    public static void markCircle(PdfContentByte content, int x, int y, int size) {
        // Proportion 1:1:3:1:1
        int sizeStep = size / 7;
        int center = size / 2;
        if (sizeStep < 1) {
            try {
                throw new IllegalArgumentException("Marker must be aliquot 7");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            

            content.circle(x, y, center);
            content.fill();
            //content.setRGBColorFill(0x00, 0x00, 0x00);
            content.setRGBColorStroke(0xFF, 0xFF, 0xFF);
            content.setLineWidth(sizeStep);
            content.circle(x, y, (float) (center - (sizeStep * 1.5)));
            
//            content.setRGBColorFill(0xFF, 0xFF, 0xFF);
//            content.setLineWidth(10f);
//            content.circle(350.0f, 300.0f, 50.0f);

            content.fillStroke();
        }
    }

}