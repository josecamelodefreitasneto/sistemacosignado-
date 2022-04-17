package gm.utils.jpa.constructor;

import java.util.List;

import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UType;
import gm.utils.exception.UException;
import gm.utils.jpa.select.SelectBase;
import gm.utils.lambda.FTT;
import gm.utils.number.Numeric1;
import gm.utils.number.Numeric2;
import gm.utils.number.UNumber;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.reflection.UAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class CriarPojo extends CriarClasse {

	private CriarPojo(ConstructorBackConfig config) {
		super(config);
	}
	
	private CriarPojo(ConstructorBackConfig config, Class<?> classe) {
		super(config, classe);
	}

	public static void exec(ConstructorBackConfig config) {
		new CriarPojo(config);
	}
	public static void execute(ConstructorBackConfig config, Class<?> classe) {
		new CriarPojo(config, classe);
	}	
	
	@Override
	public ListString corpo(){
		
//		ULog.debug("CriarPojo.exec");
		
		String n = classe.getSimpleName();
//		String m = UString.primeiraMinuscula(n);
		
		addImport( SelectBase.class );
		addImport( Pojo.class );
		addImport( List.class );
		addImport( FTT.class );
		
		ListClass entidades = getEntidades();
		
		Atributos as = UAtributos.getPersists(classe);
		as.sort();
		
		String thisPojo = nomeThis();
		
		ListString list = new ListString();
		list.add("public class "+thisPojo+" extends Pojo{");
		list.add("\tpublic "+thisPojo+"(List<"+n+"> itens) {super(itens);}"); 
		list.add("\t"+thisPojo+"(Pojo pai, String prefixo){super(pai, prefixo);}");
		list.add("\tpublic "+thisPojo+"("+n+" o) {super(o);}");
		list.add("\tpublic "+thisPojo+"(SelectBase<?,?,?> selectBase) {super(selectBase);}");
		addImport(classe);
		
		list.add("\tpublic "+thisPojo+" sequencial() {");
		list.add("\t\tadd(\"sequencial\");");
		list.add("\t\treturn this;");
		list.add("\t}");
		
		list.add("\tpublic "+thisPojo+" aux(String alias) {");
		list.add("\t\tadd(\"aux\",alias);");
		list.add("\t\treturn this;");
		list.add("\t}");
		
		Atributo id = as.getId();
		
		if (id == null) {
			throw UException.runtime("id == null: " + classe);
		}
		
		as.add(0, id);
		
		ListString all = new ListString();
		
		Metodos metodos = ListMetodos.get(classe);
		metodos.removeSeContemParametros();
		metodos.removeVoids();
		metodos.removeHerdados();
		metodos.removeSobrescritos();
		metodos.removeifRetorno(classe);
		
		as.remove("tk");
		as.remove("tc");
		as.sort();
		metodos.sort();
		
		for (Atributo a : as) {
			
			metodos.remove( a.nome() );
			metodos.remove( "get" + a.upperNome() );
			metodos.remove( a.nome() + "Inc" );
			metodos.remove( a.nome() + "Dec" );
			
			if (a.isList()) {
				continue;
			}
			
			String s = a.nome();
			String nomeCampo = s;
			
			if (a.isId()) {
				s = "id";
			} else if ( s.startsWith("ds") || s.startsWith("is") || s.startsWith("tx") ) {
				s = UString.primeiraMinuscula( s.substring(2) );
			}
			
			if (a.isFk()) {
				all.add(s + "().id(\""+a.nome()+"\")");
			} else {
				all.add(s + "()");
			}
			
			if (a.isPrimitivo()) {
				list.add("\tpublic "+thisPojo+" "+s+"() {"); 
				list.add("\t\tadd(\""+nomeCampo+"\");"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");
				list.add("\tpublic "+thisPojo+" "+s+"(String alias) {"); 
				list.add("\t\tadd(\""+nomeCampo+"\", alias);"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");
				list.add("\tpublic "+thisPojo+" "+s+"(FTT<String, Object> c) {"); 
				list.add("\t\tadd(\""+nomeCampo+"\", c);"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");
				list.add("\tpublic "+thisPojo+" "+s+"(String alias, FTT<String, Object> c) {"); 
				list.add("\t\tadd(\""+nomeCampo+"\", alias, c);"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");
			} else {
				list.add("\tpublic "+a.getType().getSimpleName()+"Pojo "+s+"() {");
				list.add("\t\treturn new "+a.getType().getSimpleName()+"Pojo(this, \""+nomeCampo+"\");"); 
				list.add("\t}");
			}
			
		}
		metodos.remove("toString");
		for (Metodo metodo : metodos) {
			final Class<?> retorno = metodo.retorno();
			
			if (retorno == null || UClass.isList(retorno) || metodo.returnVoid()) {
				continue;
			}
			
			String s = metodo.nome();
			
			if (s.startsWith("get")) {
				s = UString.primeiraMinuscula(s.substring(3));
			}
			
			if ( entidades.contains(retorno) ) {
				list.add("\tpublic "+retorno.getSimpleName()+"Pojo "+s+"() {");
				list.add("\t\treturn new "+retorno.getSimpleName()+"Pojo(this, \""+s+"\");"); 
				list.add("\t}");
			} else if (UType.isPrimitiva(retorno) || UType.isPrimitivaBox(retorno)) {
				list.add("\tpublic "+thisPojo+" "+s+"() {"); 
				list.add("\t\tadd(\""+s+"\");"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");
				list.add("\tpublic "+thisPojo+" "+s+"(String alias) {"); 
				list.add("\t\tadd(\""+s+"\", alias);"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");
				list.add("\tpublic "+thisPojo+" "+s+"(FTT<String, Object> c) {"); 
				list.add("\t\tadd(\""+s+"\", c);"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");
				list.add("\tpublic "+thisPojo+" "+s+"(String alias, FTT<String, Object> c) {"); 
				list.add("\t\tadd(\""+s+"\", alias, c);"); 
				list.add("\t\treturn this;"); 
				list.add("\t}");				
			}
		}
		
		Atributos fks = as.getFks();
		
		if (!fks.isEmpty()) {
			
			addImport( UException.class );
			addImport( Atributo.class );

			list.add("\t@Override");
			list.add("\tpublic Pojo subPojo(Atributo a) {");
			for (Atributo a : as) {
				if (a.isFk()) {
					String nome = a.nome();
					list.add("\t\tif (a.eq(\""+nome+"\")) return "+nome+"();");
//					list.add("\t\tif (a.equals( "+n+"Atributos."+nome+" )) return "+nome+"();");
				}
			}
			list.add("\t\tthrow UException.runtime(\""+thisPojo+".subPojo : Atributo n"+UConstantes.a_til+"o encontrado na classe: \" + a);");
			list.add("\t}");
		}
		
		list.add("\t@Override");
		list.add("\tpublic "+thisPojo+" all() {");
		for (String s : all) {
			list.add("\t\t" + s + ";"); 
		}
		list.add("\t\treturn this;"); 
		list.add("\t}");

		list.add("	@Override");
		list.add("	protected String getGerado(Object obj, String nome) {");
		list.add("		" + n + " o = ("+n+") obj;");
		
		for (Atributo a : as.filter(a -> !a.isFk())) {
			Class<?> type = a.isNumeric1() ? Numeric1.class : a.isNumeric2() ? Numeric2.class : a.getType();
			addGerado(list, a.nome(), a.stringGet(), type);
		}
		
		for (Metodo metodo : metodos) {
			final Class<?> retorno = metodo.retorno();
			
			if (retorno == null || UClass.isList(retorno) || metodo.returnVoid()) {
				continue;
			}
			
			if (!UType.isPrimitiva(retorno)) {
				continue;
			}
			
			String s = metodo.nome();
			
			if (s.startsWith("get")) {
				s = UString.primeiraMinuscula(s.substring(3));
			}
			
			addGerado(list, s, metodo.nome() + "()", retorno);
			
		}
		
		list.add("		return Pojo.NAO_ENCONTRADO;");
		list.add("	}");
		
//		protected abstract Object getGerado(String nome);
		
		return list;
		
	}

	private void addGerado(ListString list, String nome, String stringGet, Class<?> type) {

		list.add("		if (\""+nome+"\".equals(nome)) {");
		if (type.equals(String.class)) {
			list.add("			return o." + stringGet + ";");
		} else {
			if (!type.equals(int.class)) {
				list.add("			if (o." + stringGet + " == null) return null;");
			}
			if (nome.equals("id")) {
				list.add("			return o.getId().toString();");
			} else {
				
				if (type.equals(Integer.class)) {
					addImport(UNumber.class);
					list.add("			return UNumber.separarMilhares(o." + stringGet + ");");
				} else if (type.equals(Numeric1.class)) {
					addImport(Numeric1.class);
					list.add("			return new Numeric1(o." + stringGet + ").toString();");
				} else if (type.equals(Numeric2.class)) {
					addImport(Numeric2.class);
					list.add("			return new Numeric2(o." + stringGet + ").toString();");
				} else if (UType.isPrimitiva(type)) {
					addImport(UString.class);
					list.add("			return UString.toString(o." + stringGet + ");");
				} else {
					list.add("			return o."+stringGet+".getId().toString();");	
				}
			}
		}
		
		list.add("		}");		
		
	}

	@Override
	public String sufixo() {
		return "Pojo";
	}
	
}
