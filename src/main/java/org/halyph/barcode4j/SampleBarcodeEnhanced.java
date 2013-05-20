package org.halyph.barcode4j;

/*
 * Copyright 2010 Jeremias Maerki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id: SampleBarcodeEnhanced.java,v 1.1 2010/10/05 08:56:04 jmaerki Exp $ */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.datamatrix.SymbolShapeHint;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoder;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoderRegistry;
import org.krysalis.barcode4j.tools.UnitConv;

/**
 * This class demonstrates how to create a barcode bitmap that is enhanced by custom elements.
 *
 * @version $Id: SampleBarcodeEnhanced.java,v 1.1 2010/10/05 08:56:04 jmaerki Exp $
 */
public class SampleBarcodeEnhanced {

    private void generate(File outputFile) throws IOException {
        String msg = "TTT OIOrest Ivasiv 50e8400-e29b-41d4-a716-446655440000| 550e8400-e29b-41d4-a716-446655440000  tralalala";
//        String[] paramArr = new String[] {"Information 1", "Information 2", "Barcode4J is cool!"};

        //Create the barcode bean
        DataMatrixBean bean = new DataMatrixBean();

        final int dpi = 300;

        //Configure the barcode generator
        bean.setModuleWidth(UnitConv.in2mm(5f / dpi)); //makes a dot/module exactly eight pixels
        bean.doQuietZone(true);
        bean.setShape(SymbolShapeHint.FORCE_SQUARE);

        boolean antiAlias = false;
        int orientation = 0;
        //Set up the canvas provider to create a monochrome bitmap
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                dpi, BufferedImage.TYPE_BYTE_BINARY, antiAlias, orientation);

        //Generate the barcode
        bean.generateBarcode(canvas, msg);

        //Signal end of generation
        canvas.finish();

        //Get generated bitmap
        BufferedImage symbol = canvas.getBufferedImage();

        int width = symbol.getWidth();
        int height = symbol.getHeight();

        //Add padding
        int padding = 10;
        width += 2 * padding;
        height += 3 * padding;

        BufferedImage bitmap = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = bitmap.createGraphics();
        g2d.setBackground(Color.white);
        g2d.setColor(Color.black);
        g2d.clearRect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        //Place the barcode symbol
        AffineTransform symbolPlacement = new AffineTransform();
        symbolPlacement.translate(padding, padding);
        g2d.drawRenderedImage(symbol, symbolPlacement);

        //Encode bitmap as file
        String mime = "image/png";
        OutputStream out = new FileOutputStream(outputFile);
        try {
            final BitmapEncoder encoder = BitmapEncoderRegistry.getInstance(mime);
            encoder.encode(bitmap, out, mime, dpi);
        } finally {
            out.close();
        }
    }

    /**
     * Command-line program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        try {
            SampleBarcodeEnhanced app = new SampleBarcodeEnhanced();
            File outputFile = new File("out.png");
            app.generate(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
