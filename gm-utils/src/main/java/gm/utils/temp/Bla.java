package gm.utils.temp;

import java.io.File;
import java.util.List;

import gm.utils.files.UFile;

public class Bla {

	public static void main(String[] args) {
		exec(new File("/opt/desen/svn/conciliacao"));
//		.config
	}

	private static void exec(File dir) {
		List<File> directories = UFile.getDirectories(dir);
		for (File file : directories) {
			if (!file.getName().startsWith(".")) {
				exec(file);
			}
		}
		
		List<File> files = UFile.getFiles(dir);
		for (File file : files) {
			String s = file.toString().replace("/opt/desen/svn/conciliacao/", "/opt/desen/svn-copy/conciliacao/");
			UFile.copy(file, new File(s));
		}
		
	}
	
}
