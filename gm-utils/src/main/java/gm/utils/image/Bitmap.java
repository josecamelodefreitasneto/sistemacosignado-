package gm.utils.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gm.utils.exception.UException;
import lombok.Getter;

@Getter
public class Bitmap {

	private BufferedImage image;
	private String caminho;
	
	public static void main(final String[] args) {
		final Bitmap bitmap = new Bitmap("/opt/desen/tmp/x.png");
		bitmap.removerUltimasLinhasEmBranco();
		bitmap.saveAs("/opt/desen/tmp/x1.png");
//		System.out.println(new RGB(255, 255, 255));
		
	}

	public Bitmap(final BufferedImage image) {
		this.image = image;
	}
	public Bitmap(final int width, final int height) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		this(new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR));
	}
	
	public Bitmap(final String caminho) {
		this.caminho = caminho;
		try {
			this.image = ImageIO.read(new File(caminho));
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}	
	public int get(final int x, final int y) {
		if (this.invalid(x, y)) return 0;
		return this.image.getRGB(x, y);
	}
	private boolean invalid(final int x, final int y) {
		return x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight();
	}
	public void set(final int x, final int y, final int rgb) {
		this.image.setRGB(x, y, rgb);
	}
	public void save() {
		this.saveAs(this.caminho);
	}
	public void saveAs(final String caminho) {
//		this.saveAs(caminho, "jpg");
		this.saveAs(caminho, "png");
	}
	public void saveAs(final String caminho, final String extensao) {
		try {
			ImageIO.write(this.image, extensao, new File(caminho));
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}
	public void removerUltimasLinhasEmBranco() {
		int y = this.getHeight()-1;
		while (this.rowIsBranco(y)) y--;
		this.image = this.image.getSubimage(0, 0, this.getWidth(), y);
	}
	public boolean rowIsBranco(final int y) {
		for (int x = 0; x < this.getWidth(); x++) {
			if (!this.isBranco(x, y)) {
				return false;
			}
		}
		return true;
	}
	private boolean isBranco(final int x, final int y) {
		return this.get(x, y) == Color.WHITE.getRGB();
	}
	public int getWidth() {
		return this.image.getWidth();
	}
	public int getHeight() {
		return this.image.getHeight();
	}
	public void setWidth(final int width) {
		this.setSize(width, this.getHeight());
	}
	public void setHeight(final int height) {
		this.setSize(this.getWidth(), height);
	}
	public void setSize(final int width, final int height) {
		final java.awt.Image img = this.image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		this.image = new BufferedImage(width, height, this.image.getType());
		final Graphics2D g2d = this.image.createGraphics();
		g2d.drawImage(img, 0, 0, null);
		g2d.dispose();
	}
}
