package gm.utils.files;

import java.io.File;
import java.util.List;

import gm.utils.string.ListString;

public class ComparePaths {

	private static ListString diferentes = new ListString();
	
	public static void main(String[] args) {
		exec("/opt/desen/git/mobile-view/app/","/opt/desen/git/mobile-view-master/mobile-view/app/");
	}

	private static void exec(String path1, String path2) {
		execute(path1, path2);
		for (String s : diferentes) {
			System.out.println( "diff " + s + " " + s.replace(path1, path2) );
		}
		
	}
	
	private static void execute(String path1, String path2) {

		List<File> files = UFile.getFiles(path1);
		for (File file : files) {
			String p1 = file.toString();
			String p2 = p1.replace(path1, path2);
			if (!UFile.exists(p2)) {
				diferentes.addIfNotContains(p1);
			} else {
				ListString f1 = new ListString().load(p1);
				ListString f2 = new ListString().load(p2);
				if (!f1.eq(f2)) {
					if (!diferentes.contains(p1) && !diferentes.contains(p1.replace(path1, path2))) {
						diferentes.add(p1);	
					}
				}
			}
		}
		
		List<File> directories = UFile.getDirectories(path1);
		for (File file : directories) {
			String p1 = file.toString();
			String p2 = p1.replace(path1, path2);
			execute(p1, p2);
			execute(p2, p1);
		}
		
	}
	
}
