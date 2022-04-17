package gm.utils.recuperarDaHistory;

import java.io.File;
import java.util.List;

import gm.utils.files.UFile;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class Passo3_FiltrarPorData {
	public static void main(String[] args) {
		
		List<File> files = UFile.getAllFiles("/tmp/recuperados/ultimos");
		
		for (File file : files) {
			String s = file.getName();
			String hora = UString.afterFirst(s, "-");
			if ("2019-08-09-00-00-00".compareTo(hora) < 0) {
				ListString list = new ListString();
				list.load(file);
				list.save("/tmp/recuperados/filtrados/" + s);
			}
		}
		
	}
}
