package br.tcc.select;

import br.tcc.model.Entidade;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class EntidadeSelect<ORIGEM> extends SelectBase<ORIGEM, Entidade, EntidadeSelect<ORIGEM>> {
	public EntidadeSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Entidade.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<EntidadeSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<EntidadeSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectString<EntidadeSelect<?>> nomeClasse() {
		return new SelectString<>(this, "nomeClasse");
	}
	public SelectBoolean<EntidadeSelect<?>> primitivo() {
		return new SelectBoolean<>(this, "primitivo");
	}
	public SelectBoolean<EntidadeSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<EntidadeSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<EntidadeSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public EntidadeSelect<?> asc() {
		return id().asc();
	}
}
