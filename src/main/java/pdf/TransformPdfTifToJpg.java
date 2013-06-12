package pdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.text.pdf.qrcode.ByteArray;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStream;

public class TransformPdfTifToJpg {
    private PdfReader reader;

    public TransformPdfTifToJpg(String filePdf) {
        try {
            reader = new PdfReader(filePdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage[] convertPdfToJpg(String path, String fileJpg) {
        BufferedImage[] images = null;
        for (int i = 0; i < reader.getXrefSize(); i++) {
            PdfObject pdfobj = reader.getPdfObject(i);
            if (pdfobj != null) {
                if (pdfobj.isStream()) {
                    PdfStream stream = (PdfStream) pdfobj;
                    PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
                    if (pdfsubtype != null) {
                        // PDF Subtype OK
                        if (pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
                            // image found
                            byte[] img;
                            try {
                                img = PdfReader.getStreamBytesRaw((PRStream) stream);
                                images[i] = ImageIO.read(new ByteArrayInputStream(img));
                                FileOutputStream out = new FileOutputStream(new File(path + fileJpg + i + ".jpg"));
                                out.write(img);
                                out.flush();
                                out.close();
                                System.out.println(images[i]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return images;
    }

    public int findPdfPageCount(String path, String fileJpg) {
        int count = 0;
        for (int i = 0; i < reader.getXrefSize(); i++) {
            PdfObject pdfobj = reader.getPdfObject(i);
            if (pdfobj != null) {
                if (pdfobj.isStream()) {
                    PdfStream stream = (PdfStream) pdfobj;
                    PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
                    if (pdfsubtype != null) {
                        // PDF Subtype OK
                        if (pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
                            // image found
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TransformPdfTifToJpg tr = new TransformPdfTifToJpg("img/scaned_files/pdf_scan/SimpleImages.pdf");
        // tr.convertPdfToJpg("img/scaned_files/pdf_scan/img_temp/", "SimpleImages.pdf");
    }

}
