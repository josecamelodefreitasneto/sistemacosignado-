package br.auto.select;

import br.auto.model.ArquivoExtensao;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ArquivoExtensaoSelect<ORIGEM> extends SelectBase<ORIGEM, ArquivoExtensao, ArquivoExtensaoSelect<ORIGEM>> {
	public ArquivoExtensaoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ArquivoExtensao.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ArquivoExtensaoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ArquivoExtensaoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<ArquivoExtensaoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ArquivoExtensaoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ArquivoExtensaoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ArquivoExtensaoSelect<?> asc() {
		return id().asc();
	}
}
