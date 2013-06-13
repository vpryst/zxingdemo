package pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStream;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.TiffImage;

public class TransformPdfTifToJpg {
    private PdfReader reader;
    private String sourceFolder;
    private String destinationFolder;
    private String fileName;
    private String subFileName;

    public TransformPdfTifToJpg(String sourceFolder, String destinationFolder, String fileName) {
        this.sourceFolder = sourceFolder;
        this.destinationFolder = destinationFolder;
        this.fileName = fileName;
        this.subFileName = generateSubFileName(fileName);
        makeFolder(sourceFolder);
        makeFolder(destinationFolder);
    }

    public void convertPdfToJpg() {
        try {
            reader = new PdfReader(sourceFolder + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                                FileOutputStream out = new FileOutputStream(new File(destinationFolder + subFileName + "_" + i + ".jpg"));
                                out.write(img);
                                out.flush();
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    public int findPdfPageCount() {
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

    public void tiffToPdf() {
        Document document = new Document();
        RandomAccessFileOrArray ra = null;
        try {

            PdfWriter.getInstance(document, new FileOutputStream(destinationFolder + subFileName + ".pdf"));
            document.open();
            ra = new RandomAccessFileOrArray(sourceFolder + fileName);// "img/scaned_files/TIF_Scan/SKMBT_22313061009400.tif");
            int pages = TiffImage.getNumberOfPages(ra);
            for (int i = 1; i <= pages; i++) {
                document.add(TiffImage.getTiffImage(ra, i));
            }
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertTifToJpg() {
        tiffToPdf();
        TransformPdfTifToJpg transform = new TransformPdfTifToJpg(destinationFolder, destinationFolder + "img_jpg/", subFileName + ".pdf");
        transform.convertPdfToJpg();
    }

    private String generateSubFileName(String fileName) {
        int position = fileName.indexOf('.');
        return fileName.substring(0, position);
    }
    private void makeFolder(String folder) {
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TransformPdfTifToJpg tr =
        // new TransformPdfTifToJpg("img/scaned_files/pdf_scan/", "img/scaned_files/pdf_scan/img_jpg/", "SimpleImages.pdf");
        // tr.convertPdfToJpg();
        File dirTif = new File("img/scaned_files/tif_scan/"); // tif_pdf
        System.out.println(dirTif.getPath());
        for (int i = 0; i < dirTif.listFiles().length; i++) {
            TransformPdfTifToJpg transform =
                new TransformPdfTifToJpg("img/scaned_files/tif_scan/", "img/scaned_files/tif_scan_pdf/", dirTif.listFiles()[i].getName());
            transform.convertTifToJpg();
        }
        File dirPdf = new File("img/scaned_files/pdf_scan/"); // tif_pdf
        System.out.println(dirPdf.getPath());
        for (int i = 0; i < dirPdf.listFiles().length; i++) {
            TransformPdfTifToJpg transform =
                new TransformPdfTifToJpg("img/scaned_files/pdf_scan/", "img/scaned_files/pdf_scan_pdf/", dirPdf.listFiles()[i].getName());
            transform.convertPdfToJpg();
        }
    }

}
