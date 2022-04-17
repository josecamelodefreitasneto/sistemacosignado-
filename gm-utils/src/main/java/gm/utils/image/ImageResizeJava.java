package gm.utils.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import gm.utils.exception.UException;
import gm.utils.number.UInteger;
import gm.utils.string.UString;

public class ImageResizeJava extends ImageResize {

	private BufferedImage originalImage;
	private int type;

	@Override
	protected void execute() {
		
		try {
			originalImage = ImageIO.read(new File(getOrigem()));
			calculaScala();
			type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
			BufferedImage resizeImage = resizeImageWithHint();
			String destino = getDestino();
			String extensao = UString.afterLast(destino, ".");
			ImageIO.write(resizeImage, extensao, new File(destino));
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}

	private BufferedImage resizeImageWithHint() {

		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
		g.dispose();
		
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return resizedImage;
	}
	
	
    private int newWidth;
    private int newHeight;
	
	
	public void calculaScala() {
		
		ImageIcon icon = new ImageIcon(originalImage);
		
		int originalWidth = icon.getIconWidth();
	    int originalHeight = icon.getIconHeight();
	    
	    Integer alturaMaxima = getAltura();
	    Integer larguraMaxima = getLargura();
	    int max;
	    
	    if (alturaMaxima == null) {
	    	alturaMaxima = larguraMaxima;
		} 
	    
	    if (larguraMaxima == null) {
			larguraMaxima = alturaMaxima;
		}
	    
		max = UInteger.menor(larguraMaxima, alturaMaxima);
	    
	    newWidth = originalWidth;
	    newHeight = originalHeight;

	    // first check if we need to scale width
	    if (originalWidth > max) {
	        //scale width to fit
	        newWidth = max;
	        //scale height to maintain aspect ratio
	        newHeight = (newWidth * originalHeight) / originalWidth;
	    }

	    // then check if we need to scale even with the new height
	    if (newHeight > max) {
	        //scale height to fit instead
	        newHeight = max;
	        //scale width to maintain aspect ratio
	        newWidth = (newHeight * originalWidth) / originalHeight;
	    }

	}	
	
}
