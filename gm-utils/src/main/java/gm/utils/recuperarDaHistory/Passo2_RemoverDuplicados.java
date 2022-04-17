package gm.utils.recuperarDaHistory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.files.UFile;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class Passo2_RemoverDuplicados {
	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<>();
		
		List<File> files = UFile.getAllFiles("/tmp/recuperados");
		
		for (File file : files) {
			String s = file.getName();
			String nome = UString.beforeFirst(s, "-");
			String hora = UString.afterFirst(s, "-");
			String atual = map.get(nome);
			if (atual == null || atual.compareTo(hora) < 0) {
				map.put(nome, hora);
			}
		}
		
		map.forEach((key, value) -> {
			ListString list = new ListString();
			list.load("/tmp/recuperados/" + key + "-" + value);
			list.save("/tmp/recuperados/ultimos/" + key + "-" + value);
		});
		
	}
}
