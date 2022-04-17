package gm.utils.recuperarDaHistory;

import java.io.File;
import java.util.List;

import gm.utils.date.Data;
import gm.utils.files.UFile;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class Passo1_SalvarNaTemp {
	
	public static void main(String[] args) {
		File file = new File("/opt/desen/gm/workspace/.metadata/.plugins/org.eclipse.core.resources/.history/");
		exec(file);
	}
	
	private static ListString PACKAGES = ListString.array(
//		  "package procedureToJava;"
//		, "package consultas;"
//		, "package temp;"
//		, "package extratoDePontos;"
//		, "package transparencia;"
//		, "package teste;"
//		, "package atualizacao;"
//		, "package outros;"
//		, "package cooper;"
//		, "package jboss;"
//		, "package scr;"
//		, "package react;"
//		, "package exec.rotinas;"
		  "package testes;"
	);

	private static void exec(File path) {
		
		List<File> allFiles = UFile.getAllFiles(path);
		for (File file : allFiles) {
			ListString list = new ListString().load(file);
			if (list.isEmpty()) {
				continue;
			}
			while (!list.get(0).startsWith("package ")) {
				list.remove(0);
				if (list.isEmpty()) {
					break;
				}
			}
			if (list.isEmpty()) {
				continue;
			}
			String s = UString.trimPlus(list.get(0));
			
			if (PACKAGES.contains(s)) {
				
				String pack = UString.ignoreRigth(UString.afterFirst(s, " "));
				
				while (!list.get(0).startsWith("public class")) {
					list.remove(0);
				}
				
				s = list.get(0);
				s = UString.trimPlus(UString.afterFirst(s, "public class "));
				if (s.contains(" ")) {
					s = UString.beforeFirst(s, " ");
				}
				if (s.contains("{")) {
					s = UString.beforeFirst(s, "{");
				}
				
				String nome = pack + "." + s + "-"+new Data(file.lastModified()).format("[yyyy]-[mm]-[dd]-[hh]-[nn]-[ss]");
				list.clear();
				list.load(file);
				list.save("/tmp/recuperados/" + nome + ".java");
			}
		}
		
	}
	
}
