package gm.utils.pom;

import java.io.File;
import java.util.List;

import gm.utils.date.Data;
import gm.utils.files.UFile;

public class PomVersionar {
	
	private long lastModified;
	private static final String FORMAT = "[yyyy].[mm].[dd][hh][nn][ss]"; 
	
	private PomVersionar(String path) {
		lastModified = new File(path + "/pom.xml").lastModified();
		Pom pom = new Pom(path);
		Data versao = Data.unformat(FORMAT, pom.getVersion());
		setLastModified(new File(path));
		Data data = new Data(lastModified);
		
		if (data.diferenca(versao).emSegundos() > 5) {
			data = new Data();
			pom.setVersion(data.format(FORMAT));
			pom.save();
			AjustarVersaoNosPoms.exec(pom);
		}
	}

	public static void main(String[] args) {
		exec("/opt/desen/gm/cs2019/gm-utils");
	}
	public static void exec(String path) {
		new PomVersionar(path);
	}

	private void setLastModified(File path) {
		List<File> files = UFile.getFiles(path);
		files.removeIf(o -> o.getName().contentEquals("pom.xml"));
		for (File file : files) {
			if (file.lastModified() > lastModified) {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>> " + file);
				lastModified = file.lastModified(); 
			}
		}
		List<File> directories = UFile.getDirectories(path);
		directories.removeIf(o -> o.getName().contentEquals("test"));
		directories.removeIf(o -> o.getName().contentEquals("target"));
		directories.removeIf(o -> o.getName().contentEquals("node_modules"));
		for (File directory : directories) {
			setLastModified(directory);
		}
	}
	
}
