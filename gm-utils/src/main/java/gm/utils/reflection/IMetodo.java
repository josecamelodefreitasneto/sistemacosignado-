package gm.utils.reflection;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import gm.utils.classes.ClassBox;
import gm.utils.classes.ListClass;
import gm.utils.comum.UList;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public abstract class IMetodo {
	
	private String assinaturaSemOsNomesDosParametros;
	public final String getAssinaturaSemOsNomesDosParametros() {
		if (assinaturaSemOsNomesDosParametros == null) {
			String p = "";
			List<Parametro> params = getParametros();
			if (!params.isEmpty()) {
				for (Parametro parametro : params) {
					p += ", " + parametro.getType().getSimpleName();
				}
				p = p.substring(2);
			}
			assinaturaSemOsNomesDosParametros = getAssinaturaSemParametros() + "("+p+")";
		}
		return assinaturaSemOsNomesDosParametros;
	}
	
	private String assinaturaSemParametros;
	
	public final String getAssinaturaSemParametros() {
		if (assinaturaSemParametros == null) {
			assinaturaSemParametros = getAssinaturaSemParametrosImpl();
		}
		return assinaturaSemParametros;
	}
	
	public abstract String getAssinaturaSemParametrosImpl();
	protected abstract Class<?> getClasseImpl();
	protected abstract Class<?> getDeclaringClasseImpl();
	
	private ClassBox classe;
	public final ClassBox getClasse() {
		if (classe == null) {
			classe = ClassBox.get(getClasseImpl());
		}
		return classe;
	}
	
	private ClassBox declaringClasse;
	public final ClassBox getDeclaringClasse() {
		if (declaringClasse == null) {
			declaringClasse = ClassBox.get(getDeclaringClasseImpl());
		}
		return declaringClasse;
	}
	
	private List<Parametro> parametros;
	
	public final List<Parametro> getParametros() {
		if (parametros == null) {
			Parameter[] parameters = getParameters();
			parametros = new ArrayList<>();
			for (Parameter parameter : parameters) {
				parametros.add(new Parametro(parameter));
			}
		}
		return UList.copy(parametros);
	}
	
	protected abstract Parameter[] getParameters();

	private String assinaturaComOsNomesDosParametros;
	
	public ListString getCodigoJava() {

		String java = javaSemGenerics();
		
		String assin = getAssinaturaComOsNomesDosParametros();
		
		assin = removeGenerics(assin);
		
		for (String string : generics) {
			assin = assin.replace(" " + string + " ", " Object ");
			assin = assin.replace("(" + string + " ", "(Object ");
		}
		
		while (assin.contains("<")) {
			String params = UString.textoScopo(assin, "<", ">");
			assin = assin.replace("<"+params+">", "");
		}
		
		String s = UString.afterFirst(java, assin);
		
		if (s == null) {
			String modificador = UString.beforeFirst(assin, " ");
			assin = UString.afterFirst(assin, " ");
			boolean isFinal = assin.startsWith("final "); 
			assin = UString.afterFirst(assin, " ");
			if (isFinal) {
				assin = UString.afterFirst(assin, " ");	
			}
			assin = modificador + (isFinal ? " final " : " ") + "Object " + assin;
			s = UString.afterFirst(java, assin);
		}
		
//		gene
		if (UString.textoScopo(s, "{", "}") == null) {
			System.out.println(java);
			System.out.println(assin);
			System.out.println(s);
		}
		s = UString.textoScopo(s, "{", "}");
		s = s.replace("{", "{\n");
		s = s.replace("}", "\n}");
		
		String x = UString.textoScopo(s, "/*", "*/");
		
		while (x != null) {
			s = s.replace(x, "");
			x = UString.textoScopo(s, "/*", "*/");
		}
		
		ListString list = ListString.byDelimiter(s, "\n");
		list.identarJava();
		return list;
		
	}
	
	public final String getAssinaturaComOsNomesDosParametros() {
		if (assinaturaComOsNomesDosParametros == null) {
			assinaturaComOsNomesDosParametros = getAssinaturaComOsNomesDosParametrosImpl();
//			String s = UString.textoEntreFirst(assinaturaComOsNomesDosParametros, "(", ")");
//			ListString list = ListString.byDelimiter(s, ",");
//			list.trimPlus();
//			List<Parametro> parametros = getParametros();
//			for (int i = 0; i < list.size(); i++) {
//				parametros.get(i).setNome(UString.afterFirst(list.get(i), " "));
//			}
		}
		return assinaturaComOsNomesDosParametros;
	}
	
	private ListString generics = new ListString();
	
	private String javaSemGenerics() {

		String s = getAssinaturaSemParametros();
		String java = getClasse().getJava().getString();
		
		String xx = UString.afterFirst(java, "class " + classe.getName());
		xx = UString.beforeFirst(xx, "{");
		if (xx.contains("<")) {
			
			boolean repetir = true;

			xx = UString.textoEntreFirst(xx, "<", ">");
			ListString list = ListString.byDelimiter(xx, ",").trimPlus();
			for (String string : list) {
				if (string.contains(" extends ")) {
					string = UString.beforeFirst(string, " ");
				}
				generics.addIfNotContains(string);
			}
			
			while (repetir) {
				repetir = false;
				for (String string : generics) {
					
					if (string.equals("Object")) {
						continue;
					}
					
					java = java.replace("<"+string+">", "");
					
					if (java.contains("<"+string+", ")) {
						java = java.replace("<"+string+", ", "<");
						repetir = true;
					}
					
					java = java.replace(" "+string+" ", " Object ");
					java = java.replace(" "+string+"(", " Object(");
					java = java.replace("("+string+" ", "(Object ");
					java = java.replace("	"+string+" ", "	Object ");
					s = s.replace("<"+string+">", "Object");
					s = s.replace(" "+string+" ", " Object ");
					s = s.replace(" "+string+" ", " Object ");
					s = s.replace("("+string+" ", "(Object ");
					s = s.replace(","+string+" ", ", Object ");
				}
			}
			
		}
		
//		public <BODY> Promessa post(String url, BODY body) {
		while (java.contains("public <")) {
			xx = UString.afterFirst(java, "public <");
			xx = UString.textoScopo("<" + xx, "<", ">");
			generics.add(xx);
			java = java.replace("public <" + xx + ">", "public");
			java = java.replace(" " + xx + " ", " Object ");
			java = java.replace("(" + xx + " ", "(Object ");
			java = java.replace("	" + xx + " ", "	Object ");
		}
		
		java = removeGenerics(java);
		
		return java;
		
	}
	
	private String removeGenerics(String s) {

		ListClass imports = getClasse().getImports();
		
		for (Class<?> c : imports) {
			String n = c.getSimpleName();
			while (s.contains(n+"<")) {
				String ss = UString.afterFirst(s, n+"<");
				ss = UString.textoScopo("<" + ss, "<", ">");
				s = s.replace(n+"<" + ss + ">", n);
			}
		}
		
		return s;
	}

	private String getAssinaturaComOsNomesDosParametrosImpl() {
		
//		String java = javaSemGenerics();
		
		String s = getAssinaturaSemParametros();
		for (String string : generics) {
			s = s.replace(" " + string + " ", " Object ");
		}
		
		String ss = "";
		
		List<Parametro> parametros = getParametros();
		if (!parametros.isEmpty()) {
			for (Parametro p : parametros) {
				String tipo = UString.beforeLast(p.getParameter().toString(), " ");
				if (tipo.contains("<")) {
					tipo = UString.beforeFirst(tipo, "<");
				}
				if (tipo.contains(".")) {
					tipo = UString.afterLast(tipo, ".");
				}
//				ss += ", " + p.getType().getSimpleName() + " " + p.getNome();
				ss += ", " + tipo + " " + p.getNome();
			}
			ss = ss.substring(2);
		}
		s = s + "("+ss+")";
		return s;
		
		/*
		
		int qtdParametros = parametros.size();
		
		while (true) {
			String ss = UString.afterFirst(java, s);
			if (ss == null) {
				getAssinaturaComOsNomesDosParametrosImpl();
				throw UException.runtime("Algo deu errado!");
			}
			java = UString.afterFirst(java, ss);
			ss = UString.afterFirst(ss, "(");
			ss = UString.beforeFirst(ss, ")");
			
 			ListString l = ListString.byDelimiter(ss, ",");
			l.trimPlus();
			
			ListString list = new ListString();
			
			while (!l.isEmpty()) {
				String remove = l.remove(0);
				if (!remove.contains(" ") && remove.contains("<")) {
					remove = UString.beforeFirst(remove, "<");
					String xx = l.remove(0);
					if (xx.contains(">")) {
						remove += " " + UString.afterFirst(xx, ">").trim();
					} else {
						throw UException.runtime("N"+UConstantes.a_til+"o tratado");
					}
				}
				list.add(remove);
			}
			
			if (list.size() == qtdParametros) {
				boolean iguais = true;
				for (int i = 0; i < qtdParametros; i++) {
					String tipo = UString.beforeFirst(list.get(i), " ");
					if (tipo.contains("<")) {
						tipo = UString.beforeFirst(tipo, "<");
					}
					if (tipo.contains("...")) {
						tipo = tipo.replace("...", "[]");
					}
					String sn = parametros.get(i).getType().getSimpleName();
					if ( !sn.equals(tipo) && !generics.contains(sn) ) {
						iguais = false;
						break;
					}
				}
				if (iguais) {
					if (ss == null) {
						return s + "()";
					} else {
						return s + "("+ss+")";
					}
				}
			}				
		}
		/**/
	}
	
	protected abstract int getModifiers();
	
	public final String getModificadorDeAcesso() {
		if (isPublic()) {
			return "public";
		} else if (isPrivate()) {
			return "private";
		} else if (isProtected()) {
			return "protected";
		} else {
			return "default";
		}
	}
	
	public final boolean isPublic(){
		return java.lang.reflect.Modifier.isPublic(getModifiers());
	}
	public final boolean isPrivate(){
		return java.lang.reflect.Modifier.isPrivate(getModifiers());
	}
	public final boolean isProtected(){
		return java.lang.reflect.Modifier.isProtected(getModifiers());
	}
	public boolean isDefault(){
		return !isPublic() && !isPrivate() && !isProtected();
	}
	public int getParameterCount() {
		return getParametros().size();
	}
	public boolean isParameters(Class<?>... parametros) {
		if ( getParameterCount() != parametros.length ) {
			return false;
		}
		List<Parametro> params = getParametros();
		for (int i = 0; i < parametros.length; i++) {
			if ( !params.get(i).is(parametros[i]) ) {
				return false;
			}
		}
		return true;
	}
	public String getParametrosJs() {
		if (getParameterCount() == 0) {
			return "";
		}
		getAssinaturaComOsNomesDosParametros();
		String s = "";
		List<Parametro> parametros = getParametros();
		for (Parametro parametro : parametros) {
			s += ", " + parametro.getNome();					
		}
		return s.substring(2);
	}
	
}

