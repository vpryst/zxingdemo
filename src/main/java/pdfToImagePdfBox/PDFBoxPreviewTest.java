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
           String[] fileNamePdf = {
               "SKMBT_22313061010260_0001.pdf",
               "SKMBT_22313061010260_0002.pdf",
               "SKMBT_22313061010260_0003.pdf",
               "SKMBT_22313061010260_0005.pdf",
               "SKMBT_22313061010260_0007.pdf",
               "SKMBT_22313061010260_0009.pdf",
               "SKMBT_22313061010260_0010.pdf",
               "SKMBT_22313061010260_0011.pdf",
               "SKMBT_22313061010260_0013.pdf",
               "SKMBT_22313061010260_0015.pdf",
               "SKMBT_22313061010260_0016.pdf",
               "SKMBT_22313061010260_0017.pdf",
               "SKMBT_22313061010260_0019.pdf",
               "SKMBT_22313061010260_0020.pdf",
               "SKMBT_22313061010260_0021.pdf",
               "SKMBT_22313061010260_0023.pdf",
               "SKMBT_22313061010260_0024.pdf",
               "SKMBT_22313061010260_0025.pdf",
               "SKMBT_22313061010260_0026.pdf",
               "SKMBT_22313061010260_0027.pdf",
               "SKMBT_22313061010260_0028.pdf",
               "SKMBT_22313061010260_0029.pdf",
               "SKMBT_22313061010260_0031.pdf",
               "SKMBT_22313061010260_0032.pdf",
               "SKMBT_22313061010260_0033.pdf",
               "SKMBT_22313061010260_0034.pdf",
               "SKMBT_22313061010260_0035.pdf",
               "SKMBT_22313061010260_0037.pdf",
               "SKMBT_22313061010260_0038.pdf",
               "SKMBT_22313061010260_0039.pdf",
               "SKMBT_22313061009380.pdf"
           };
           
           for (int i = 0; i<fileNamePdf.length; i++) {
           
              try {
                     String path = "img/scaned_files/pdf_scan/" + fileNamePdf[i];
                     PDFBoxPreviewTest t = new PDFBoxPreviewTest();
                     t.getDocument(path, fileNamePdf[i]);
                     
              } catch (Exception e) {

                     System.out.println(e.getMessage());
              }
           }
       }

       private void getDocument(String path, String file) throws Exception {
              PDDocument document = null;

              document = PDDocument.load(path);

              PDFImageWriter writer = new PDFImageWriter();
              boolean success = writer.writeImage(document, "png", "", 1, 40,
                           "img/scaned_files/pdf_scan/img_pdfbox/" + file, BufferedImage.TYPE_BYTE_GRAY, 300);
              System.out.println(success);

              document.close();
       }
}