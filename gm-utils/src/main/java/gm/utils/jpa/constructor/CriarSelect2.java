package gm.utils.jpa.constructor;
import java.util.Calendar;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.javaCreate.JcParametro;
import gm.utils.javaCreate.JcTipo;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectDate;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectLong;
import gm.utils.jpa.select.SelectNumeric1;
import gm.utils.jpa.select.SelectNumeric15;
import gm.utils.jpa.select.SelectNumeric2;
import gm.utils.jpa.select.SelectNumeric3;
import gm.utils.jpa.select.SelectNumeric4;
import gm.utils.jpa.select.SelectNumeric5;
import gm.utils.jpa.select.SelectString;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.UAtributos;
import lombok.Setter;

@Setter
public class CriarSelect2 {

	private Class<?> classe;
	private boolean pojo;
	private boolean update;
	private String pathJavaMain;
	private String pacoteOndeAsClassesSeraoCriadas;
	private String sn;
	private JcClasse jc;
	private Class<?> classeDia;
	
	public void exec() {
		this.sn = classe.getSimpleName();
		this.jc = new JcClasse(pathJavaMain, pacoteOndeAsClassesSeraoCriadas, sn + "Select");
		jc.setPularLinhaAntesDeCadaMetodo(false);
		jc.generics("ORIGEM");
		jc.setSuperClass(SelectBase.class);
		jc.setSuperClassGeneric("ORIGEM");
		jc.setSuperClassGeneric(classe);
		jc.setSuperClassGeneric(pacoteOndeAsClassesSeraoCriadas + "."+sn + "Select<ORIGEM>");
		
		jc.addImport(Criterio.class);
		jc.addImport(SelectBase.class);
		jc.addImport(classe);
		
		String n = sn;
		
		Atributos as = UAtributos.getPersistAndVirtuais(classe);
		
		jc.construtor()
		.public_()
		.addParametro("origem", "$GENERICS$.ORIGEM")
		.addParametro(new JcParametro("criterio", Criterio.class).generics("?"))
		.addParametro("prefixo", String.class)
		.add("super(origem, criterio, prefixo, " + n + ".class);");

		if (as.contains("ordem") || as.contains("nome")) {
			JcMetodo m = jc.metodo("beforeSelect").override();
			if (as.contains("ordemHierarquia")) {
				m.add("ordemHierarquia().asc();");
			}
			if (as.contains("ordem")) {
				m.add("ordem().asc();");
			}
			if (as.contains("nome")) {
				m.add("nome().asc();");
			}
		}
		
		if (pojo) {
			jc.metodo("pojo").public_().type(pacoteOndeAsClassesSeraoCriadas + "."+n+"Pojo").add("return new " + n + "Pojo(list());");
		}
		
		if (update) {
			jc.metodo("update").public_().type(pacoteOndeAsClassesSeraoCriadas + "."+n+"Update").add("return new " + n + "Update(this);");
		}
		
		if (classeDia != null) {
			Class<?> fwTabela = UClass.getClass("gm.fw.model.Tabela");
			if (classe.equals(fwTabela)) {
				Class<?> fwEntidade = UClass.getClass("gm.fw.support.Entidade");
				jc.addImport(fwEntidade);
				Class<?> fwTabelaBoo = UClass.getClass("gm.fw.boos.TabelaBoo");
				jc.addImport(fwTabelaBoo);
				
				jc.metodo("eq").public_().type("ORIGEM")
				.addParametro(new JcParametro("classe", Class.class).generics("? extends Entidade<?>"))
				.add("return eq(TabelaBoo.get(classe));");
			}
		}
		as.add(0, as.getId());
		for (Atributo a : as) {
			final String s = a.nome();
			String nomeValue = a.getNomeValue();
			
			JcMetodo m = jc.metodo(s).public_();
			
			if (a.is(Integer.class)) {
				m.type(SelectInteger.class).add("return new SelectInteger<>(this, \"" + nomeValue + "\");");
			} else if (a.is(Long.class)) {
				m.type(SelectLong.class).add("return new SelectLong<>(this, \"" + nomeValue + "\");");
			} else if (a.is(Boolean.class)) {
				m.type(SelectBoolean.class).add("return new SelectBoolean<>(this, \"" + nomeValue + "\");");
			} else if (a.isString()) {
				m.type(SelectString.class).add("return new SelectString<>(this, \"" + nomeValue + "\");");
			} else if (a.isFk()) {
				final String tp = a.getType().getSimpleName();
				m.type(pacoteOndeAsClassesSeraoCriadas + "."+tp+"Select");
				m.add("return new "+tp+"Select<>(this, getC(), getPrefixo() + \"." + s + "\" );");
			} else if (a.isDate()) {
				m.type(SelectDate.class);
				m.add("return new SelectDate<>(this, \"" + nomeValue + "\");");
			} else if (a.isNumeric2()) {
				m.type(SelectNumeric2.class);
				m.add("return new SelectNumeric2<>(this, \""+nomeValue+"\");");
			} else if (a.isNumeric1()) {
				m.type(SelectNumeric1.class);
				m.add("\t\treturn new SelectNumeric1<>(this, \""+nomeValue+"\");");
			} else if (a.digitsFraction() == 3) {
				m.type(SelectNumeric3.class);
				m.add("return new SelectNumeric3<>(this, \""+nomeValue+"\");");
			} else if (a.digitsFraction() == 4) {
				m.type(SelectNumeric4.class);
				m.add("return new SelectNumeric4<>(this, \""+nomeValue+"\");");
			} else if (a.digitsFraction() == 5) {
				m.type(SelectNumeric5.class);
				m.add("return new SelectNumeric5<>(this, \""+nomeValue+"\");");
			} else if (a.digitsFraction() == 15) {
				m.type(SelectNumeric15.class);
				m.add("return new SelectNumeric15<>(this, \""+nomeValue+"\");");
			} else {
				throw new RuntimeException("??? " + a);
			}
			
			JcTipo genericsRetorno = new JcTipo(pacoteOndeAsClassesSeraoCriadas + "." + n + "Select");
			genericsRetorno.addGenerics("?");
			m.addGenericsRetorno(genericsRetorno);
			
		}
		Atributos datas = as.getWhereType(Calendar.class);
		if (datas.contains("inicio") && datas.contains("fim")) {
			JcMetodo m = jc.metodo("entreInicioEFim").public_();
			m.type(pacoteOndeAsClassesSeraoCriadas + "."+n+"Select");
			m.setGenericsRetorno("ORIGEM");
			m.addParametro("data", Calendar.class);
			m.add("inicio().menorOuIgual(data);");
			m.add("fim().maiorOuIgual(data);");
			m.add("return this;");
		}
		if (classeDia != null) {
			Atributos dias = as.getWhereType(classeDia);
			if (dias.contains("inicio") && dias.contains("fim")) {
				JcMetodo m = jc.metodo("entreInicioEFim").public_();
				m.setGenericsRetorno("ORIGEM");
				m.type(n+"Select");
				m.addParametro("data", Data.class);
				m.add("inicio().menorOuIgual(data);");
				m.add("fim().maiorOuIgual(data);");
				m.add("return this;");
			}
		}
		Atributo pai = as.get("pai");
		if (pai != null && pai.is(classe)) {
			Atributo hierarquia = as.get("hierarquia");
			if (hierarquia != null && hierarquia.is(String.class)) {
				jc.addImport(List.class);
				JcMetodo m = jc.metodo("hierarquia_of").public_();
				m.type("gm.auto."+n+"Select<?>");
				m.addParametro("list", List.class, classe);
				m.add("while (!list.isEmpty()) {");
				m.add(classe.getSimpleName()+" o = list.remove(0);");
				m.add("hierarquia().like(o.getHierarquia()+\";\");");
				m.add("if (!list.isEmpty()) or();");
				m.add("}");
				m.add("return this;");
			}
		}
		
		JcTipo tipo = new JcTipo(pacoteOndeAsClassesSeraoCriadas + "."+n+"Select");
		tipo.addGenerics("?");

		jc.metodo("asc").public_().type(tipo).add("return id().asc();");
		
		if (classeDia != null && classe.equals(classeDia)) {
			
			jc.addImport(pacoteOndeAsClassesSeraoCriadas + ".FwSelect");
			jc.addImport(Calendar.class);
			jc.addImport(Data.class);
			
			jc.metodo("min").public_().type(classeDia)
			.add("Integer id = id().min();")
			.add("if (id == null) return null;")
			.add("return byId(id);");

			jc.metodo("max").public_().type(classeDia)
			.add("Integer id = id().max();")
			.add("if (id == null) return null;")
			.add("return byId(id);");

			jc.metodo("maior").public_().type("ORIGEM").addParametro("dia", classeDia)
			.add("id().maior(dia.getId());")
			.add("return getOrigem();");
			
			jc.metodo("maiorOuIgual").public_().type("ORIGEM").addParametro("dia", classeDia)
			.add("id().maiorOuIgual(dia.getId());")
			.add("return getOrigem();");
			
			jc.metodo("menorOuIgual").public_().type("ORIGEM").addParametro("dia", classeDia)
			.add("id().menorOuIgual(dia.getId());")
			.add("return getOrigem();");
			
			jc.metodo("maiorOuIgual").public_().type("ORIGEM").addParametro("dia", Calendar.class)
			.add("data().maiorOuIgual(dia);")
			.add("return getOrigem();");
			
			jc.metodo("menorOuIgual").public_().type("ORIGEM").addParametro("dia", Calendar.class)
			.add("data().menorOuIgual(dia);")
			.add("return getOrigem();");
			
			jc.metodo("maiorOuIgual").public_().type("ORIGEM").addParametro("dia", Data.class)
			.add("data().maiorOuIgual(dia);")
			.add("return getOrigem();");
			
			jc.metodo("menorOuIgual").public_().type("ORIGEM").addParametro("dia", Data.class)
			.add("data().menorOuIgual(dia);")
			.add("return getOrigem();");
			
			jc.metodo("menor").public_().type("ORIGEM").addParametro("dia", classeDia)
			.add("id().menor(dia.getId());")
			.add("return getOrigem();");
			
		}		
		
		jc.save();
		
	}
	
}
