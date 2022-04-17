package gm.utils.string;

import java.util.function.Predicate;

import gm.utils.classes.UClass;
import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import lombok.Getter;

@Getter
public class Java {

	private String file;
	private final ListString list = new ListString();
	private Class<?> classe;
	
	private ListString strings = new ListString();

	public Java(String file) {
		this.file = file;
		reLoad();
	}

	public void reLoad() {
		
		list.clear();

		if (UFile.exists(file)) {
			ListString lst = new ListString().load(file);
			lst.removeLastEmptys();
			while (UString.isEmpty(lst.get(0))) {
				lst.remove(0);
			}
			
			int index = 0;
			ListString lst2 = new ListString();
			
			for (String s : lst) {
				while (s.contains("\"")) {
					String before = UString.beforeFirst(s, "\"");
					s = UString.afterFirst(s, "\"");
					String string = UString.beforeFirst(s, "\"");
					strings.add(string);
					s = UString.afterFirst(s, "\"");
					s = before + "[STRING"+index+"]" + s;
					index++;
				}
				lst2.add(s);
			}
			
			for (String s : lst2) {
				if (s.contains("//")) {
					String s2 = UString.afterFirst(s, "//");
					s = UString.beforeFirst(s, "//");
					s += "/*"+s2+"*/"; 
				}
				list.add(s);
			}
		}
		
	}

	public void replaceStrings(ListString list) {
		for (int i = 0; i < strings.size(); i++) {
			list.replaceTexto("[STRING"+i+"]", "\"" + strings.get(i) + "\"");	
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

	public Java copy() {
		return new Java(file);
	}

	public ListString getList() {
		reLoad();
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
	
}
