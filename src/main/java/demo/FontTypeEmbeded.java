package demo;

import static demo.MarkerCircle.markCircle;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.FontFactoryImp;
import com.lowagie.text.FontProvider;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.ex.HTMLWorkerEx;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class FontTypeEmbeded {

    /**
     * @param args
     * @throws IOException
     * @throws DocumentException
     */
    /**
     * Inner class implementing the FontProvider class. This is needed if you want to select the correct fonts.
     */
    public static class CustomFontProvider implements FontProvider {
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

    public static void main(String[] args) throws DocumentException, IOException {
        FontFactory.defaultEmbedding = true;
        FontFactory.register("fonts/Helvetica.afm", BaseFont.HELVETICA);
        FontFactory.getFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        

        try {
            Document document = new Document(PageSize.A4);
            File pdfFile = File.createTempFile("iTextExample_HTML2PDF", ".pdf");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.setMargins(50, 50, 50, 50);

            document.open();
            document.addAuthor("Real Gagnon");
            document.addCreator("Real's HowTo");
            document.addSubject("Thanks for your support");
            document.addCreationDate();
            document.addTitle("Please read this");

            PdfContentByte canvas = writer.getDirectContent();

            HTMLWorker htmlWorker = new HTMLWorker(document);
            String str = "";

            str = "<span style=\"font-size: 10; \">I was at the railway station when I heard <font size=\"4\"><b>6 tolls</b></font> of the clock. It was <b><font size=\"4\">6 o'clock</font></b>. Out of curiosity I measured that it took <font><b><font face=\"times new roman\">30 seconds for 6 tolls</font></b></font>.&nbsp; I concluded that it will take <b><font>1 minute for 12 tolls at 12 o'clock</font></b>.&nbsp; Am I correct?</span>"
               + "<span style=\"font-size: 10;font-family: Arial;\">Can you recognize the following solid?<br></span>"
             + "<div align=\"center\"><b><span style=\"font-size: 10;font-family: Arial;\">&nbsp;There are five chains with 3 links in each chain. It takes $1 to break one link and $2 to join it back. What would it cost to make a single chain out of 5 chains?</span></b></div>"
             + "<span style=\"font-size: 10;font-family: Arial;\">Two trains started from stations A and B, 300 km apart with speeds 40 km/hr and 60 km/hr respectively towards each other at the same time. At the same time a bird started flying from station A to station B with speed 100 km/hr.&nbsp; On the flight when it reached the second train it turned back. When it reached the first train it turned back. This continued till the trains met each other on the way. Is the distance traveled by the bird deterministic?</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;How many <font size=\"5\"><b>rectangles </b></font>can be formed in the figure below?</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;What is the next number in the following sequence?<br><br>1&nbsp;&nbsp; &nbsp;11&nbsp;&nbsp; &nbsp;21&nbsp;&nbsp; &nbsp;1211&nbsp;&nbsp;&nbsp; ____<br></span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">John has few coins.<br></span><br><ul><li><span style=\"font-size: 10;font-family: Arial;\">He stacked them in 2 coins per stack. 1 coin left.&nbsp; <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He re-arranged them in stacks of 3, 1 coin left. <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He re-arranged them in stacks of 4, 1 coin left.&nbsp; <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He arranged them in stacks of 5, still 1 coin left. <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He arranged them in stacks of 6, again 1 coin left. <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">Finally with stacks of 7 all stacks were complete with no coin left.&nbsp;&nbsp;</span><span style=\"font-size: 10;font-family: Arial;\"></span></li></ul><span style=\"font-size: 10;font-family: Arial;\"><br>How many coins John has?&nbsp; <br></span><br>";
            
            
             str += "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Pyramid</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Cuboid</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Circular Prism<br></span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Cylinder</span>";
            
            
            
             str +=
             "<span><font><span><span><span><sub><font><font size=\"4\"><font face=\"tahoma\">$6</font></font></font></sub><br></span></span></span></font></span>"
             + "<font><u><font face=\"courier new\"><span style=\"font-size: 10;\">$9</span></font></u></font>"
             + "<font face=\"times new roman\"><span style=\"font-size: 10;\">&nbsp;$15</span></font>"
             + "<b><font><span style=\"font-size: 10;font-family: Arial;\">$12</span></font></b>";
            
            
             str += "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;120</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;140</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;150</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;130</span>";
            
             str +=
             "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;None of the above<br>.Do you really think it is a valid sequence?<br>I don't think so.</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;72111</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;2111</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;1221</span>";
            
            
             str += "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;121</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;None of the above<br></span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;49</span>"
             + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;63</span>";

            HashMap<String, Object> providers = new HashMap<String, Object>();
            providers.put("font_factory", new CustomFontProvider());

            ArrayList<Element> list = HTMLWorkerEx.parseToList(new StringReader(str), null, providers);
            for (Object line : list) {
                document.add((Element) line);
            }
            document.add(new Paragraph("hjgfhjfghjfghj" + "fghjfghjf", FontFactory.getFont(BaseFont.HELVETICA)));
            
             markCircle(canvas, 36, 36, 14);
             markCircle(canvas, 36, 806, 14);
             markCircle(canvas, 559, 806, 14);
             markCircle(canvas, 559, 36, 14);
            
             canvas.setRGBColorStroke(0, 0, 0);
             canvas.rectangle(45, 45, 505, 752);
             canvas.stroke();

            document.close();
            System.out.println("Done");
            Desktop.getDesktop().open(pdfFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}