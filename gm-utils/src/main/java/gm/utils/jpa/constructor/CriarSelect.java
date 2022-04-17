package gm.utils.jpa.constructor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectDate;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectNumeric1;
import gm.utils.jpa.select.SelectNumeric2;
import gm.utils.jpa.select.SelectString;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.UAtributos;
import gm.utils.string.ListString;
public class CriarSelect extends CriarClasse {
	private CriarSelect(ConstructorBackConfig config) {
		super(config);
	}
	private CriarSelect(ConstructorBackConfig config, Class<?> classe) {
		super(config, classe);
	}
	public static void exec(ConstructorBackConfig config) {
		new CriarSelect(config);
	}
	public static void execute(ConstructorBackConfig config, Class<?> classe) {
		new CriarSelect(config, classe);
	}
	@Override
	public ListString corpo() {
		
		Class<?> fwDia = UClass.getClass("gm.fw.model.Dia");
		
//		ULog.debug( "Executando CriarSelect" );
		String n = classe.getSimpleName();
		addImport(Criterio.class);
		addImport(SelectBase.class);
		addImport(classe);
//		addImport(U.class);
		Atributos as = UAtributos.getPersistAndVirtuais(classe);
		as.remove("tc");
		as.remove("tk");
		ListString list = new ListString();
		list.add("public class " + n + "Select<ORIGEM> extends SelectBase<ORIGEM," + n + "," + n + "Select<ORIGEM>> {");
		list.add("\tpublic " + n + "Select(ORIGEM origem, Criterio<?> criterio, String prefixo) {");
		list.add("\t\tsuper(origem, criterio, prefixo, " + n + ".class);");
		list.add("\t}");
		if (as.contains("ordem") || as.contains("nome")) {
			list.add("\t@Override");
			list.add("\tprotected void beforeSelect(){");
			if (as.contains("ordemHierarquia")) {
				list.add("\t\tordemHierarquia().asc();");
			}
			if (as.contains("ordem")) {
				list.add("\t\tordem().asc();");
			}
			if (as.contains("nome")) {
				list.add("\t\tnome().asc();");
			}
			list.add("\t}");
		}
		
		if (getConfig().criarPojo()) {
			list.add("\tpublic " + n + "Pojo pojo(){");
			list.add("\t\treturn new " + n + "Pojo(list());");
			list.add("\t}");
		}
		
		if (getConfig().criarUpdate()) {
			list.add("\tpublic " + n + "Update update(){");
			list.add("\t\treturn new " + n + "Update(this);");
			list.add("\t}");
		}
		
		if (fwDia != null) {
			Class<?> fwTabela = UClass.getClass("gm.fw.model.Tabela");
			if (classe.equals(fwTabela)) {
				Class<?> fwEntidade = UClass.getClass("gm.fw.support.Entidade");
				addImport(fwEntidade);
				Class<?> fwTabelaBoo = UClass.getClass("gm.fw.boos.TabelaBoo");
				addImport(fwTabelaBoo);
				list.add("	public ORIGEM eq(Class<? extends Entidade<?>> classe){");
				list.add("		return eq(TabelaBoo.get(classe));");
				list.add("	}");
			}
		}
		as.add(0, as.getId());
		for (Atributo a : as) {
			final String s = a.nome();
			String nomeValue = a.getNomeValue();
			if (a.is(Integer.class)) {
				addImport(SelectInteger.class);
				list.add("\tpublic SelectInteger<" + n + "Select<?>> " + s + "(){");
				list.add("\t\treturn new SelectInteger<>(this, \"" + nomeValue + "\");");
				list.add("\t}");
			} else if (a.is(Boolean.class)) {
				addImport(SelectBoolean.class);
				list.add("\tpublic SelectBoolean<" + n + "Select<?>> " + s + "(){");
				list.add("\t\treturn new SelectBoolean<>(this, \"" + nomeValue + "\");");
				list.add("\t}");
			} else if (a.isString()) {
				addImport(SelectString.class);
				list.add("\tpublic SelectString<" + n + "Select<?>> " + s + "(){");
				list.add("\t\treturn new SelectString<>(this, \"" + nomeValue + "\");");
				list.add("\t}");
			} else if (a.isFk()) {
				String tp = a.getType().getSimpleName();
				list.add("\tpublic "+tp+"Select<" + n + "Select<?>> " + s + "(){");
				list.add("\t\treturn new "+tp+"Select<>(this, getC(), getPrefixo() + \"." + s + "\" );");
				list.add("\t}");
			} else if (a.isDate()) {
				addImport(SelectDate.class);
				list.add("\tpublic SelectDate<" + n + "Select<?>> " + s + "(){");
				list.add("\t\treturn new SelectDate<>(this, \"" + nomeValue + "\");");
				list.add("\t}");
			} else if (a.isNumeric2()) {
				addImport(SelectNumeric2.class);
				list.add("\tpublic SelectNumeric2<" + n + "Select<?>> " + s + "(){");
				list.add("\t\treturn new SelectNumeric2<>(this, \""+nomeValue+"\");");
				list.add("\t}");
			} else if (a.isNumeric1()) {
				addImport(SelectNumeric1.class);
				list.add("\tpublic SelectNumeric1<" + n + "Select<?>> " + s + "(){");
				list.add("\t\treturn new SelectNumeric1<>(this, \""+nomeValue+"\");");
				list.add("\t}");
			} else {
				addImport(a.getType());
				list.add("\tpublic " + n + "Select " + s + "(" + a.getType().getSimpleName() + " " + s + "){");
				list.add("\t\tgetC().eq(getPrefixo()+\"" + nomeValue + "\", " + s + ");");
				list.add("\t\treturn this;");
				list.add("\t}");
				list.add("\tpublic " + n + "Select " + s + "().ne(" + a.getType().getSimpleName() + " " + s + "){");
				list.add("\t\tgetC().ne(getPrefixo()+\"" + nomeValue + "\", " + s + ");");
				list.add("\t\treturn this;");
				list.add("\t}");
				list.add("\tpublic " + n + "Select " + s + "().isNull(){");
				list.add("\t\tgetC().isNull(getPrefixo()+\"" + nomeValue + "\");");
				list.add("\t\treturn this;");
				list.add("\t}");
				list.add("\tpublic " + n + "Select " + s + "().isNotNull(){");
				list.add("\t\tgetC().isNotNull(getPrefixo()+\"" + nomeValue + "\");");
				list.add("\t\treturn this;");
				list.add("\t}");
				list.add("\tpublic " + n + "Select " + s + "().asc(){");
				list.add("\t\tgetC().order(getPrefixo()+\"" + nomeValue + "\");");
				list.add("\t\treturn this;");
				list.add("\t}");
				list.add("\tpublic " + n + "Select " + s + "().desc(){");
				list.add("\t\tgetC().addOrderDesc(getPrefixo()+\"" + nomeValue + "\");");
				list.add("\t\treturn this;");
				list.add("\t}");
				list.add("\tpublic " + n + "Select " + s + "().in(Collection<" + a.getType().getSimpleName() + "> list){");
				list.add("\t\tgetC().in(getPrefixo()+\"" + nomeValue + "\", list);");
				list.add("\t\treturn this;");
				list.add("\t}");
				list.add("\tpublic " + n + "Select " + s + "().in(" + a.getType().getSimpleName() + "... list){");
				list.add("\t\treturn " + s + "().in(Arrays.asList(list));");
				list.add("\t}");
				addImport(Arrays.class);
				if (!s.equals("id")) {
					list.add("\tpublic " + n + "Select " + s + "().notIn(Collection<" + a.getType().getSimpleName()
							+ "> list){");
					list.add("\t\tgetC().not().in(getPrefixo()+\"" + nomeValue + "\", list);");
					list.add("\t\treturn this;");
					list.add("\t}");
				}
				list.add("\tpublic " + n + "Select " + s + "().notIn(" + a.getType().getSimpleName() + "... list){");
				list.add("\t\treturn " + s + "().notIn(Arrays.asList(list));");
				list.add("\t}");
//				FIXME pessoa logada
//				if (a.type(PessoaFisica.class)) {
//					list.add("\tpublic " + n + "Select " + s + "_logado(){");
//					list.add("\t\treturn " + s + "(Sessao.getPessoa());");
//					list.add("\t}");
//					addImport(Sessao.class);
//				} else if (a.type(Usuario.class)) {
//					list.add("\tpublic " + n + "Select " + s + "_logado(){");
//					list.add("\t\treturn " + s + "(Sessao.getPessoaEmpresa());");
//					list.add("\t}");
//					addImport(Sessao.class);
//				}
				list.add("\tpublic List<" + a.getType().getSimpleName() + "> " + s + "().distinct(){");
				list.add("\t\treturn getC().distinct(getPrefixo()+\"" + nomeValue + "\");");
				list.add("\t}");
			}
		}
		Atributos datas = as.getWhereType(Calendar.class);
		if (datas.contains("inicio") && datas.contains("fim")) {
			addImport(Calendar.class);
			list.add("\tpublic " + n + "Select<ORIGEM> entreInicioEFim(Calendar data){");
			list.add("\t\tinicio().menorOuIgual(data);");
			list.add("\t\tfim().maiorOuIgual(data);");
			list.add("\t\treturn this;");
			list.add("\t}");
		}
		if (fwDia != null) {
			Atributos dias = as.getWhereType(fwDia);
			if (dias.contains("inicio") && dias.contains("fim")) {
				addImport(fwDia);
				list.add("\tpublic " + n + "Select<ORIGEM> entreInicioEFim(Dia data){");
				list.add("\t\tinicio().menorOuIgual(data);");
				list.add("\t\tfim().maiorOuIgual(data);");
				list.add("\t\treturn this;");
				list.add("\t}");
			}
		}
		Atributo pai = as.get("pai");
		if (pai != null && pai.is(classe)) {
			Atributo hierarquia = as.get("hierarquia");
			if (hierarquia != null && hierarquia.is(String.class)) {
				addImport(List.class);
				list.add("\tpublic " + n + "Select<?> hierarquia_of(List<" + classe.getSimpleName() + "> list){");
				list.add("\t\twhile (!list.isEmpty()) {");
				list.add("\t\t	"+classe.getSimpleName()+" o = list.remove(0);");
				list.add("\t\t	hierarquia().like(o.getHierarquia()+\";\");");
				list.add("\t\t	if (!list.isEmpty()) {");
				list.add("\t\t		or();");
				list.add("\t\t	}");
				list.add("\t\t}");
				list.add("\t\treturn this;");
				list.add("\t}");
			}
		}
//		FIXME - rever fixeds
//		Class<Object> fixed = UClass.getClass(classe.getName() + "Fixed");
//
//		if (fixed != null) {
//			Method method = U.getMethod("list", fixed);
//			Object invoke = U.invoke(method, fixed);
//			@SuppressWarnings("unchecked")
//			List<O> lista = (List<O>) invoke;
//			for (O o : lista) {
//				list.add("\tpublic " + n + "Select " + o.getText() + "(){");
//				list.add("\t\treturn id(" + o.getId() + ");");
//				list.add("\t}");
//			}
//		}
		list.add("\tpublic "+n+"Select<?> asc(){");
		list.add("\t\treturn id().asc();");
		list.add("\t}");
		if (fwDia != null && classe.equals(fwDia)) {
//			addImport(FwSelect.class);
			addImport(PACOTE + ".FwSelect");
			addImport(Calendar.class);
			addImport(Data.class);
			list.add("\tpublic Dia min(){");
			list.add("\t\tInteger id = id().min();");
			list.add("\t\tif (id == null) return null;");
			list.add("\t\treturn FwSelect.dia(id);");
			list.add("\t}");
			list.add("\tpublic Dia max(){");
			list.add("\t\tInteger id = id().max();");
			list.add("\t\tif (id == null) return null;");
			list.add("\t\treturn FwSelect.dia(id);");
			list.add("\t}");
			list.add("\tpublic ORIGEM maior(Dia dia){");
			list.add("\t\tid().maior(dia.getId());");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");	
			list.add("\tpublic ORIGEM maiorOuIgual(Dia dia){");
			list.add("\t\tid().maiorOuIgual(dia.getId());");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");			
			list.add("\tpublic ORIGEM menorOuIgual(Dia dia){");
			list.add("\t\tid().menorOuIgual(dia.getId());");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");
			list.add("\tpublic ORIGEM maiorOuIgual(Calendar dia){");
			list.add("\t\tdata().maiorOuIgual(dia);");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");			
			list.add("\tpublic ORIGEM menorOuIgual(Calendar dia){");
			list.add("\t\tdata().menorOuIgual(dia);");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");			
			list.add("\tpublic ORIGEM maiorOuIgual(Data dia){");
			list.add("\t\tdata().maiorOuIgual(dia);");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");			
			list.add("\tpublic ORIGEM menorOuIgual(Data dia){");
			list.add("\t\tdata().menorOuIgual(dia);");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");			
			list.add("\tpublic ORIGEM menor(Dia dia){");
			list.add("\t\tid().menor(dia.getId());");
			list.add("\t\treturn getOrigem();");
			list.add("\t}");
		}
		return list;
	}
	@Override
	public String sufixo() {
		return "Select";
	}
}
