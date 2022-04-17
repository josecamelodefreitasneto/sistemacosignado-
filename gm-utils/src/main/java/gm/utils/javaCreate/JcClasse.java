package gm.utils.javaCreate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.comum.UList;
import gm.utils.config.UConfig;
import gm.utils.files.UFile;
import gm.utils.lambda.FT;
import gm.utils.lambda.FVoidT;
import gm.utils.reflection.Atributo;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter
public class JcClasse {

	private final JcAnotacoes anotations = new JcAnotacoes();
	private final ListString interfaces = new ListString();
	private final ListString statesSets = new ListString();
	private final List<JcAtributo> atributos = new ArrayList<>();
	private final JcTipos imports = new JcTipos();
	private final List<JcMetodo> metodos = new ArrayList<>();
	private final List<JcClasse> inners = new ArrayList<>();
	private final String nome;
	private final String pacote;
	private String superClass;
	private String superClassGeneric;
	private String singletonCommonsClass;
	private final String path;
	private Class<?> exceptionClass = RuntimeException.class;
	private String generics;
	private final ListString adds = new ListString();
	private boolean sortMetodos = false;
	private boolean abstract_ = false;
	@Setter private boolean formal = false;
	@Setter private boolean anyThrow = false;
	
	@Setter private boolean pularLinhaAntesDeCadaMetodo = true;
	
	public JcClasse(final Class<?> classe) {
		pacote = classe.getPackage().getName();
		path = UString.beforeFirst(UClass.javaFileName(classe), "/src/main/java/") + "/src/main/java/";
		nome = classe.getSimpleName();
	}
	
//	config.getPathRaizProjetoAtual()
	
