package gm.utils.string;

import java.util.List;
import java.util.function.Predicate;

import gm.utils.classes.UClass;
import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.number.UInteger;
import gm.utils.number.UNumber;
import lombok.Getter;

@Getter
public class Java2 {

	private String file;
	private Class<?> classe;
	
	private ListString list;
	private ListString list2;
	private ListString imports = new ListString();
	private ListString comentariosDeLinha = new ListString();
	private ListString comentariosDeBlocoMesmaLinha = new ListString();
	private ListString comentariosDeBlocoStart = new ListString();
	private ListString comentariosDeBlocoMeio = new ListString();
	private ListString comentariosDeBlocoEnd = new ListString();
	private ListString strings = new ListString();
	private ListString stringsAspasSimples = new ListString();

	public Java2(String file) {
		if (UFile.exists(file)) {
			list = new ListString().load(file);
			list.rtrim();
			list.removeLastEmptys();
			while (UString.isEmpty(list.get(0))) {
				list.remove(0);
			}
			list.replaceTexto("http://", "$HTTP$");
			list.replaceTexto("\"\\\\\"", "$BARRABARRAASPAS$");
			list.replaceTexto("\\\"", "$BARRAASPAS$");
			readComentarios();
//			readImports();
			readStrings();			
		}
		
		this.file = file;
	}
	
	public String getString(int index) {
		String s = strings.get(index);
		s = s.replace("$BARRAASPAS$", "\\\"");
		s = s.replace("$BARRABARRAASPAS$", "\"\\\\\"");
		s = s.replace("$HTTP$", "http://");
		return s;
	}
	public String getStringAspa(int index) {
		String s = stringsAspasSimples.get(index);
		return s;
	}

	public void replaceStrings(ListString list) {
		for (int i = 0; i < strings.size(); i++) {
			list.replaceTexto("$STR"+UNumber.format00(i, 3)+"$", "\"" + getString(i) + "\"");	
		}
	}
	
	public void save() {
		replaceStrings(list);
		ListString filter = list.filter(s -> s.startsWith("/*") && s.endsWith("*/"));
		for (String s : filter) {
			String x = "//" + UString.ignoreRight(s.substring(2),2);
			list.replace(s, x);
		}
		list.save(file);
	}
	public void print() {
		replaceStrings(list);
		list.print();
	}
	
	public void add(String s) {
		list.add(s);
	}

	public Java2 copy() {
		return new Java2(file);
	}

	public ListString getList() {
		return list.copy();
	}

	public void save(ListString list) {
		this.list.clear();
		this.list.add(list);
		save();
	}

	public void removeIf(Predicate<String> filter) {
		list.removeIf(filter);
		list.removeLastEmptys();
	}

	public void removeLast() {
		list.removeLast();
		list.removeLastEmptys();
	}

	public void add() {
		list.add();
	}
	
	public Class<?> getClasse() {
		
		if (classe != null) {
			return classe;
		}

		for (String s : this.list) {
			if (s.startsWith("package ")) {
				s = UString.afterFirst(s, " ");
				s = UString.beforeLast(s, ";");
				s += "." + UString.afterLast(file, "/");
				s += "." + UString.beforeLast(s, ".java");
				classe = UClass.getClass(s);
				return classe;
			}
		}
		
		throw UException.runtime("N"+UConstantes.a_til+"o foi poss"+UConstantes.i_agudo+"vel determinar a classe");
		
	}

	public boolean contains(String s) {
		return getString().contains(s);
	}

	public String getString() {
		return list.toString(" ");
	}

	public void removeComentarios() {
		
		boolean repetir = true;
		
		while (repetir) {
			
			repetir = false;
			ListString lst = new ListString();

			while (!list.isEmpty()) {
				String s = list.remove(0);
				if (s.trim().startsWith("/*")) {
					while (!s.contains("*/")) {
						s = list.remove(0);
					}
					s = UString.afterFirst(s, "*/");
					if (!s.isEmpty()) {
						lst.add(s);
					}
					repetir = true;
				} else {
					lst.add(s);
				}
			}
			
			list.add(lst);
			
		}
		
		ListString lst = list.copy();
		list.clear();
		lst.removeIfTrimStartsWith("//");
		for (String s : lst) {
			if (s.contains("//")) {
				s = UString.beforeFirst(s, "//");
			}
			list.add(s);
		}
		
	}
	
	/**/
	
//	private void readImports() {
//		
//		boolean ja = false;
//		list2 = new ListString();
//		while (!list.isEmpty()) {
//			String s = list.remove(0);			
//			if (UString.isEmpty(s)) {
//				list2.add();
//				continue;
//			}
//			if (!s.startsWith("import ")) {
//				list2.add(s);
//				continue;
//			}
//			
//			while (!s.contains(";")) {
//				s += " " + list.remove(0).trim();
//			}
//			imports.add(s);
//			if (!ja) {
//				list2.add("$IMPORTS$");
//				ja = true;
//			}
//			
//		}
//		list = list2;
//	}

