package gm.utils.image;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

import gm.utils.exception.UException;

public class UImage {
	public byte[] getBytes(String url) {
		try {
			File imgPath = new File(url);
			BufferedImage bufferedImage = ImageIO.read(imgPath);
			WritableRaster raster = bufferedImage.getRaster();
			DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
			return data.getData();
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

}
