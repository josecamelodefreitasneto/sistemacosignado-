package gm.utils.temp;

import java.io.File;
import java.util.List;

import gm.utils.files.UFile;
import gm.utils.string.ListString;

public class ContadorDeLinhas {
	public static void main(String[] args) {
		int linhas = 0;
//		String path = "/opt/desen/git/mobile-view/app/";
//		String path = "/opt/desen/git/webbanking-frontend/src";
//		String path = "/opt/desen/git/mobile/mobileIntraBack/src/main/java/";
		String path = "/opt/desen/git/mobile/mobileIntraFacade/src/main/java/";
		List<File> files = UFile.getAllFiles(path);
//		List<File> files = UFile.getAllFiles("/opt/desen/git/mobile-view/app/");
//		
		for (File file : files) {
			linhas += new ListString().load(file).size();
		}
		System.out.println(files.size());
		System.out.println(linhas);
	}
}