	private void readStrings() {
		list2 = new ListString();
		while (!list.isEmpty()) {
			String s = list.remove(0);			
			if (UString.isEmpty(s)) {
				list2.add();
				continue;
			}
			if (addStringAspasDuplas(s)) {
				continue;
			}
			if (addStringAspasSimples(s)) {
				continue;
			}
			list2.add(s);
		}
		list = list2;
	}

	private boolean addStringAspasDuplas(String s) {
		if (!s.contains("\"")) {
			return false;
		}
		while (s.contains("\"")) {
			String before = UString.beforeFirst(s, "\"");
			if (before == null) {
				before = "";
			} else if (before.contains("'")) {
				return false;
			}
			s = UString.afterFirst(s, "\"");
			String texto = UString.beforeFirst(s, "\"");
			String after = UString.afterFirst(s, "\"");
			
			s = before + "$STR"+f000(strings)+"$" + after;
			strings.add(texto);
			
		}
		
		list.add(0, s);
		
		return true;
	}

	private boolean addStringAspasSimples(String s) {
		
		if (!s.contains("'")) {
			return false;
		}
		String before = UString.beforeFirst(s, "'");
		if (before == null) {
			before = "";
		} else if (before.contains("\"")) {
			throw UException.runtime("nao deveria ocorrer");
		}
		s = UString.afterFirst(s, "'");
		String texto = UString.beforeFirst(s, "'");
		String after = UString.afterFirst(s, "'");
		
		s = before + "$CHR"+f000(strings)+"$" + after;
		list.add(0, s);
		stringsAspasSimples.add(texto);
		
		return true;
		
	}

	private void readComentarios() {
		list2 = new ListString();
		while (!list.isEmpty()) {
			String s = list.remove(0);
			
			if (UString.isEmpty(s)) {
				list2.add();
				continue;
			}
			
			if (addComentarioDeLinha(s)) {
				continue;
			}
			if (addComentarioDeBloco(s)) {
				continue;
			}
			
			list2.add(s);
			
		}
		list = list2;
	}
	
	private String f000(List<?> list) {
		return UNumber.format00(list.size(), 3);
	}

//	private static int vez = 0;
	
	private boolean addComentarioDeBloco(String s) {

		if (!s.contains("/*")) {
			return false;
		}
		
//		vez++;
//		
//		if (vez == 10) {
//			System.out.println("vez " + vez);
//		}
		
		String before = UString.beforeFirst(s, "/*");
		
		if (before != null && before.contains("\"")) {
			int ocorrencias = UString.ocorrencias(before, "\"");
			if (!UInteger.ehPar(ocorrencias)) {
				return false;
			}
		}
		
		s = UString.afterFirst(s, "/*");
		
		if (s.contains("*/")) {
			before += "$CBL"+f000(comentariosDeBlocoMesmaLinha)+"$";
			String comentario = UString.beforeFirst(s, "*/");
			comentariosDeBlocoMesmaLinha.add(tratarReplaces(comentario));
			list.add(0, before);//para ser novamente avaliada
			return true;
		}
		
		list2.add(before +"$CBS"+f000(comentariosDeBlocoStart)+"$");
		comentariosDeBlocoStart.add(s);
		
		while (true) {
			s = list.remove(0);
//			System.out.println(s);
			if (s.contains("*/")) {
				before = UString.beforeFirst(s, "*/");
				s = UString.afterFirst(s, "*/");
				list.add(0, "$CBE"+f000(comentariosDeBlocoEnd)+"$" + s);//para ser novamente avaliada
				s = tratarReplaces(before);
				comentariosDeBlocoEnd.add(s);
				return true;
			} else {
				list2.add("$CBM"+f000(comentariosDeBlocoMeio)+"$");
				s = tratarReplaces(s);
				comentariosDeBlocoMeio.add(s);
			}
		}
		
		
	}
	private boolean addComentarioDeLinha(String s) {
		
		if (!s.contains("//")) {
			return false;
		}
		
		String before = UString.beforeFirst(s, "//");
		
		if (before.contains("/*")) {
			return false;
		}
		
		if (!UInteger.ehPar(UString.ocorrencias(before, "\""))) {
			return false;
		}
		
		s = UString.afterFirst(s, "//");
		
		list2.add(before + "$CDL"+f000(comentariosDeLinha)+"$");
		s = tratarReplaces(s);
		comentariosDeLinha.add(s);
		
		return true;
	}
	
	private String tratarReplaces(String s) {
		s = s.replace("$BARRABARRAASPAS$", "\"\\\\\"");
		s = s.replace("$HTTP$", "http://");
		s = s.replace("$BARRAASPAS$", "\\\"");
		return s;
	}

//	list.replaceTexto
//	list.replaceTexto("\"\\\\\"", "$BARRABARRAASPAS$");
//	list.replaceTexto
	
}
