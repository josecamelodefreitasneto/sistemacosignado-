package gm.utils.javaCreate;

import java.lang.annotation.Annotation;

import gm.utils.javaCreate.annotations.JcAnotacaoGet;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;

@Getter
public class JcParametro {
	
	private final JcAnotacoes anotacoes = new JcAnotacoes();
	private final String nome;
	private boolean array;
	private JcTipo tipo;
	protected boolean final_;
	
	public JcParametro(final String nome, final JcTipo tipo) {
		this.nome = UString.trimPlus(nome);
		this.tipo = tipo;
	}
	
	public final void setTipo(final JcTipo tipo) {
		this.tipo = tipo;
	}
	
	public JcParametro(final String nome, final Class<?> tipo, final Class<?> generics) {
		this(nome, tipo);
		this.setGenerics(generics);
	}
	public JcParametro(final String nome, final String tipo, final String generics) {
		this(nome, tipo);
		this.setGenerics(generics);
	}
	public JcParametro(final String nome, final Class<?> tipo, final String generics) {
		this(nome, tipo);
		this.setGenerics(generics);
	}
	public JcParametro(final String nome, final Class<?> tipo) {
		this(nome, tipo.getName());
	}
	public JcParametro(final String nome, final JcClasse tipo) {
		this(nome, tipo.getPacote() + "." + tipo.getNome());
	}
	public JcParametro(final String nome, final String tipo) {
		this.nome = nome;
		this.setTipo(tipo);
	}
	public JcParametro array() {
		array = true;
		return this;
	}
	public final void setTipo(final String tipo) {
		this.setTipo(new JcTipo(tipo));
	}
	public final void setGenerics(final JcClasse tipo) {
		this.setGenerics(tipo.getPacote() + "." + tipo.getNome());
	}
	public final void setGenerics(final JcTipo tipo) {
		this.tipo.addGenerics(tipo);
	}
	public final void setGenerics(final String tipo) {
		this.tipo.addGenerics(tipo);
	}
	public final void setGenericsNivel2(final String tipo) {
		this.tipo.getGenerics().get(0).addGenerics(tipo);
	}
	public final void setGenerics(final Class<?> tipo) {
		this.setGenerics(tipo.getName());
	}
	public final JcParametro addAnotacao(final String tipo) {
		anotacoes.add(tipo);
		return this;
	}
	public final JcParametro addAnotacao(final Class<? extends Annotation> classe) {
		anotacoes.add(classe);
		return this;
	}
	public JcParametro addAnotacao(final JcAnotacao value) {
		anotacoes.add(value);
		return this;
	}
	public JcParametro addAnotacao(final JcAnotacaoGet value) {
		return addAnotacao(value.get());
	}
	public JcParametro generics(final String tipo) {
		this.setGenerics(tipo);
		return this;
	}
	public JcParametro generics(final Class<?> tipo) {
		this.setGenerics(tipo);
		return this;
	}
	public JcParametro generics(final JcClasse tipo) {
		this.setGenerics(tipo);
		return this;
	}
	@Override
	public String toString() {
		
		String s;
		
		if (anotacoes.has()) {
			s = anotacoes.toString() + " ";
		} else {
			s = "";
		}
		
		if (final_) {
			s += "final ";	
		}
		
		s += tipo.toString();
		if (array) {
			s += "...";
		}
		return s + " " + nome;
	}
	
	public final ListString getTipos() {
		return tipo.getTipos(); 
	}
	
	public static void main(final String[] args) {
		System.out.println( new JcParametro("classe", Class.class).generics("? extends Entidade<?>") );
	}

	public JcParametro setFinal_(final boolean value) {
		final_ = value;
		return this;
	}
	
}
