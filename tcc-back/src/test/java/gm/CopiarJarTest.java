package gm;

import org.junit.Test;

import gm.utils.files.UFile;

public class CopiarJarTest {

	@Test
	public void exec() {
		UFile.copy("target/tcc-back-0.jar", "../tcc-java/target/back.jar");
	}
	
	public static void main(String[] args) {
		new CopiarJarTest().exec();
	}
	
}
