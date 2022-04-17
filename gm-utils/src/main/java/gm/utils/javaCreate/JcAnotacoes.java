package gm.utils.javaCreate;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import gm.utils.comum.UList;
import gm.utils.string.ListString;

public class JcAnotacoes {
	
	private final List<JcAnotacao> list = new ArrayList<>();
	
	public JcAnotacao add(final String tipo) {
		return this.add(new JcTipo(tipo));
	}
	public JcAnotacao add(final Class<? extends Annotation> classe) {
		return this.add(new JcAnotacao(classe));
	}
	public JcAnotacao add(final Class<? extends Annotation> classe, final String parametro) {
		return this.add(new JcAnotacao(classe, parametro));
	}
	public JcAnotacao add(final JcTipo tipo) {
		return this.add(new JcAnotacao(tipo));
	}
	public JcAnotacao add(final JcAnotacao anotacao) {
		if (!this.has(anotacao.getTipo().getName())) {
			this.list.add(anotacao);
		}
		return anotacao;
	}
	public JcAnotacoes add(final JcAnotacoes anotacoes) {
		for (final JcAnotacao o : anotacoes.list) this.add(o);
		return this;
	}
	@Override
	public String toString() {
		if (this.list.isEmpty()) {
			return "";
		}
		String s = "";
		for (final JcAnotacao o : this.list) {
			s += " " + o; 
		}
		return s.substring(1);
	}
	public ListString toListString() {
		final ListString lst = new ListString();
		for (final JcAnotacao o : this.list) {
			lst.add(o.toString()); 
		}
		return lst;
	}
	public boolean has() {
		return !this.list.isEmpty();
	}
	public List<JcAnotacao> getList() {
		return this.list;
	}
	public boolean has(final Class<?> tipo) {
		return this.has(tipo.getName());
	}
	public boolean has(final String tipo) {
		return UList.exists(this.list, o -> o.getTipo().getName().contentEquals(tipo));
	}
}
