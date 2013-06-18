package ImageDpi;

import org.w3c.dom.*;

import java.io.*;
import java.net.URLConnection;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

public class Demo {
    private int[] dpi = {0, 0};
    private ImageReader reader;
    private String fileName;
    
    
    public Demo(String fileName) {
        this.fileName = fileName;
    }
    
    public static void main(String[] args) {
        Demo meta = new Demo("img/scaned_files/tif_scan/SKMBT_22313061009400.tif");
        int length = args.length;
        // for ( int i = 0; i < length; i++ )
        meta.readAndDisplayMetadata();
    }

    void readAndDisplayMetadata() {
        try {

            File file = new File(fileName);
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (readers.hasNext()) {

                // pick the first available ImageReader
                reader = readers.next();

                // attach source to the reader
                reader.setInput(iis, true);

                // read metadata of first image
                IIOMetadata metadata = reader.getImageMetadata(0);
                
                String[] names = metadata.getMetadataFormatNames();
                int length = names.length;
                for (int i = 0; i < length; i++) {
                    displayMetadata(metadata.getAsTree(names[i]));
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    void displayMetadata(Node node, int level) {
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                if (attr.getNodeName() == "Xdensity") {
                    setDpiX(Integer.valueOf(attr.getNodeValue()));
                }
                if (attr.getNodeName() == "Ydensity") {
                    setDpiY(Integer.valueOf(attr.getNodeValue()));
                }
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            return;
        }

        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }
    }

    public int[] getDpi() {
        return dpi;
    }

    public void setDpiX(int dpiX) {
        this.dpi[0] = dpiX;
    }

    public void setDpiY(int dpiY) {
        this.dpi[1] = dpiY;
    }
    public ImageReader fileType() throws Exception {
    	//System.out.println(reader.getFormatName());
        return reader;
    }
        
    
}
