package coordiate.tarnsform;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class ManipulateImage {

    static Image image;

    public static void main(String[] args) {

        // The image URL - change to where your image file is located!

        String imageURL = "img/rectangle_qr.png";

        // This call returns immediately and pixels are loaded in the background

        image = Toolkit.getDefaultToolkit().getImage(imageURL);

        // Create a frame

        Frame frame = new Frame();

        // Add a component with a custom paint method

        frame.add(new CustomPaintComponent());

        // Display the frame

        int frameWidth = 300;

        int frameHeight = 300;

        frame.setSize(frameWidth, frameHeight);

        frame.setVisible(true);
    }

    /*
     * To draw on the screen, it is first necessary to subclass a Component and override its paint() method. The paint() method is
     * automatically called by the windowing system whenever component's area needs to be repainted.
     */
    static class CustomPaintComponent extends Component {

        public void paint(Graphics g) {

            // Retrieve the graphics context; this object is used to paint shapes

            Graphics2D g2d = (Graphics2D) g;

            AffineTransform affineTransform = new AffineTransform();

            double scalex = .5;

            double scaley = 1;

            affineTransform.scale(scalex, scaley);

            double shiftx = .1;

            double shifty = .3;

            affineTransform.shear(shiftx, shifty);

            double x = 50;

            double y = 50;

            affineTransform.translate(x, y);

            double radians = -Math.PI / 4;

            affineTransform.rotate(radians);

            g2d.drawImage(image, affineTransform, this);

        }

    }

}
