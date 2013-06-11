package pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.codec.TiffImage;

public class TiffToJpg {

	/**
	 * @param args
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			Exception {
		Document document = new Document();
		PdfWriter.getInstance(document,
				new FileOutputStream("SimpleImages.pdf"));
		document.open();
		RandomAccessFileOrArray ra = new RandomAccessFileOrArray(
				"img/scaned_files/TIF_Scan/SKMBT_22313061009400.tif");
		int pages = TiffImage.getNumberOfPages(ra);
		for (int i = 1; i <= pages; i++) {
			document.add(TiffImage.getTiffImage(ra, i));
		}

		document.close();

	}

}
