package gm.utils.javaCreate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.string.CorretorOrtografico;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JcMetodo {

	private JcAnotacoes anotacoes = new JcAnotacoes();
	private String afterComment;
	private String nome;
	private JcTipo tipoRetorno;
	private String acesso = "private";
	
	private boolean hasThrow;
	private boolean override;
	private boolean abstrato;
	private boolean estatico;
	private boolean synchronized_;
	private boolean final_;
	private boolean construtor;
	private boolean pularLinhaAntes = true;
	private boolean naoQuebrarLinhas;
	
	private JcTipos tipos = new JcTipos();
	
	private List<JcParametro> parametros = new ArrayList<>();
	private ListString body = new ListString();
	
//	30032764 skynet vitoria
//	ligacao fixo ilimitada
//	72316011
	
	public JcMetodo(final String nome) {
		this.nome = nome;
		this.body.setAutoIdentacao(true);
	}
	public JcMetodo add() {
		if (this.abstrato) {
			throw UException.runtime("M"+UConstantes.e_agudo+"todo abstrato");
		}
		this.body.add();
		return this;
	}
	public JcMetodo returnNull() {
		return this.return_("null");
	}
	public JcMetodo while_(final String s) {
		return this.add("while ("+s+") {");
	}
	public JcMetodo if_(final String s) {
		return this.add("if ("+s+") {");
	}
	public JcMetodo throwBusiness(String message) {
		return throw_(message, true);
	}
	public JcMetodo throw_(String s, boolean colocarAspas) {
		if (colocarAspas) {
			s = "\"" + CorretorOrtografico.exec(s) + "\"";
		}
		hasThrow = true;
		return this.add("throw new [exceptionClass]("+s+");");
	}
	public JcMetodo ifReturn(final String condicao, final String resultado) {
		return this.if_(condicao, "return " + resultado + ";");
	}
	public JcMetodo if_(final String condicao, final String... add) {
		this.if_(condicao);
		for (final String string : add) {
			this.add(string);
		}
		return this.close();
	}
	public JcMetodo ifElse(final String s, final String ifTrue, final String ifFalse) {
		return this.if_(s).add(ifTrue + ";").else_().add(ifFalse + ";").close();
	}
	public JcMetodo else_() {
		return this.add("} else {");
	}
	public JcMetodo elseif_(final String s) {
		return this.add("} else if ("+s+") {");
	}
	public JcMetodo close() {
		return this.add("}");
	}
	public JcMetodo add(final String s) {
		if (this.abstrato) {
			throw UException.runtime("M"+UConstantes.e_agudo+"todo abstrato");
		}
		this.body.add(s);
		return this;
	}
	public String getAssinatura() {

		String s = this.acesso + " ";
		
		if (this.abstrato) {
			s += "abstract ";
		} 
		
		if (!this.construtor) {
			if (this.estatico) {
				s += "static ";
			}
			if (this.synchronized_) {
				s += "synchronized ";
			}
			if (this.final_) {
				s += "final ";
			}
			if (this.tipoRetorno == null) {
				s += "void";
			} else {
				s += this.tipoRetorno;
			}
			s += " ";
		}
		
		s += this.nome + "(";
		if (!this.parametros.isEmpty()) {
			for (final JcParametro parametro : this.parametros) {
				s += parametro + ", ";
			}
			s = UString.ignoreRight(s, 2);
		}

		if (this.abstrato) {
			s += ");";
		} else {
			s += ") {";
		}
		
		return s;
		
	}
	public ListString get() {
		final ListString list = new ListString();
		
		if (this.pularLinhaAntes) {
			list.add();
		}
		
		if (this.override) {
			this.anotacoes.add(Override.class);
		}
		
		final String ans = this.anotacoes.toString();
		if (!ans.isEmpty()) {
			list.add(this.anotacoes.toString());
		}
		
		list.add(this.getAssinatura());
		
		if (this.abstrato) {
			return list;
		}
		
		list.margemInc();
		list.add(this.body);
		list.margemDec();
		list.add("}");
		if (this.afterComment != null) {
			list.add("\\" + this.afterComment);	
		}
		if (this.naoQuebrarLinhas) {
			list.trim();
			String s = list.toString(" ");
			s = s.replace("( ","(");
			s = s.replace(" )",")");
			s = s.replace("{ ","{");
			s = s.replace(" }","}");
			list.clear();
			list.add(s.trim());
		}
		return list;
	}
	
	@Override
	public String toString() {
		return get().toString("\n");
	}
	
	public JcMetodo addParametro(final String nome, final Class<?> tipo, final Class<?> generics) {
		return this.addParametro(new JcParametro(nome, tipo, generics).setFinal_(true));
	}
	public JcMetodo addParametro(final String nome, final String tipo, final String generics) {
		if (UString.isEmpty(generics)) {
			return this.addParametro(new JcParametro(nome, tipo).setFinal_(true));
		} else {
			return this.addParametro(new JcParametro(nome, tipo, "." + generics).setFinal_(true));
		}
	}
	public JcMetodo param(final String nome, final Class<?> tipo, final JcClasse generics) {
		return addParametro(nome, tipo, generics.getNameFull());
	}
	public JcMetodo addParametro(final String nome, final Class<?> tipo, final String generics) {
		return this.addParametro(new JcParametro(nome, tipo, generics).setFinal_(true));
	}
	public JcMetodo addParametro(final String nome, final JcTipo tipo) {
		return this.addParametro(new JcParametro(nome, tipo).setFinal_(true));
	}
	public JcMetodo paramId() {
		return this.param("id", int.class);
	}
	public JcMetodo param(final Class<?> tipo) {
		return this.param(UString.primeiraMinuscula(tipo.getSimpleName()), tipo);
	}
	public JcMetodo param(final String nome, final JcTipo tipo) {
		return this.addParametro(nome, tipo);
	}
	public JcMetodo param(final String nome, final JcClasse tipo) {
		return this.addParametro(nome, tipo.getNameFull());
	}
	public JcMetodo param(final String nome, final String tipo) {
		return this.addParametro(nome, tipo);
	}
	public JcMetodo param(final String nome, final Class<?> tipo) {
		return this.addParametro(nome, tipo);
	}
	public JcMetodo sparams(final String... nomes) {
		for (final String nome : nomes) {
			this.addParametro(nome, String.class);
		}
		return this;
	}
	public JcMetodo addParametro(final String nome, final Class<?> tipo) {
		return this.addParametro(new JcParametro(nome, tipo).setFinal_(true));
	}
	public JcMetodo addParametroArray(final String nome, final String tipo) {
		return this.addParametro(new JcParametro(nome, tipo).setFinal_(true).array());
	}
	public JcMetodo addParametroArray(final String nome, final Class<?> tipo) {
		return this.addParametro(new JcParametro(nome, tipo).setFinal_(true).array());
	}
	public JcMetodo addParametro(final String nome, final String tipo) {
		return this.addParametro(new JcParametro(nome, tipo).setFinal_(true));
	}
	public JcMetodo addParametro(final Class<?> classe) {
		return this.addParametro(UString.primeiraMinuscula(classe), classe);
	}
	public JcMetodo addParametro(final JcParametro param) {
		this.parametros.add(param);
//		addTipo(param.getTipo());
		return this;
	}
	public JcMetodo setTipoRetorno(final String tipo) {
		return this.setTipoRetorno(new JcTipo(tipo));
	}
	public JcMetodo setTipoRetorno(final JcTipo tipo) {
		this.tipoRetorno = tipo;
		return this;
	}
	public JcMetodo setTipoRetorno(final Class<?> tipo) {
		this.setTipoRetorno(tipo.getName());
		return this;
	}
	public JcMetodo clearGenericsRetorno() {
		this.tipoRetorno.clearGenerics();
		return this;
	}
	public JcMetodo addGenericsRetorno(final JcClasse tipo) {
		this.tipoRetorno.addGenerics(tipo.getNameFull());
		return this;
	}
	public JcMetodo addGenericsRetorno(final JcTipo tipo) {
		this.tipoRetorno.addGenerics(tipo);
		return this;
	}
	public JcMetodo setGenericsRetorno(final String tipo) {
		return addGenericsRetorno(tipo);
	}
	public JcMetodo addGenericsRetorno(final String tipo) {
		if (tipo == null || tipo.contentEquals(".null")) {
			return this;
		}
		this.addGenericsRetorno(new JcTipo(tipo));
		return this;
	}
	public JcMetodo setGenericsRetornoNivel2(final String tipo) {
		if (tipo == null || tipo.contentEquals(".null")) {
			return this;
		}
		this.tipoRetorno.getGenerics().get(0).addGenerics(tipo);
		return this;
	}
	public JcMetodo setGenericsRetorno(final Class<?> tipo) {
		this.setGenericsRetorno(tipo.getName());
		return this;
	}
	public JcMetodo setGenericsRetornoNivel2(final Class<?> tipo) {
		this.setGenericsRetornoNivel2(tipo.getName());
		return this;
	}
	public JcMetodo margemInc() {
		this.body.margemInc();
		return this;
	}
	public JcMetodo margemDec() {
		this.body.margemDec();
		return this;
	}
	public String removeLast() {
		return this.body.removeLast();
	}
	public JcMetodo final_() {
		this.setFinal_(true);
		return this;
	}
	public JcMetodo public_() {
		this.setAcesso("public");
		return this;
	}
	public JcMetodo protected_() {
		this.setAcesso("protected");
		return this;
	}
	public JcMetodo synchronized_() {
		this.setSynchronized_(true);
		return this;
	}
	public JcMetodo abstract_() {
		if (!this.body.isEmpty()) {
			throw UException.runtime("M"+UConstantes.e_agudo+"todo cont"+UConstantes.e_agudo+"m body");
		}
		if (!this.acesso.equals("public")) {
			this.protected_();
		}
		this.setAbstrato(true);
		return this;
	}
	public JcMetodo override() {
		if (!this.acesso.equals("public")) {
			this.protected_();
		}
		this.setOverride(true);
		return this;
	}
	public JcMetodo static_() {
		this.setEstatico(true);
		return this;
	}
	public JcMetodo typeSemValidacao(final String tipo) {
		this.tipoRetorno = new JcTipo(tipo);
		return this;
	}
	public JcMetodo type(final JcClasse tipo) {
		return this.type(tipo.getPacote() + "." + tipo.getNome());
	}
	public JcMetodo type(final JcTipo tipo) {
		this.setTipoRetorno(tipo);
		return this;
	}
	public JcMetodo type(final String tipo) {
		return this.type(new JcTipo(tipo));
	}
	public JcMetodo type(final Class<?> tipo) {
		this.setTipoRetorno(tipo);
		return this;
	}
	public JcMetodo type(final Class<?> tipo, final Class<?> generics) {
		this.setTipoRetorno(tipo);
		this.setGenericsRetorno(generics);
		return this;
	}
	public JcMetodo type(final Class<?> tipo, final String generics) {
		this.setTipoRetorno(tipo);
		this.setGenericsRetorno(generics);
		return this;
	}
	public JcMetodo type(final Class<?> tipo, final JcClasse generics) {
		this.setTipoRetorno(tipo);
		this.addGenericsRetorno(generics);
		return this;
	}
	public ListString getTipos() {
		final ListString list = new ListString();
		if (this.tipoRetorno != null) {
			this.tipoRetorno.addTipos(list);
		}
		
		this.parametros.forEach(o -> o.getTipo().addTipos(list));
		
		if (!this.anotacoes.getList().isEmpty()) {
			this.anotacoes.getList().forEach(o -> {
				if (o.getTipo().getName().contains(".")) {
					list.addIfNotContains(o.getTipo().getName());
				}
			});
		}
		
		for (JcTipo tipo : this.tipos.getList()) {
			list.add(tipo.getName());
		}
		
		return list;
	}
	public final JcMetodo addAnotacao(final String tipo) {
		this.anotacoes.add(tipo);
		return this;
	}
	public final JcMetodo addAnotacao(final JcTipo tipo) {
		this.anotacoes.add(tipo);
		return this;
	}
	public final JcMetodo addAnotacao(final Class<? extends Annotation> classe) {
		this.anotacoes.add(classe);
		return this;
	}
	public final JcMetodo addAnotacao(final Class<? extends Annotation> classe, final String parametro) {
		this.anotacoes.add(classe, parametro);
		return this;
	}
//	(Class<? extends Annotation> classe, String parametros)
	
	public JcMetodo return_(final String s) {
		return this.add("return " + s + ";");
	}
	public String getPenultimo() {
		return this.body.getPenultimo();
	}
	public String getLast() {
		return this.body.getLast();
	}
	public JcMetodo suppressWarningsUnchecked() {
		this.addAnotacao(SuppressWarnings.class, "\"unchecked\"");
		return this;
	}
	public JcMetodo ifStringEquals(String a, String b) {
		tipos.add(UString.class);
		if_("UString.equals("+a+", "+b+")");
		return this;
	}

}