	public JcClasse(final String nome) {
		this("gm.auto", nome);
	}
	public JcClasse(final String pacote, final String nome) {
		this(UConfig.get().getPathRaizProjetoAtual() + "java/", pacote, nome);
	}
	public JcClasse(final String path, final String pacote, final String nome) {
		this.path = path.endsWith("/") ? path : path + "/";
		this.pacote = pacote;
		this.nome = nome;
	}
	public JcClasse setSuperClass(final Class<?> superClass) {
		return this.setSuperClass(superClass.getName());
	}
	public JcClasse setSuperClassGeneric(final Class<?> classe) {
		return this.setSuperClassGeneric(classe.getName());
	}
	public JcClasse setSuperClassGeneric(String classe) {
		if (classe != null) {
			if (classe.contains(".")) {
				this.addImport(classe);
				classe = UString.afterLast(classe, ".");
			}
			if (superClassGeneric == null) {
				superClassGeneric = classe; 
			} else {
				superClassGeneric += ", " + classe;
			}
		}
		return this;
	}
	public JcClasse setSuperClass(final JcClasse superClass) {
		return this.setSuperClass(superClass.getPacote() + "." + superClass.getNome());
	}
	public JcClasse setSuperClass(final String superClass) {
		if (superClass != null) {
			this.superClass = superClass;
			this.addImport(superClass);
		}
		return this;
	}
	public JcClasse addImport(final JcTipo tipo) {
		final ListString tipos = tipo.getTipos();
		for (final String s : tipos) {
			this.addImport(s);
		}
		return this;
		
	}
	public JcClasse addImport(String s) {
		if (s.startsWith("java.lang.") || s.startsWith("*.")) {
			return this;
		} else {
			if (s.contains("<")) {
				s = UString.beforeFirst(s, "<");
			}
			if (s.startsWith("$GENERICS$.")) {
				return this;
			}
			imports.add(s);
			return this;
		}
	}
	public JcClasse addImport(final Class<?> tipo) {
		return this.addImport(tipo.getName());
	}
	public JcClasse addAtributo(final JcAtributo atributo) {
		atributos.add(atributo);
		return this;
	}
	public JcClasse addMetodo(final JcMetodo metodo) {
		metodos.add(metodo);
		return this;
	}
	public String getFileName() {
		return path + pacote.replace(".", "/") + "/" + nome + ".java";
	}
	public boolean exists() {
		return UFile.exists(getFileName());
	}
	public boolean hasAnnotation(final Class<?> tipo) {
		return anotations.has(tipo);
	}
	public JcClasse addAnnotation(final Class<? extends Annotation> tipo) {
		anotations.add(tipo);
		return this;
	}
	public JcClasse addAnnotation(final Class<?> tipo, final String value) {
		return this.addAnnotation(tipo.getName(), value);
	}
	public JcClasse addAnnotation(final String tipo) {
		anotations.add(tipo);
		return this;
	}
	public JcClasse addAnnotation(String tipo, Object value) {
		JcAnotacao jcAnotacao = new JcAnotacao(tipo, value);
		return this.addAnnotation(jcAnotacao);
	}
	public JcClasse addAnnotation(final JcAnotacao anotacao) {
		anotations.add(anotacao);
		return this;
	}
	public JcClasse addAnnotations(final JcAnotacoes anotacoes) {
		anotations.add(anotacoes);
		return this;
	}
	public JcClasse addImplements(final Class<?> tipo) {
		return this.addImplements(tipo.getName());
	}
	public JcClasse addImplements(final String tipo) {
		this.addImport(tipo);
		interfaces.add(tipo);
		return this;
	}
	public void save() {
		get().save(getFileName());
	}
	public void save(FVoidT<ListString> tratar) {
		ListString list = get();
		tratar.call(list);
		list.save(getFileName());
	}
	public ListString get() {
		
		for (final JcAtributo o : atributos) {
			if (o.isGetter()) {
				
				JcMetodo m = metodo("get" + UString.primeiraMaiuscula(o.getNome()))
				.type(o.getTipo())
				.public_();

				if (o.isStatico()) {
					m.static_().return_(nome +"."+o.getNome());
				} else {
					m.return_("this."+o.getNome());
				}
				
			}
			if (o.isSetter()) {
				metodo("set" + UString.primeiraMaiuscula(o.getNome()))
				.addParametro("value", o.getTipo())
				.add("this."+o.getNome()+" = value;")
				.public_()
				;
			}
			if (o.isGetterLombok()) {
				this.addImport(Getter.class);
			}
			if (o.isSetterLombok()) {
				this.addImport(Setter.class);
			}
			if (o.isBuilder()) {
				metodo(o.getNome()).type(this).public_().param(o.getNome(), o.getTipo()).add("this."+o.getNome()+" = "+o.getNome()+";").return_("this;");
				if (o.getBuilderNome() != null) {
					metodo(o.getBuilderNome()).type(this).public_().param(o.getNome(), o.getTipo()).return_("this."+o.getNome()+"("+o.getNome()+");");	
				}
			}
		}
		
		final ListString list = new ListString();
		
		list.add("package "+pacote+";");
		list.add();
		
		anotations.getList().forEach(o -> imports.add(o.getTipos()));
		
		metodos.forEach(o -> {
			o.getTipos().forEach(tipo -> {
				this.addImport(tipo);
			});
		});
		
		metodos.forEach(o -> {
			if (o.isHasThrow()) {
				addImport(exceptionClass);
				anyThrow = true;
			}
		});		
		
		atributos.forEach(o -> {
			o.getTipos().forEach(tipo -> {
				this.addImport(tipo);
			});
			o.getAnotacoes().getList().forEach(i -> imports.add(i.getTipos()));
		});
		
		if (singletonCommonsClass != null) {
			this.addImport(FT.class);
		}
		
		ListString innerStrings = new ListString();
		
		for (JcClasse inner : inners) {
			ListString lst = inner.get();
			
			while (!lst.get(0).startsWith("public class ")) {
				lst.remove(0);
			}
			
			String s = lst.remove(0);
			s = s.replace("public class", "private static class");
			innerStrings.add();
			innerStrings.add(s);
			innerStrings.add(lst);
			
			for (JcTipo tipo : inner.imports.getList()) {
				addImport(tipo);
			}
		}
		
		tratarImports();
		imports.sort();

		for (final JcTipo tipo : imports.getList()) {
			if (tipo.isGenerics()) {
				continue;
			}
			final String s = tipo.getName();
			if (!UString.beforeLast(s, ".").equals(getPacote()) && !s.startsWith(".")) {
				list.add("import "+s+";");
			}
		}
		
		list.add();
		
		if (anotations.has()) {
			list.add(anotations.toString());
		}
		
		final boolean abstrato = abstract_ || UList.exists(metodos, m -> m.isAbstrato());
		
		String s;
		if (abstrato) {
			s = "public abstract class " + nome;
		} else {
			s = "public class " + nome;
		}
		
		if (generics != null) {
			s += "<"+generics+">";
		}
		
		if (superClass != null) {
			s += " extends "+UString.afterLast("." + superClass, ".");
			if (superClassGeneric != null) {
				s += "<"+superClassGeneric+">";
			}
		}
		
		if (!interfaces.isEmpty()) {
			String x = "";
			for (final String string : interfaces) {
				x += ", " + UString.afterLast("." + string, ".");
			}
			s += " implements" + x.substring(1);
		}
		
		list.add(s + " {");
		
		list.add(innerStrings);
		
		list.getMargem().inc();
		if (!atributos.isEmpty()) {
			list.add();
//			nao deve ser ordenado
//			atributos.sort((a,b) -> a.toString().compareTo(b.toString()));
			if (formal) {
				UList.filter(atributos, o -> o.isStatico()).forEach(o -> {
					list.add(o.toListString());
					list.add();
				});
				UList.filter(atributos, o -> !o.isStatico()).forEach(o -> {
					list.add(o.toListString());
					list.add();
				});
			} else {
				UList.filter(atributos, o -> o.isStatico()).forEach(o -> list.add(o.toString()));
				UList.filter(atributos, o -> !o.isStatico()).forEach(o -> list.add(o.toString()));
			}
		}
		
		if (!pularLinhaAntesDeCadaMetodo) {
			metodos.forEach(o -> o.setPularLinhaAntes(false));
		}
		
		if (sortMetodos) {
			metodos.sort((a, b) -> a.getAssinatura().compareTo(b.getAssinatura()));
		}
		
		metodos.forEach(o -> list.add(o.get()));
		
		if (!statesSets.isEmpty()) {
			list.add();
			list.add(statesSets);
		}
		
		if (!adds.isEmpty()) {
			list.add();
			list.add(adds);
		}

		if (singletonCommonsClass != null) {
			list.add();
			list.add("private static "+nome+" instance;");
			if (abstrato) {
				list.add("public static FT<"+nome+"> newInstance;");
			} else {
				list.add("public static FT<"+nome+"> newInstance = () -> new "+nome+"();");
			}
			list.add("public static "+nome+" getInstance() {");
			list.add("	if ("+UString.afterLast(singletonCommonsClass, ".")+".isEmpty(instance)) {");
			list.add("		instance = newInstance.call();");
			list.add("	}");
			list.add("	return instance;");
			list.add("}");
		}
		
		list.getMargem().dec();
		list.add();
		
		list.add("}");
		
		list.removeDoubleWhites();
		list.getMargem().set(0);
		list.rtrim();
		list.replace(ListString.array("\n", "}"), "}");
		list.juntarFimComComecos("{", "}", "");
		list.juntarFimComComecos("}", "else", " ");
		list.juntarFimComComecos("}", "catch", " ");
		list.rtrim();
		if (anyThrow) {
			list.replaceTexto("[exceptionClass]", exceptionClass.getSimpleName());
		}
		list.replaceTexto(" + \"\")", ")");
		list.replaceTexto("[TAB]", "\t");
		
		return list;
		
	}

