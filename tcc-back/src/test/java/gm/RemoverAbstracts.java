//package gm;
//
//import br.impl.controllers.AtendenteController;
//import br.impl.service.AtendenteService;
//import br.impl.service.AuditoriaCampoBox;
//import br.impl.service.AuditoriaEntidadeBox;
//import br.impl.service.ClienteService;
//import br.impl.service.IDS;
//import br.impl.service.IDSDefault;
//import gm.utils.classes.ListClass;
//import gm.utils.classes.UClass;
//import gm.utils.config.ConfigAuto;
//import gm.utils.files.UFile;
//import gm.utils.string.ListString;
//import gm.utils.string.UString;
//
//public class Removers {
//	
//	private static final boolean ajuste = false;
//
//	public static void main(String[] args) {
//		ConfigAuto.config();
//		if (ajuste) {
//			exec(ClienteService.class);
//		} else {
//			execPackage(AtendenteController.class);
//			execPackage(AtendenteService.class);
//			move(IDS.class);
//			move(IDSDefault.class);
//			move(AuditoriaCampoBox.class);
//			move(AuditoriaEntidadeBox.class);
//		}
//	}
//
//	private static void execPackage(Class<?> classeBase) {
//		ListClass classes = UClass.classesDaMesmaPackageESubPackages(classeBase);
//		classes.remove(IDS.class);
//		classes.remove(IDSDefault.class);
//		classes.remove(AuditoriaCampoBox.class);
//		classes.remove(AuditoriaEntidadeBox.class);
//		for (Class<?> classe : classes) {
//			exec(classe);
//		}
//	}
//	
//	private static void move(Class<?> classe) {
//		String fileA = UClass.javaFileName(classe);
//		ListString list = load(fileA);
//		list.add(0, list.remove(0).replace(".auto.", ".impl."));
//		String fileB = fileA.replace("/auto/", "/impl/");
//
//		if (ajuste) {
//			list.print();
//		} else {
//			list.save(fileB);
//			UFile.delete(fileA);
//		}
//
//	}
//
//	private static void exec(Class<?> classe) {
//
//		String fileA = UClass.javaFileName(classe);
//		ListString a = load(fileA);
//		a.replaceTexto("", "");
//		a.replaceTexto("public  class ", "public class ");
//		a.removeLastEmptys();
//
//		String pacoteAbastract = UString.ignoreRight(UString.afterFirst(a.remove(0), " "), 1);
//		String import = "import " + pacoteAbastract + "." + classe.getSimpleName() + ";";
//		
//		String fileB = fileA.replace("/auto/", "/impl/").replace(".", ".");
//
//		ListString b = load(fileB);
//		b.remove(import);
//		b.replaceTexto("", "");
//		b.removeLastEmptys();
//		
//		String pacote = b.remove(0);
//
//		ListString imports = new ListString();
//		addImports(imports, a);
//		addImports(imports, b);
//		
//		ListString autowireds = a.filter(s -> s.trim().startsWith("@Autowired"));
//		for (String s : autowireds) {
//			b.remove(s);
//		}
//		
//		ListString metodosAbstratos = a.removeIfContains("  ");
//
//		for (String s : metodosAbstratos) {
//			s = s.replace("  ", " ");
//			s = UString.ignoreRight(s, 1);
//			s += " {";
//			int i = b.indexOf(s);
//			if (i == -1) {
//				throw new RuntimeException();
//			}
//			if (!b.remove(i-1).trim().contentEquals("@Override")) {
//				throw new RuntimeException();
//			}
//		}
//		
//		String anotacao = b.remove(0);
//		
//		b.remove(0);
//
//		if (!b.isEmpty()) {
//			a.removeLast();
//			a.add(b);
//		}
//
//		ListString list = new ListString();
//		list.add(pacote);
//		list.add();
//		list.add(imports);
//		list.add();
//		list.add(anotacao);
//		list.add(a);
//
//		if (ajuste) {
//			list.print();
//		} else {
//			list.save(fileB);
//			UFile.delete(fileA);
//		}
//		
//	}
//
//	private static ListString load(String file) {
//		ListString list = new ListString();
//		list.load(file);
//		list.replaceTexto("(final ", "(");
//		list.replaceTexto("){", ") {");
//		tratarAutowired(list);
//		return list;
//	}
//
//	private static void tratarAutowired(ListString a) {
//		a.juntarFimComComecos("@Autowired", "protected", " ");
//		a.replaceTexto("@Autowired protected", "@Autowired");
//		a.juntarFimComComecos("@Autowired", "private", " ");
//		a.replaceTexto("@Autowired private", "@Autowired");
//	}
//
//	private static void addImports(ListString imports, ListString a) {
//		a.removeFisrtEmptys();
//		while (a.get(0).startsWith("import")) {
//			imports.addIfNotContains(a.remove(0));
//			a.removeFisrtEmptys();
//		}
//	}
//	
//}
