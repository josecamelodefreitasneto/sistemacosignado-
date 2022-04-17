package gm.utils.classes;

import java.io.File;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.util.Utils;

import gm.utils.anotacoes.Titulo;
import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.comum.ULog;
import gm.utils.comum.UType;
import gm.utils.config.UConfig;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.map.MapSO;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UClass {
	
	private static Map<Class<?>, Class<?>> classesJaEncontradas = new HashMap<>();
	private static Map<String, Class<?>> nomesJaEncontrados = new HashMap<>();
	
	public static void addClassReplace(Class<?> de, Class<?> para) {
		classesJaEncontradas.put(de, para);
	}

	public static <T> Class<T> getClass(Object o) {
		return getClass(o.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(Class<?> classe) {
		Class<?> classe2 = classesJaEncontradas.get(classe);
		if (classe2 != null) {
			if (classe2 == classe) {
				return (Class<T>) classe;
			} else {
				return getClass(classe2);
			}
		}
		String s = classe.getName();
		classe2 = getClass(s);
		if (classe2 == null) {
			classe2 = classe;
		} else if (classe != classe2 && s.contentEquals(classe2.getName())) {
			classe2 = classe;
		}
		classesJaEncontradas.put(classe, classe2);
		return (Class<T>) classe2;
	}
	
	public static <T> Class<T> getClassObrig(String s) {
		Class<T> classe = getClass(s);
		if (classe == null) {
			throw UException.runtime("Classe n"+UConstantes.a_til+"o encontrada: " + s);
		}
		return classe;
	}
	
	public static ListClass tiposJava = new ListClass(
			  String.class, Object.class
			, Integer.class, int.class
			, Double.class, double.class
			, Data.class, Calendar.class, Date.class
			, Float.class, float.class
			, Long.class, long.class
			, Boolean.class, boolean.class
			, BigDecimal.class
	);
	
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(String s) {
		
		if (s.contentEquals("T")) {
			return (Class<T>) Object.class;
		}
		
		Class<?> classe = nomesJaEncontrados.get(s);
		
		if (classe != null) {
			Class<?> classe2 = classesJaEncontradas.get(classe);
			if (classe2 != null && classe2 != classe) {
				return getClass(classe2);
			} else {
				return (Class<T>) classe;
			}
		}

		final String SS = UConstantes.cifrao + UConstantes.cifrao;
		
		// verificar se eh um proxy
		if (s.contains("_"+SS+"_")) {
			s = UString.beforeFirst(s, "_"+SS+"_");
		}
		if (s.contains(SS)) {
			s = UString.beforeFirst(s, SS);
		}
		if (s.contains(UConstantes.cifrao+"HibernateProxy"+UConstantes.cifrao)) {
			s = UString.beforeFirst(s, UConstantes.cifrao+"HibernateProxy"+UConstantes.cifrao);
		}
		if (s.startsWith("class ")) {
			s = UString.afterFirst(s, " ");
		}
		
		for (Class<?> tipo : tiposJava) {
			if (s.equalsIgnoreCase(tipo.getSimpleName())) {
				nomesJaEncontrados.put(s, tipo);
				return (Class<T>) tipo;
			}
		}
		
		try {
			classe = Class.forName(s);
			nomesJaEncontrados.put(s, classe);
			return (Class<T>) classe;
		} catch (ClassNotFoundException e) {
			
			String before = UString.beforeLast(s, ".");
			String after = UString.afterLast(s, ".");
			String s2 = before + "$" + after;

			try {
				classe = Class.forName(s2);
				nomesJaEncontrados.put(s, classe);
				nomesJaEncontrados.put(s2, classe);
				return (Class<T>) classe;
			} catch (ClassNotFoundException e2) {
				return null;
			}
			
		}
	}

	public static <T> T newInstance(String nomeClasse) {
		return newInstance(getClassObrig(nomeClasse));
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> classe) {
		if (classe.equals(java.sql.Date.class)) {
			return (T) new java.sql.Date(0L);
		}
//		if (classe.equals(java.util.List.class)) {
//			return (T) new ArrayList<>();
//		}
//		java.util.List
		UAssert.notEmpty(classe, "classe == null");
		Object[] parameters = null;
		return newInstance(classe, parameters);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> classe, Object... parameters) {

		UAssert.notEmpty(classe, "classe == null");
		classe = getClass(classe);
		
		if (classe.equals(Integer.class)) {
			Integer i = 0;
			return (T) i;
		}
		if (classe.equals(String.class)) {
			String s = "";
			return (T) s;
		}
		try {
			
			if (parameters == null || parameters.length == 0) {
				return classe.getDeclaredConstructor().newInstance();
//				return classe.newInstance();
			}
			
			Constructor<?>[] constructors = classe.getConstructors();
			List<Constructor<?>> list = new ArrayList<>();
			for (Constructor<?> constructor : constructors) {
				if (parameters.length == constructor.getParameterCount()) {
					list.add(constructor);
				}
			}
			
			List<Constructor<?>> list2 = new ArrayList<>();
			for (Constructor<?> constructor : list) {
				Class<?>[] types = constructor.getParameterTypes();
				int i = 0;
				for (Class<?> type : types) {
					Object p = parameters[i];
					if ( p == null || isInstanceOf(p, type) ) {
						list2.add(constructor);
					}
				}
			}
			
			if (list2.isEmpty()) {
				String s = classe.getSimpleName() + "(" + parameters + ")";
				throw UException.runtime("N"+UConstantes.a_til+"o foi encontrado um construtor adequado: " + s);			
			}
			if (list2.size() > 1) {
				String s = classe.getSimpleName() + "(" + parameters + ")";
				throw UException.runtime("Existem mais de um construtor: " + s);			
			}
			Constructor<?> constructor = list2.remove(0);

			Object p0 = parameters[0];
			if (parameters.length == 1) {
					return (T) constructor.newInstance(p0);
			} else {
				Object p1 = parameters[1];
				if (parameters.length == 2) {
					return (T) constructor.newInstance(p0, p1);
				} else {
					Object p2 = parameters[2];
					if (parameters.length == 3) {
						return (T) constructor.newInstance(p0, p1, p2);
					} else {
						throw UException.runtime("Na epoca da implementa"+UConstantes.cao+" deste metodo "
								+ "n"+UConstantes.a_til+"o havia como passar um array como argumento "
								+ "para um newInstance de um contrutor. Desta forma "
								+ "era necessario fazer um if para cada quantidade "
								+ "de argumentos. Soh foi implementado ateh 3 argumentos. "
								+ " "+UConstantes.E_agudo+" necessario implementar mais situacoes nesta classe.");
					}
				}
			}
		
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				RuntimeException me = (RuntimeException) cause;
				throw me;
			}
			throw UException.runtime("Erro ao tentar instanciar a classe: " + classe.getName());
		}
	}

	public static boolean isInstanceOf(Object a, Class<?> c) {
		Class<?> classe = a.getClass();
		return instanceOf(classe, c);
	}
	
	public static boolean instanceOf(Class<?> classe, Class<?>... list) {

		for (Class<?> c : list) {
			
			if (a_herda_b(classe, c)) {
				return true;
			}
			
			Class<?> x = classe;
			
			while (x != null) {
				if (c.equals(x)) return true;
				x = x.getSuperclass();
			}
			
		}
		
		// TODO preciso completar com interfaces
		return false;
	}
	
	public static boolean a_herda_b(Class<?> a, Class<?> b) {
		if (a == null) throw UException.runtime("a == null");
		if (b == null) throw UException.runtime("b == null");
		return b.isAssignableFrom(a);
	}

	public static boolean isAbstract(Class<?> classe) {
		int modifiers = classe.getModifiers();
		return modifiers >= 1024;
	}
	
	private static ListString sources = ListString.array("main","test");
	
	public static String javaFileName(Class<?> classe) {
		return javaFileName(classe, true);
	}
	
	private static ListString pathRaizProjetos;
	
	private static ListString getPathRaizProjetos() {
		if (pathRaizProjetos == null) {
			String s = System.getProperty("user.dir") + "/src/main/java/";
			if (UConfig.get() == null) {
				pathRaizProjetos = new ListString();
				pathRaizProjetos.add(s);
			} else {
				pathRaizProjetos = UConfig.get().getPathRaizProjetos();
				pathRaizProjetos.setBloqueada(false);
				pathRaizProjetos.addIfNotContains(s);
				pathRaizProjetos.setBloqueada(true);
			}
			if (s.startsWith("/opt/desen/gm/cs2019/reacts/")) {
				pathRaizProjetos.setBloqueada(false);
				pathRaizProjetos.addIfNotContains("/opt/desen/gm/cs2019/reacts/react/src/main/java/");
				pathRaizProjetos.setBloqueada(true);
			}
			pathRaizProjetos.setBloqueada(false);
			pathRaizProjetos.addIfNotContains("/opt/desen/gm/cs2019/gm-utils/src/main/java/");
			pathRaizProjetos.setBloqueada(true);
		}
		return pathRaizProjetos;
	}
	
	public static String javaFileName(Class<?> classe, boolean obrig) {

//		/opt/desen/gm/cs2019/reacts/recat-mobile-cooper/src/main/java/src/app/misc/services/Service.java
//		/opt/desen/gm/cs2019/reacts/react-mobile-cooper
//		System.out.println(System.getProperty("user.dir"));
		
		ListString locaisProcurados = new ListString();
		
		String caminho = "/src/%s/java/" + classe.getName().replace(".", "/") + ".java";
		
		for (String pathRaiz : getPathRaizProjetos()) {
			if (pathRaiz.contains("/src/")) {
				pathRaiz = UString.beforeFirst(pathRaiz, "/src/");
			}
			for (String source : sources) {
				String path = pathRaiz + String.format(caminho, source);
				if (UFile.exists(path)) {
					return path;
				} else if (UFile.exists("/"+path)) {
					return "/"+path;
				} else {
					locaisProcurados.add(path);
				}
				
			}
		}
		
		if (obrig) {
			locaisProcurados.print();
			throw UException.runtime("Nao encontrato: " + classe.getName());
		} else {
			return null;
		}
		
	}

	public static ListClass classesDaMesmaPackage(Class<?> classe) {
		return classesDaMesmaPackage(classe, "");
	}
	
	public static ListClass classesDaMesmaPackageESubPackages(Class<?> classe) {

		String s = UClass.javaFileName(classe);
		s = UString.beforeLast(s, "/");
		s = UString.afterLast(s, "/src/main/");
		
		ListClass list = new ListClass();
		
		ListString projetos = UConfig.get().getPathRaizProjetos();
		
		for (String path : projetos) {
			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			UFile.assertExists(path);
			path = path + s;
			if (UFile.exists(path)) {
				addAllAndSubs(list, new File(path));
			}
		}
		
		return list;
		
	}
	
	private static void addAllAndSubs(ListClass list, File path) {

		List<File> files = UFile.getFiles(path, "java");
		for (File file : files) {
			String s = file.toString();
			s = UString.afterFirst(s, "/src/main/java/");
			s = UString.beforeLast(s, ".");
			s = s.replace("/",".");
			list.add( getClassObrig(s) );
		}
		
		List<File> directories = UFile.getDirectories(path);
		for (File diretorio : directories) {
			addAllAndSubs(list, diretorio);
		}
		
	}

	public static ListClass classesDaMesmaPackageESubPackagesOld(Class<?> classe) {
		String s = UClass.javaFileName(classe);
		s = UString.beforeLast(s, "/");
		
		List<File> allFiles = UFile.getAllFiles(s);
		allFiles.get(0).getName();
		allFiles.sort((a, b) -> a.getName().compareTo(b.getName()));
		ListClass list = new ListClass();
		for (File file : allFiles) {
			s = file.toString();
			if (!s.endsWith(".java")) {
				continue;
			}
			s = UString.afterFirst(s, "/src/main/java/");
			s = UString.beforeLast(s, ".");
			s = s.replace("/",".");
			list.addIfNotContains(getClassObrig(s));
			
		}
		return list;
	}
	
	public static ListClass classesDaMesmaPackage(Class<?> classe, String subpackage) {
		
		String pathPrincipal = classe.getSimpleName() + ".class";
		URL resource = classe.getResource(pathPrincipal);
		if (resource == null) {
			throw new NullPointerException();
		}
		pathPrincipal = resource.getFile();
		pathPrincipal = pathPrincipal.replace("file:", "");
		pathPrincipal = pathPrincipal.replace(classe.getSimpleName() + ".class", "");
		if (!UString.isEmpty(subpackage)) {
			pathPrincipal += subpackage + "/";
		}
		
		ListClass list = fromPath(pathPrincipal);
		
		ListString pathRaizProjetos = UConfig.get().getPathRaizProjetos();
		
		final String s;
		
		if (pathPrincipal.contains(".jar!")) {
			s = "/target/classes/" + UString.afterFirst(pathPrincipal, ".jar!/");	
		} else {
			s = "/target/" + UString.afterFirst(pathPrincipal, "/target/");
		}
		
		for (String path : pathRaizProjetos) {
			
			path = UString.beforeFirst(path, "/src/main/") + s;

			if (!path.startsWith("/")) {
				path = "/" + path;
			}
			
			if (path.equals(pathPrincipal)) {
				continue;
			}
			
			if (UFile.exists(path)) {
				ListClass list2 = fromPath(path);
				list.addIfNotContains(list2);
			}
			
		}
		
		list.sort();
		return list;
		
	}
	
	private static String getPathProjeto(String projeto) {

		MapSO map = new MapSO();
		map.loadIfExists("/opt/desen/gm/cs2019/gm-utils/paths.txt");
		String path = map.getString(projeto);
		if (UString.notEmpty(path) && UFile.exists(path)) {
			return path;
		}
		List<File> dirs = UFile.getAllDirectories("/opt/desen/gm/cs2019/");
		dirs = UList.filter(dirs, file -> file.toString().endsWith("/" + projeto));
		if (dirs.isEmpty()) {
			throw new RuntimeException("??? " + projeto);
		}
		
		dirs = UList.filter(dirs, file -> UFile.exists(file.toString() + "/pom.xml"));
		if (dirs.isEmpty()) {
			throw new RuntimeException("??? " + projeto);
		}
		if (dirs.size() > 1) {
			throw new RuntimeException("??? " + projeto);	
		}
		path = UString.beforeLast(dirs.get(0).toString(), "/") + "/" + projeto;
		map.add(projeto, path);
		map.save("/opt/desen/gm/cs2019/gm-utils/paths.txt");
		return path;
		
	}
	
	public static ListClass fromPath(String path) {
		
		if (path.contains(".jar!")) {
//			/home/gamarra/.m2/repository/gm/fc-core-back/0/fc-core-back-0.jar!/br/auto/model/
			String s = UString.beforeFirst(path, ".jar!");
//			/home/gamarra/.m2/repository/gm/fc-core-back/0/fc-core-back-0
			s = UString.beforeLast(s, "/");
//			/home/gamarra/.m2/repository/gm/fc-core-back/0
			s = UString.beforeLast(s, "/");
//			/home/gamarra/.m2/repository/gm/fc-core-back
			s = UString.afterLast(s, "/");
//			fc-core-back
			path = getPathProjeto(s) + "/src/main/java" + UString.afterFirst(path, ".jar!");
		}
		
		while (path.contains("/opt/desen/java/m2/reacts/")) {
			String s = UString.afterFirst(path, "/opt/desen/java/m2/reacts/");
			String nome = UString.beforeFirst(s, "/");
			s = UString.afterFirst(s, "/");
			String versao = UString.beforeFirst(s, "/");
			s = "/opt/desen/java/m2/reacts/" + nome + "/" + versao + "/" + nome + "-" + versao + ".jar!/";
			String x = "/opt/desen/gm/cs2019/reacts/"+nome+"/src/main/java/";
			path = path.replace(s, x);
//			path = path.replace("/opt/desen/java/m2/reacts/front-constructor/0/front-constructor-0.jar!/", "/opt/desen/gm/cs2019/reacts/front-constructor/src/main/java/");
		}
		
		
		if (path.contains("-0.jar!")) {
			
			String before = UString.beforeFirst(path, "-0.jar!");
//			/opt/desen/java/m2/react/react/0/react
			
			String after = UString.afterFirst(path, "-0.jar!");
//			/src/infra/consts/enums/
			
			String projeto = UString.afterLast(before, "/");
//			react
		
			String caminho = UConfig.get().getPathRaizProjetos().unique(s -> s.endsWith("/" + projeto + "/src/main/"));
//			opt/desen/gm/cs2019/react/src/main/
			
			if (Utils.isEmpty(caminho)) {
				throw UException.runtime("Nao foi possivel resolver " + path);
			}
			path = caminho + "java" + after;
			
		}
		
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		
		String pack;
		
		if (path.contains("/src/main/java/")) {
			pack = UString.afterFirst(path, "/src/");
			pack = UString.afterFirst(pack, "/java/");
		} else {
			pack = UString.afterFirst(path, "/target/");
			pack = UString.afterFirst(pack, "classes/");
		}
		
		pack = pack.replace("/", ".");
		
		if (!path.endsWith("/")) {
			pack += ".";
		}
		
		ListClass list = new ListClass();
		
		List<File> files = UFile.getFiles(path, "class", "java");
		
		for (File file : files) {
			String s = file.toString();
			s = UString.afterLast(s, "/");
			s = UString.beforeLast(s, ".");
			s = pack + s;
			try {
				list.add( getClassObrig(s) );
			} catch (ExceptionInInitializerError | Exception e) {
				ULog.debug("Erro ao tentar carregar " + s);
				UException.printTrace(e);
			}
		}
		return list;
		
	}

	public static boolean isList(Class<?> type) {
		String name = type.getName();
		return name.equals( List.class.getName() ) || name.equals( Set.class.getName() );
	}

	public static <T> T newInstanceAutoParameters(Class<T> classe) {
		
		try {
			
			if (isAbstract(classe)) {
				classe = ListAtributos.get(classe, false).getObrig("DEFAULT_IMPLEMENTATION").get(classe);
			}

			Constructor<?> constructor = classe.getConstructors()[0];
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			
			if (parameterTypes.length == 0) {
				return newInstance(classe);
			}
			
			Object[] args = UType.asAutoParameters(parameterTypes);
			
			@SuppressWarnings("unchecked")
			T o = (T) constructor.newInstance(args);
			
			return o;
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}

	public static String getTitulo(Class<?> classe) {
		Titulo annotation = classe.getAnnotation(Titulo.class);
		if (annotation != null) {
			return annotation.value();
		} else {
			return UString.toCamelCaseSepare(classe);
		}
	}

	
}
