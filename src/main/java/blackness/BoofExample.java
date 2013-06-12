package blackness;

import java.awt.image.BufferedImage;
import java.io.File;

import boofcv.alg.filter.binary.BinaryImageOps;
import boofcv.alg.filter.binary.ThresholdImageOps;
import boofcv.alg.misc.ImageStatistics;
import boofcv.core.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.ImageFloat32;
import boofcv.struct.image.ImageSInt32;
import boofcv.struct.image.ImageUInt8;

public class BoofExample {

	/**
	 * http://boofcv.org/index.php?title=Example_Binary_Image 
	 * @param args
	 */
	public static void main(String[] args) {
		// load and convert the image into a usable format
		BufferedImage image = UtilImageIO
				.loadImage("img/blackness/10_11371023110546.png");

		File dir = new File("img/blackness");
		dir.listFiles();
		
		// convert into a usable format
		ImageFloat32 input = ConvertBufferedImage.convertFromSingle(image,
				null, ImageFloat32.class);
		ImageUInt8 binary = new ImageUInt8(input.width,input.height);
		ImageSInt32 label = new ImageSInt32(input.width,input.height);

		// the mean pixel value is often a reasonable threshold when creating a
		// binary image
		double mean = ImageStatistics.mean(input);
		System.out.println((1 - mean / 255.0));
		// create a binary image by thresholding
		ThresholdImageOps.threshold(input, binary, (float) mean, true);

		// remove small blobs through erosion and dilation
		// The null in the input indicates that it should internally declare the
		// work image it needs
		// this is less efficient, but easier to code.
		ImageUInt8 filtered = BinaryImageOps.erode8(binary, null);
		filtered = BinaryImageOps.dilate8(filtered, null);
		
		double mean2 = ImageStatistics.mean(input);
		System.out.println((1 - mean2 / 255));
	}

}
