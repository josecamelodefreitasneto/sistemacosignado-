package gm;

import java.io.File;
import java.util.List;

import gm.utils.files.UFile;

public class Bla {

	public static void main(String[] args) {
		
		List<File> list = UFile.getAllFiles("/opt/desen/gm/cs2019/gm-frame-constructor/fc-core-back/src/");

		String a = "/opt/desen/gm/cs2019/gm-frame-constructor/fc-core-back/";
		String b = "/opt/desen/gm/cs2019/extras/tcc/tcc-back/";
		
		for (File file : list) {
			String s = file.toString();
			s = s.replace(a, b);
			UFile.copy(file, new File(s));
		}
		
//		/opt/desen/gm/cs2019/gm-frame-constructor/fc-core-back/src/main/java/br/auto/controllers/AnoMesControllerAbstract.java
//		              /opt/desen/gm/cs2019/extras/tcc/tcc-back/src/main/java/br/auto/controllers/AtendenteControllerAbstract.java
	}
	
}
