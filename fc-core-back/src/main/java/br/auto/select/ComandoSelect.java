package br.auto.select;

import br.auto.model.Comando;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ComandoSelect<ORIGEM> extends SelectBase<ORIGEM, Comando, ComandoSelect<ORIGEM>> {
	public ComandoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Comando.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ComandoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public EntidadeSelect<ComandoSelect<?>> entidade() {
		return new EntidadeSelect<>(this, getC(), getPrefixo() + ".entidade" );
	}
	public SelectString<ComandoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<ComandoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ComandoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ComandoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ComandoSelect<?> asc() {
		return id().asc();
	}
}
