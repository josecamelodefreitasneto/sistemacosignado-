package gm.utils.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class PdfToBase64 {
	
	private static final String filePath = "/home/cooperforte.coop/gamarra/Downloads/";
	private static final String originalFileName = "folhaponto_2738_5_2019.pdf";
	private static final String newFileName = "test.pdf";

	public static void main(String[] args) throws IOException {
        byte[] input_file = Files.readAllBytes(Paths.get(filePath+originalFileName));
        byte[] encodedBytes = Base64.getEncoder().encode(input_file);
        String base64 =  new String(encodedBytes);
        exec(base64);
	}
	
	public static void exec(String base64) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64.getBytes());
        FileOutputStream fos = new FileOutputStream(filePath+newFileName);
        fos.write(decodedBytes);
        fos.flush();
        fos.close();
		
	}
	
}
