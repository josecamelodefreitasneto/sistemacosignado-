package br.tcc.select;

import br.tcc.model.ConsultaOperador;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ConsultaOperadorSelect<ORIGEM> extends SelectBase<ORIGEM, ConsultaOperador, ConsultaOperadorSelect<ORIGEM>> {
	public ConsultaOperadorSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ConsultaOperador.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ConsultaOperadorSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ConsultaOperadorSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<ConsultaOperadorSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ConsultaOperadorSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ConsultaOperadorSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ConsultaOperadorSelect<?> asc() {
		return id().asc();
	}
}
