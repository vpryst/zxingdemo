package demo;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontProvider;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.ex.HTMLWorkerEx;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class FontEmbedder {
    private static final int DEFAULT_MARGIN = 36;
    private String fileName;
    private String fontDirectory;
    private FontFactoryImpEx impEx; // Changed FontFactoryImp

    public FontEmbedder(String fileName, String fontDirectory) {
        this.fileName = fileName;
        this.fontDirectory = fontDirectory;
        impEx = new FontFactoryImpEx();
    }

    /**
     * Register Fonts by path Absolute "d:/fonts" Relative from project classPath "fonts"
     */
    public void registerFonts() {
        impEx.defaultEmbedding = true;
        impEx.registerDirectory(fontDirectory); // Absolute or Relative ClassPath path to fonts
    }

    public File renderText(String text) throws Exception {
        Document document = new Document(PageSize.A4);
        File pdfFile = File.createTempFile(fileName, ".pdf");
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

        document.setMargins(DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN);

        document.open();

        document.add(new Paragraph("Courier Normal " + text, impEx.getFont(BaseFont.COURIER)));
        document.add(new Paragraph("Courier Normal " + text, impEx.getFont(BaseFont.COURIER, Font.DEFAULTSIZE, Font.BOLDITALIC)));
        document.add(new Paragraph("Courier Bold " + text, impEx.getFont(BaseFont.COURIER, Font.DEFAULTSIZE, Font.BOLD)));
        document.add(new Paragraph("Courier BoldItalic " + text, impEx.getFont(BaseFont.COURIER, Font.DEFAULTSIZE, Font.BOLDITALIC)));
        document.add(new Paragraph("Courier Italic " + text, impEx.getFont(BaseFont.COURIER, Font.DEFAULTSIZE, Font.ITALIC)));

        document.add(new Paragraph("Helvetica Normal " + text, impEx.getFont(BaseFont.HELVETICA)));
        document.add(new Paragraph("Helvetica Bold " + text, impEx.getFont(BaseFont.HELVETICA, Font.DEFAULTSIZE, Font.BOLD)));
        document.add(new Paragraph("Helvetica BoldItalic " + text, impEx.getFont(BaseFont.HELVETICA, Font.DEFAULTSIZE, Font.BOLDITALIC)));
        document.add(new Paragraph("Helvetica Italic " + text, impEx.getFont(BaseFont.HELVETICA, Font.DEFAULTSIZE, Font.ITALIC)));

        document.add(new Paragraph("Symbol normal " + text, impEx.getFont(BaseFont.SYMBOL)));

        document.add(new Paragraph("Times_roman Bold " + text, impEx.getFont(BaseFont.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD)));
        document.add(new Paragraph("Times_roman BoldItalic " + text, impEx.getFont(BaseFont.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLDITALIC)));
        document.add(new Paragraph("Times_roman Italic " + text, impEx.getFont(BaseFont.TIMES_ROMAN, Font.DEFAULTSIZE, Font.ITALIC)));
        document.add(new Paragraph("Times_roman Normal " + text, impEx.getFont(BaseFont.TIMES_ROMAN)));

        document.add(new Paragraph("Zapfdingbats " + text, impEx.getFont(BaseFont.ZAPFDINGBATS)));

        document.close();
        return pdfFile;
    }

    private static class CustomFontProvider implements FontProvider {
        private FontFactoryImpEx impEx;

        public CustomFontProvider(FontFactoryImpEx impEx) {
            this.impEx = impEx;
        }

        public Font getFont(String fontname, String encoding, boolean embedded, float size, int style, Color color) {
            if (fontname == null || !isRegistered(fontname)) {
                return impEx.getFont(BaseFont.HELVETICA, encoding, BaseFont.EMBEDDED, size, style, color);
            } else {
                return impEx.getFont(fontname, encoding, BaseFont.EMBEDDED, size, style, color);
            }
        }

        public boolean isRegistered(String fontname) {
            return impEx.isRegistered(fontname);
        }
    }

    public File renderHTML(String text) throws Exception {
        Document document = new Document(PageSize.A4);
        File pdfFile = File.createTempFile(fileName, ".pdf");
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

        document.setMargins(DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_MARGIN);

        document.open();

        HTMLWorkerEx htmlWorkerEx = new HTMLWorkerEx(document);

        HashMap<String, Object> providers = new HashMap<String, Object>();
        providers.put("font_factory", new CustomFontProvider(impEx)); //

        ArrayList<Element> list = HTMLWorkerEx.parseToList(new StringReader(text), null, providers);
        for (Object line : list) {
            document.add((Element) line);
        }

        document.close();
        return pdfFile;
    }
}