	protected void tratarImports() {}

	public JcMetodo construtor() {
		final JcMetodo o = metodo(nome);
		o.setConstrutor(true);
		return o;
	}
	public JcMetodo metodo(final String nome) {
		final JcMetodo o = new JcMetodo(nome);
		addMetodo(o);
		return o;
	}
	public JcAtributo constant(final String nome, final Class<?> tipo, final Class<?> generic, final String inicializacao) {
		return this.constant(nome, tipo, inicializacao).generics(generic);
	}
	public JcAtributo constantString(final String nome, final String value) {
		return this.constant(nome, String.class, "\""+value+"\"");
	}
	private static String toInicializacao(final ListString list) {
		list.add(0, "");
		list.addLeft("\t\t");
		list.add("\t");
		return list.toString("\n");
	}
	public JcAtributo constant(final String nome, final Class<?> tipo, final ListString inicializacao) {
		return this.constant(nome, tipo, JcClasse.toInicializacao(inicializacao));
	}
	public JcAtributo constant(final String nome, final Class<?> tipo, final String inicializacao) {
		return this.constant(nome, new JcTipo(tipo), inicializacao);
	}
	public JcAtributo constant(final String nome, final JcTipo tipo, final ListString inicializacao) {
		return this.constant(nome, tipo, JcClasse.toInicializacao(inicializacao));
	}
	public JcAtributo constant(final String nome, final JcTipo tipo, final String inicializacao) {
		final JcAtributo o = this.atributo(nome, tipo).static_().final_();
		o.setInicializacao(inicializacao);
		return o;
	}
	public JcAtributo atributo(final Atributo a) {
		return this.atributo(a.nome(), a.getType());
	}
	public JcAtributo atributo(final String nome, final String tipo) {
		final JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcAtributo atributo(final String nome, final JcClasse tipo) {
		final JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcAtributo atributo(final String nome, final JcTipo tipo) {
		final JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcAtributo atributo(final String nome, final Class<?> tipo) {
		final JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcClasse add(final String s) {
		adds.add(s);
		return this;
	}
	public JcClasse singletonCommonsClass(final String classe) {
		this.addImport(classe);
		singletonCommonsClass = classe;
		return this;
	}
	public JcClasse singletonCommonsClass(final Class<?> classe) {
		return this.singletonCommonsClass(classe.getName());
	}
	public void delete() {
		UFile.delete(getFileName());
	}
	public JcMetodo main() {
		return metodo("main").public_().static_().addParametroArray("args", String.class);		
	}
	public void generics(final String s) {
		if (generics == null) {
			generics = s;
		} else {
			generics += ", " + s;
		}
	}
	public JcClasse sortMetodos() {
		sortMetodos = true;
		return this;
	}
	public JcClasse lombokGetter() {
		return this.addAnnotation(Getter.class);
		
	}
	public JcClasse lombokSetter() {
		return this.addAnnotation(Setter.class);
	}
	public JcClasse setAbstract() {
		abstract_ = true;
		return this;
	}
	public void setExceptionClass(Class<?> classe) {
//		addImport(classe);
		exceptionClass = classe;
	}
	public JcAtributo getAtributo(String s) {
		return UList.filterUnique(getAtributos(), o -> UString.equals(o.getNome(), s));
	}
	public String getNameFull() {
		return getPacote() + "." + getNome();
	}

	public void addInner(JcClasse inner) {
		inners.add(inner);		
	}
}
