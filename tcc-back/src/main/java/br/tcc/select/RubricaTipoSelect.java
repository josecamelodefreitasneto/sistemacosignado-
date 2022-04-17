package br.tcc.select;

import br.tcc.model.RubricaTipo;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class RubricaTipoSelect<ORIGEM> extends SelectBase<ORIGEM, RubricaTipo, RubricaTipoSelect<ORIGEM>> {
	public RubricaTipoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, RubricaTipo.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<RubricaTipoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<RubricaTipoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<RubricaTipoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<RubricaTipoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<RubricaTipoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public RubricaTipoSelect<?> asc() {
		return id().asc();
	}
}
