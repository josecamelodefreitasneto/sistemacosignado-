package br.tcc.select;

import br.tcc.model.TipoAuditoriaEntidade;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class TipoAuditoriaEntidadeSelect<ORIGEM> extends SelectBase<ORIGEM, TipoAuditoriaEntidade, TipoAuditoriaEntidadeSelect<ORIGEM>> {
	public TipoAuditoriaEntidadeSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, TipoAuditoriaEntidade.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<TipoAuditoriaEntidadeSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<TipoAuditoriaEntidadeSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<TipoAuditoriaEntidadeSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<TipoAuditoriaEntidadeSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<TipoAuditoriaEntidadeSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public TipoAuditoriaEntidadeSelect<?> asc() {
		return id().asc();
	}
}
