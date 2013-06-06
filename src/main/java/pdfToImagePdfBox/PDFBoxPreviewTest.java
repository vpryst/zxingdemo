package pdfToImagePdfBox;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFImageWriter;

public class PDFBoxPreviewTest {

       /**
        * @param args
        */
       public static void main(String[] args) {
              try {
                     String path = "C:/Users/oivasiv/AppData/Local/Temp/pba_generation/16/16940d75-6510-443a-9b77-4c69d0bead60.pdf";
                     PDFBoxPreviewTest t = new PDFBoxPreviewTest();
                     t.getDocument(path);
              } catch (Exception e) {

                     System.out.println(e.getMessage());
              }
       }

       private PDDocument getDocument(String path) throws Exception {
              PDDocument document = null;

              document = PDDocument.load(path);

              PDFImageWriter writer = new PDFImageWriter();
              boolean success = writer.writeImage(document, "png", "", 1, 5,
                           "myFileName", BufferedImage.TYPE_INT_RGB, 300);
              System.out.println(success);

              return document;
       }
}