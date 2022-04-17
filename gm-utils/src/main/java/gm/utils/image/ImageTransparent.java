package gm.utils.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;

import javax.imageio.ImageIO;

import gm.utils.exception.UException;

public class ImageTransparent {
	public static void exec(File file) {
		exec(file, file);
	}
	public static void exec(File file, File fileOutput) {
		try {
			BufferedImage source = ImageIO.read(file);
			int color = source.getRGB(0, 0);
			Image image = makeColorTransparent(source, new Color(color));
			BufferedImage transparent = imageToBufferedImage(image);
			ImageIO.write(transparent, "PNG", fileOutput);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

    private static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }

    private static Image makeColorTransparent(BufferedImage im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {

            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                	Color waterMarkColor = new Color(212,212,212);
                	return waterMarkColor.getRGB();
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }	
	
}
