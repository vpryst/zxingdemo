package pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

public class TransformPdfTifToJpgBuf {

    private PdfReader reader;
    private String sourceFolder;
    private String destinationFolder;
    private String fileName;
    private String subFileName;
    private List<ByteArrayOutputStream> listOut = new ArrayList<ByteArrayOutputStream>();

    /**
     * return List of ByteArrayOutputStream
     * 
     * @return
     */
    public List<ByteArrayOutputStream> getListOut() {
        return listOut;
    }

    /**
     * Constructor
     * 
     * @param sourceFolder - Source folder for files
     * @param destinationFolder - Destrination folder if needs save them
     * @param fileName - file name ho have to convert
     */
    public TransformPdfTifToJpgBuf(String sourceFolder, String destinationFolder, String fileName) {
        this.sourceFolder = sourceFolder;
        this.destinationFolder = destinationFolder;
        this.fileName = fileName;
        this.subFileName = generateSubFileName(fileName);
    }

    public TransformPdfTifToJpgBuf(String sourceFolder, String destinationFolder, File fileName) {
        this.sourceFolder = sourceFolder;
        this.destinationFolder = destinationFolder;
        this.fileName = fileName.getName();
        this.subFileName = generateSubFileName(fileName.getName());
    }

    public static List<File> savePdfToJpg(File pdfFile, File destFolder) {
        List<File> listFiles = new ArrayList<File>();
        PdfReader pdfReader = null;
        try {
               pdfReader = new PdfReader(pdfFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        for (int i = 0; i < pdfReader.getXrefSize(); i++) {
            PdfObject pdfobj = pdfReader.getPdfObject(i);
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
                                System.out.println(destFolder);
                                img = PdfReader.getStreamBytesRaw((PRStream) stream);
                                File file = new File (destFolder + "/" + pdfFile.getName() + "_" + i + ".jpg");
                                out = new FileOutputStream(file);
                                out.write(img);
                                listFiles.add(file);
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
        return listFiles;
    }

    /**
     * Convert Pdf file to Image jpg format
     * 
     * @param input - Only use when convert From tiff to jpg
     */
    public List<ByteArrayOutputStream> convertPdfToJpg(ByteArrayInputStream input) {

        try {
            if (reader == null && input == null) {
                reader = new PdfReader(sourceFolder + fileName);
            } else {
                reader = new PdfReader(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = null;
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
                                out = new ByteArrayOutputStream();
                                out.write(img);
                                listOut.add(out);
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
        return listOut;
    }

    /**
     * Convert Tiff Image to Pdf format
     * 
     * @return - Pdf file in ByteArrayOutputStream format
     */
    public ByteArrayOutputStream tiffToPdf() {
        Document document = new Document();
        RandomAccessFileOrArray ra = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {

            PdfWriter.getInstance(document, out);
            document.open();
            ra = new RandomAccessFileOrArray(sourceFolder + fileName);// "img/scaned_files/TIF_Scan/SKMBT_22313061009400.tif");
            int pages = TiffImage.getNumberOfPages(ra);
            for (int i = 1; i <= pages; i++) {
                document.add(TiffImage.getTiffImage(ra, i));
            }
            document.close();
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 
     */
    public List<ByteArrayOutputStream> convertTifToJpg() {
        ByteArrayInputStream input = new ByteArrayInputStream(tiffToPdf().toByteArray());
        convertPdfToJpg(input);
        return listOut;
    }

    public void saveTiffToJpgFile() {
        List<ByteArrayOutputStream> list = convertTifToJpg();
        try {
            for (int i = 0; i < list.size(); i++) {
                FileOutputStream saver = new FileOutputStream(new File(destinationFolder + subFileName + "_" + i + ".jpg"));
                saver.write(list.get(i).toByteArray());
                saver.flush();
                saver.close();
                list.clear();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePdfToJpgFile() {
        List<ByteArrayOutputStream> list = convertPdfToJpg(null);
        try {
            for (int i = 0; i < list.size(); i++) {
                FileOutputStream saver = new FileOutputStream(new File(destinationFolder + subFileName + "_" + i + ".jpg"));
                saver.write(list.get(i).toByteArray());
                saver.flush();
                saver.close();
            }
            list.clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateSubFileName(String fileName) {
        int position = fileName.indexOf('.');
        return fileName.substring(0, position);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

//        File dirTif = new File("img/scaned_files/tif_scan/"); // tif_pdf
//        System.out.println(dirTif.getPath());
//        for (int i = 0; i < dirTif.listFiles().length; i++) {
//            TransformPdfTifToJpgBuf transform =
//                new TransformPdfTifToJpgBuf("img/scaned_files/tif_scan/", "img/scaned_files/tif_scan_pdf/", dirTif.listFiles()[i].getName());
//            transform.saveTiffToJpgFile();
//
//        }
//        File dirPdf = new File("img/scaned_files/pdf_scan/"); // tif_pdf
//        System.out.println(dirPdf.getPath());
//        for (int i = 0; i < dirPdf.listFiles().length; i++) {
//            TransformPdfTifToJpgBuf transform =
//                new TransformPdfTifToJpgBuf("img/scaned_files/pdf_scan/", "img/scaned_files/pdf_scan_pdf/", dirPdf.listFiles()[i].getName());
//            transform.savePdfToJpgFile();
//        }
        File dirPdf = new File("img/scaned_files/pdf_scan/");
        System.out.println(dirPdf.getName());
        for (int i = 0; i < dirPdf.listFiles().length; i++) {
          System.out.println(TransformPdfTifToJpgBuf.savePdfToJpg(dirPdf.listFiles()[i], new File("img/scaned_files/pdf_scan_pdf/")));
        }
    }

}
