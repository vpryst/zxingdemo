package demo;


import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.FontFactoryImp;
import com.lowagie.text.FontProvider;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.ex.HTMLWorkerEx;

/**
 * This is demo (POC) for PDF Font Embedding.
 * Please note: it's ONLY demo. I.e. must be improved.
 * @author oivasiv
 *
 */
public class FontEmbedder {
	
	private static final int DEFAULT_MARGIN = 36;
	private String fileName;
	private String fontDirectory;
	
    public FontEmbedder(String fileName, String fontDirectory) {
		this.fileName = fileName;
		this.fontDirectory = fontDirectory;
	}

	/**
     * Register Fonts by path Absolute "d:/fonts" Relative from project classPath "fonts"
     */
    public void registerFonts() {
        FontFactory.defaultEmbedding = true;
        FontFactoryImp impl = new FontFactoryImp();
        impl.registerFamily(BaseFont.COURIER.toLowerCase(), BaseFont.COURIER_BOLDOBLIQUE.toLowerCase(), fontDirectory + "Courier_BoldOblique.afm");
        impl.registerFamily(BaseFont.HELVETICA.toLowerCase(), BaseFont.HELVETICA_BOLDOBLIQUE.toLowerCase(), fontDirectory + "Helvetica_BoldOblique.afm");
        impl.registerFamily(BaseFont.TIMES_ROMAN.toLowerCase(), BaseFont.TIMES_BOLDITALIC.toLowerCase(), fontDirectory + "Times_BoldItalic.afm");
        impl.registerFamily(BaseFont.ZAPFDINGBATS.toLowerCase(), BaseFont.ZAPFDINGBATS.toLowerCase(), fontDirectory + "ZapfDingbats.afm");
        FontFactory.setFontImp(impl);
        //FontFactory.register("fonts/Courier_BoldOblique.afm", BaseFont.COURIER_BOLDOBLIQUE.toLowerCase());
        FontFactory.registerDirectory(fontDirectory); // Absolute or Relative ClassPath path to fonts
    }

	public File renderText(String text) throws IOException, DocumentException {
		Document document = new Document(PageSize.A4);
		File pdfFile = File.createTempFile(fileName, ".pdf");
		PdfWriter writer = PdfWriter.getInstance(document,
				new FileOutputStream(pdfFile));

		document.setMargins(DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN,
				DEFAULT_MARGIN);

		document.open();

		document.add(new Paragraph("Courier Normal " + text, FontFactory
				.getFont(BaseFont.COURIER)));
		document.add(new Paragraph("Courier Bold " + text, FontFactory.getFont(
				BaseFont.COURIER, 12, Font.BOLD)));
		document.add(new Paragraph("Courier BoldItalic " + text, FontFactory
				.getFont(BaseFont.COURIER, 12, Font.BOLDITALIC)));
		document.add(new Paragraph("Courier Italic " + text, FontFactory
				.getFont(BaseFont.COURIER, 12, Font.ITALIC)));

		document.add(new Paragraph("Helvetica Normal " + text, FontFactory
				.getFont(BaseFont.HELVETICA)));
		document.add(new Paragraph("Helvetica Bold " + text, FontFactory
				.getFont(BaseFont.HELVETICA, 12, Font.BOLD)));
		document.add(new Paragraph("Helvetica BoldItalic " + text, FontFactory
				.getFont(BaseFont.HELVETICA, 12, Font.BOLDITALIC)));
		document.add(new Paragraph("Helvetica Italic " + text, FontFactory
				.getFont(BaseFont.HELVETICA, 12, Font.ITALIC)));

		document.add(new Paragraph("Symbol normal " + text, FontFactory
				.getFont(BaseFont.SYMBOL)));

		document.add(new Paragraph("Times_roman Bold " + text, FontFactory
				.getFont(BaseFont.TIMES_ROMAN, 12, Font.BOLD)));
		document.add(new Paragraph("Times_roman BoldItalic " + text,
				FontFactory.getFont(BaseFont.TIMES_ROMAN, 12, Font.BOLDITALIC)));
		document.add(new Paragraph("Times_roman Italic " + text, FontFactory
				.getFont(BaseFont.TIMES_ROMAN, 12, Font.ITALIC)));
		document.add(new Paragraph("Times_roman Normal " + text, FontFactory
				.getFont(BaseFont.TIMES_ROMAN)));

		document.add(new Paragraph("Zapfdingbats " + text, FontFactory
				.getFont(BaseFont.ZAPFDINGBATS)));

		document.close();

		return pdfFile;
	}

    public File renderHTML(String text) throws IOException, DocumentException {
            Document document = new Document(PageSize.A4);
            File pdfFile = File.createTempFile(fileName, ".pdf");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            document.setMargins(DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN);
            document.open();

            HTMLWorkerEx htmlWorker = new HTMLWorkerEx(document);

            HashMap<String, Object> providers = new HashMap<String, Object>();
            providers.put("font_factory", new CustomFontProvider());

            ArrayList<Element> list = HTMLWorkerEx.parseToList(new StringReader(text), null, providers);
            for (Object line : list) {
                document.add((Element) line);
            }

            document.close();
            return pdfFile;
    }
    
    private static class CustomFontProvider implements FontProvider {
        public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, Color color) {
            if (fontname == null || !isRegistered(fontname)) {
                return FontFactory.getFont(BaseFont.HELVETICA, encoding, size, style, color);
            } else {
                return FontFactory.getFont(fontname, encoding, size, style, color);
            }

        }

        public boolean isRegistered(String fontname) {
            return FontFactory.isRegistered(fontname);
        }
    }


}
