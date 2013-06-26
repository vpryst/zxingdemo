package demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

public class Temper {

    /**
     * @param args
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        // step 1
        FontFactory.defaultEmbedding = true;
        FontFactory.register("fonts/Courier.afm", BaseFont.COURIER);
        FontFactory.getFont(BaseFont.COURIER, BaseFont.WINANSI, BaseFont.EMBEDDED);
        FontFactory.register("fonts/Courier_Bold.afm", BaseFont.COURIER_BOLD);
        FontFactory.getFont(BaseFont.COURIER_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
        FontFactory.register("fonts/Courier_BoldOblique.afm", BaseFont.COURIER_BOLDOBLIQUE);
        FontFactory.getFont(BaseFont.COURIER_BOLDOBLIQUE, BaseFont.WINANSI, BaseFont.EMBEDDED);
        FontFactory.register("fonts/Courier_Oblique.afm", BaseFont.COURIER_OBLIQUE);
        FontFactory.getFont(BaseFont.COURIER_OBLIQUE, BaseFont.WINANSI, BaseFont.EMBEDDED);
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream("Temp.pdf"));
        // step 3
        document.open();
        // step 4:

        document.add(new Paragraph("Courier Normal Corier The quick brown fox jumps over the lazy dog", FontFactory
            .getFont(BaseFont.COURIER)));
        document.add(new Paragraph("Courier Bold Corier The quick brown fox jumps over the lazy dog", FontFactory.getFont(BaseFont.COURIER,
            12, Font.BOLD)));
        document.add(new Paragraph("Courier BoldItalic Corier The quick brown fox jumps over the lazy dog", FontFactory.getFont(
            BaseFont.COURIER, 12, Font.BOLDITALIC)));

        document.close();

    }

}
