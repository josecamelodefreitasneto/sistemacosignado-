package gm.utils.image;

import java.io.File;
import java.io.FileInputStream;

import gm.utils.exception.UException;

public class Image {

	private String caminho;
	
	public Image(String caminho) {
		this.caminho = caminho;
	}

	public byte[] getBytes() {
		File file = new File(caminho);
		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			byte[] ba = new byte[fileInputStream.available()]; 
			fileInputStream.read(ba);
			return ba;
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}

}
