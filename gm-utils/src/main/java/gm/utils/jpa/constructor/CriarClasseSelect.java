package gm.utils.jpa.constructor;

import gm.utils.classes.ListClass;
import gm.utils.jpa.UJpa;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.string.ListString;
import gm.utils.string.UString;

@Deprecated//usar jc
public class CriarClasseSelect extends CriarClasse {
	public static void exec(ConstructorBackConfig config) {
		//ULog.debug( "Executando CriarClasseSelect" );
		new CriarClasseSelect(config);
	}
	private CriarClasseSelect(ConstructorBackConfig config) {
		super(config, false);
		save();
	}
	private static void criarMetodo(ListString list, String s, String sm){
		list.add("\tpublic static " + s + "Select<"+s+"Select<?>> " + sm + "(){");
		list.add("\t\treturn " + sm + "(null);");
		list.add("\t}");
		list.add("\tstatic " + s + "Select<"+s+"Select<?>> " + sm + "(String prefixo){");
		list.add("\t\treturn new " + s + "Select<>(null, new Criterio<>("+s+".class), prefixo);");
		list.add("\t}");
		list.add("\tpublic static " + s + " " + sm + "(int id){");
		list.add("\t\treturn " + sm + "().byId(id);");
		list.add("\t}");
		list.add("\tpublic static " + s + " " + sm + "Obrig(int id){");
		list.add("\t\t"+s+" o = "+sm+"(id);");
		list.add("\t\tUJpa.checaObrig(o, \""+sm+"\", id);");
		list.add("\t\treturn o;");
		list.add("\t}");
	}
	
	public ListString corpo(){
		
		ListClass classes = getEntidades();
		
		ListString list = new ListString();
		
		addImport(Criterio.class);
		addImport(UJpa.class);
		
		for (Class<?> o : classes) {
			addImport(o);
		}
		
		String prefixo = getPrefixo();
		
		if (prefixo.equals("Fw")) {
			list.add("public class FwSelect {");
		} else {
			if (getConfig().usaFramework()) {
				list.add("public class Select extends FwSelect {");
			} else {
				list.add("public class Select {");
			}
		}
		
		for (Class<?> o : classes) {
			String s = o.getSimpleName();
			String sm = UString.primeiraMinuscula( o.getSimpleName() );
			criarMetodo(list, s, sm);
		}
		
		return list;
		
	}

	@Override
	public String sufixo() {
		return getPrefixo() + "Select";
	}

	@Override
	String nomeThis() {
		return sufixo();
	}	
	
}
