package demoTest;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;

import org.junit.Test;

import demo.FontEmbedder;
import demo.FontEmbedderTemp;

public class TestFontEmbedderTemp {

    private String fontDirectory;

    public TestFontEmbedderTemp() {
        URL resource = getClass().getClassLoader().getResource("fonts");
        this.fontDirectory = "fonts";//resource.getPath();
    }

    //@Test
    public void renderPdfWithoutFontEmbeddig() throws Exception {
        FontEmbedderTemp embedder = new FontEmbedderTemp("TestFontEmbedder_PdfWithoutFontEmbeddig", fontDirectory);
        File pdfFile = embedder.renderText("The quick brown fox jumps over the lazy dog");
        System.out.println("renderPdfWithoutFontEmbeddig : " + pdfFile);
        Desktop.getDesktop().open(pdfFile);
    }

    @Test
    public void renderPdfWithFontEmbeddig() throws Exception {
        FontEmbedderTemp embedder = new FontEmbedderTemp("TestFontEmbedder_PdfWithFontEmbeddig", fontDirectory);
        embedder.registerFonts();
        File pdfFile = embedder.renderText("The quick brown fox jumps over the lazy dog");
        System.out.println("renderPdfWithFontEmbeddig : " + pdfFile);
        Desktop.getDesktop().open(pdfFile);
    }

    @Test
    public void renderHTMLPdfWithFontEmbeddig() throws Exception {
        String str =
            "<span style=\"font-size: 10; \">I was at the railway station when I heard <font size=\"4\"><b>6 tolls</b></font> of the clock. It was <b><font size=\"4\">6 o'clock</font></b>. Out of curiosity I measured that it took <font><b><font face=\"times new roman\">30 seconds for 6 tolls</font></b></font>.&nbsp; I concluded that it will take <b><font>1 minute for 12 tolls at 12 o'clock</font></b>.&nbsp; Am I correct?</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">Can you recognize the following solid?<br></span>"
                + "<div align=\"center\"><b><span style=\"font-size: 10;font-family: Arial;\">&nbsp;There are five chains with 3 links in each chain. It takes $1 to break one link and $2 to join it back. What would it cost to make a single chain out of 5 chains?</span></b></div>"
                + "<span style=\"font-size: 10;font-family: Arial;\">Two trains started from stations A and B, 300 km apart with speeds 40 km/hr and 60 km/hr respectively towards each other at the same time. At the same time a bird started flying from station A to station B with speed 100 km/hr.&nbsp; On the flight when it reached the second train it turned back. When it reached the first train it turned back. This continued till the trains met each other on the way. Is the distance traveled by the bird deterministic?</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;How many <font size=\"5\"><b>rectangles </b></font>can be formed in the figure below?</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;What is the next number in the following sequence?<br><br>1&nbsp;&nbsp; &nbsp;11&nbsp;&nbsp; &nbsp;21&nbsp;&nbsp; &nbsp;1211&nbsp;&nbsp;&nbsp; ____<br></span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">John has few coins.<br></span><br><ul><li><span style=\"font-size: 10;font-family: Arial;\">He stacked them in 2 coins per stack. 1 coin left.&nbsp; <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He re-arranged them in stacks of 3, 1 coin left. <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He re-arranged them in stacks of 4, 1 coin left.&nbsp; <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He arranged them in stacks of 5, still 1 coin left. <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">He arranged them in stacks of 6, again 1 coin left. <br></span></li><li><span style=\"font-size: 10;font-family: Arial;\">Finally with stacks of 7 all stacks were complete with no coin left.&nbsp;&nbsp;</span><span style=\"font-size: 10;font-family: Arial;\"></span></li></ul><span style=\"font-size: 10;font-family: Arial;\"><br>How many coins John has?&nbsp; <br></span><br>";

        str +=
            "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Pyramid</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Cuboid</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Circular Prism<br></span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;Cylinder</span>";

        str +=
            "<span><font><span><span><span><sub><font><font size=\"4\"><font face=\"tahoma\">$6</font></font></font></sub><br></span></span></span></font></span>"
                + "<font><u><font face=\"courier new\"><span style=\"font-size: 10;\">$9</span></font></u></font>"
                + "<font face=\"times new roman\"><span style=\"font-size: 10;\">&nbsp;$15</span></font>"
                + "<b><font><span style=\"font-size: 10;font-family: Arial;\">$12</span></font></b>";

        str +=
            "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;120</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;140</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;150</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;130</span>";

        str +=
            "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;None of the above<br>.Do you really think it is a valid sequence?<br>I don't think so.</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;72111</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;2111</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;1221</span>";

        str +=
            "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;121</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;None of the above<br></span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;49</span>"
                + "<span style=\"font-size: 10;font-family: Arial;\">&nbsp;63</span>";

        FontEmbedderTemp embedder = new FontEmbedderTemp("TestFontEmbedder_HTMLPdfWithFontEmbeddig", fontDirectory);
        embedder.registerFonts();
        File pdfFile = embedder.renderHTML(str);
        System.out.println("renderPdfWithFontEmbeddig : " + pdfFile);
        Desktop.getDesktop().open(pdfFile);
    }
}
