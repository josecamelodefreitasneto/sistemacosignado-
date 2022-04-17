package gm.utils.classes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.Construtor;
import gm.utils.reflection.ListAtributos;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.string.Java;
import gm.utils.string.Java2;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

public class ClassBox {
	
	@Getter
	private Class<?> classe;
	private String fileName;
	private Java java;
	private Java2 java2;
	private Metodos metodos;
	private Map<String, Class<?>> parametrosConstrutor;
	
	@Setter
	private ListClass imports;
	
	private List<ImportStatic> importsStatic;

	@Getter
	private String name;

	private ClassBox(Class<?> classe) {
		this.classe = classe;
		this.name = classe.getSimpleName();
	}
	
	public Map<String, Class<?>> getParametrosConstrutor() {
		if (parametrosConstrutor == null) {
			parametrosConstrutor = new HashMap<>();
			Constructor<?>[] constructors = classe.getConstructors();
			if (constructors.length > 1) {
				throw UException.runtime(name + " - constructors.length > 1");
			}
			
			Class<?>[] parameterTypes = constructors[0].getParameterTypes();
			if (parameterTypes.length == 0) {
				return parametrosConstrutor;
			}
			
			ListString list = getJava().getList();
			String s = list.trimPlus().toString(" ").replace(" (", "(");
			s = UString.afterFirst(s, " " + name + "(");
			s = UString.beforeFirst(s, ")");
			list = ListString.byDelimiter(s, ",").trimPlus();
			
			for (int i = 0; i < parameterTypes.length; i++) {
				Class<?> type = parameterTypes[i];
				s = list.remove(0);
				String nomeTipo = UString.beforeFirst(s, " ");
				if (nomeTipo.endsWith("...")) {
					nomeTipo = UString.beforeLast(nomeTipo, "...") + "[]";
				}
				if (nomeTipo.contains(".")) {
					nomeTipo = UString.afterLast(nomeTipo, ".");
				}
				if (!nomeTipo.equals(type.getSimpleName())) {
					for (int x = 0; x < parameterTypes.length; x++) {
//						System.out.println(parameterTypes[x].getSimpleName());
					}
					list.print();		
					throw UException.runtime("Algo deu errado!");
				}
				s = UString.afterFirst(s, " ");
				parametrosConstrutor.put(s, type);
			}
		}
		
		return parametrosConstrutor;
		
	}
	
	public Java getJava() {
		if (java == null) {
			java = new Java(UClass.javaFileName(classe));
		}
		return java;
	}
	public Java2 getJava2() {
		if (java2 == null) {
			java2 = new Java2(UClass.javaFileName(classe));
		}
		return java2;
	}
	
	private static Map<Class<?>, ClassBox> map = new HashMap<>();

	public static ClassBox get(Class<?> classe) {
		classe = UClass.getClass(classe);
		ClassBox o = map.get(classe);
		if (o == null) {
			o = new ClassBox(classe);
			map.put(classe, o);
		}
		return o;
	}
	
	public String getFileName() {
		if (fileName == null) {
			fileName = UClass.javaFileName(classe);
		}
		return fileName;
	}
	public Atributos getAtributos() {return getAtributos(false);}
	public Atributos getAtributos(Predicate<Atributo> predicate) {return getAtributos(predicate, false);}
	public Atributos getAtributos(boolean withId) {
		return ListAtributos.get(classe, withId);
	}
	public Atributos getAtributos(Predicate<Atributo> predicate, boolean withId) {
		return getAtributos(withId).filter(predicate);
	}
	public Metodos getMetodos(Predicate<Metodo> predicate) {
		return getMetodos().filter(predicate);
	}
	public Metodos getMetodos() {
		if (metodos == null) {
			metodos = ListMetodos.get(classe);
		}
		return metodos.clone();
	}
	
	public List<ImportStatic> getImportsStatic() {
		
		if (importsStatic != null) {
			return importsStatic; 
		}
		
		importsStatic = new ArrayList<>();

		ListString javaList = getJava().getList();
		
		ListString list = javaList.filter(s -> s.startsWith("import static"));
		for (String s : list) {
			s = UString.afterFirst(s, "import static ");
			s = UString.beforeLast(s, ";");
			ImportStatic o = new ImportStatic();
			o.setNome(UString.afterLast(s, "."));
			s = UString.beforeLast(s, ".");	
			o.setClasse(UClass.getClassObrig(s));
			importsStatic.add(o);
		}
		
		return importsStatic;
		
	}
	
	public ListClass getImports() {
		
		if (imports == null) {
			
			imports = new ListClass();

			ListString javaList = getJava().getList();
			
			ListString list = javaList.filter(s -> s.startsWith("import ") && !s.startsWith("import static "));
			for (String s : list) {
				s = UString.afterFirst(s, " ");
				s = UString.beforeLast(s, ";");
				imports.add(UClass.getClassObrig(s));
			}
			
			ListClass classes = UClass.classesDaMesmaPackage(getClasse());
			classes.remove(getClasse());
			
			if (!classes.isEmpty()) {
				String s = javaList.trimPlus().toString(" ");
				s = s.replace("(", "( ");
				s = s.replace(" (", "(");
				ListString.separaPalavras(s);
				for (Class<?> c : classes) {
					if (s.contains(c.getSimpleName())) {
						imports.add(c);
					}
				}
			}
			
		}
		
		return imports;
	}

	private List<Construtor> construtores;
	
	public List<Construtor> getConstrutores() {
		if (construtores == null) {
			construtores = new ArrayList<>();
			Constructor<?>[] constructors = classe.getConstructors();
			for (Constructor<?> constructor : constructors) {
				construtores.add(new Construtor(constructor));
			}
		}
		return construtores;
	}

	public boolean instanceOf(Class<?> classe) {
		return UClass.a_herda_b(getClasse(), classe);
	}

	public boolean isAbstract() {
		return UClass.isAbstract(classe);
	}

	public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
		return classe.isAnnotationPresent(annotationClass);
	}

	private ListString genericNames;
	
	public ListString getGenericNames() {
		
		if (genericNames == null) {
			
			genericNames = new ListString();
			
			if (classe.isEnum()) {
				return genericNames;
			}

			String s = getJava().getList().toString(" ");
			
			if (classe.isInterface()) {
				s = UString.afterFirst(s, " interface " + getName());
			} else {
				s = UString.afterFirst(s, " class " + getName());
			}
			
			s = UString.beforeFirst(s, "{");
			
			
			
			s = s.replace("<>", "");
			if (s.contains("<")) {
				s = UString.textoScopo(s, "<", ">");
				while (s.contains("<")) {
					String old = s;
					s = s.replace(UString.textoScopo(s, "<", ">"), "");
					s = s.replace("<>", "");
					if (old.equals(s)) {
						throw UException.runtime("loop infinito");
					}
				}
				ListString list = ListString.byDelimiter(s, ",").trimPlus();
				for (String string : list) {
					if (string.contains(" ")) {
						string = UString.beforeFirst(string, " ");
					}
					genericNames.add(string);
				}
			}
			
		}
		
		
		return genericNames;
	}

	private String tripa;
	
	public String getTripa() {
		if (tripa == null) {
			tripa = getJava().getList().trimPlus().toString(" ");
		}
		return tripa;
	}

}
