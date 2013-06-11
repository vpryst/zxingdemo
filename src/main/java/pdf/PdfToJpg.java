package pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStream;

public class PdfToJpg {

    private PdfReader reader;
    private String name; 
    
    public PdfToJpg(String filePdf) {
        try {
            reader = new PdfReader(filePdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void convertPdfToJpg(String path, String fileJpg) {
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
                                FileOutputStream out = new FileOutputStream(new File(path + fileJpg + i + ".jpg"));
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

    /**
     * @param args
     * @throws DocumentException
     * @throws IOException
     */
    public static void main(String[] args) throws DocumentException, IOException {
        String[] fileNamePdf = {
            /*"SKMBT_22313061010260_0001.pdf",
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
            "SKMBT_22313061009380.pdf"*/
            "SimpleImages.pdf"
            
        };
        
        for (int i = 0; i<fileNamePdf.length; i++) {
        PdfToJpg pd = new PdfToJpg("img/scaned_files/pdf_scan/" + fileNamePdf[i]);
        pd.convertPdfToJpg("img/scaned_files/pdf_scan/img_temp/", fileNamePdf[i]);
        }

    }
}
