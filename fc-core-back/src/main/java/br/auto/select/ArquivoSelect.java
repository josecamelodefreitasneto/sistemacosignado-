package br.auto.select;

import br.auto.model.Arquivo;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ArquivoSelect<ORIGEM> extends SelectBase<ORIGEM, Arquivo, ArquivoSelect<ORIGEM>> {
	public ArquivoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Arquivo.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ArquivoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ArquivoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectInteger<ArquivoSelect<?>> tamanho() {
		return new SelectInteger<>(this, "tamanho");
	}
	public ArquivoPathSelect<ArquivoSelect<?>> path() {
		return new ArquivoPathSelect<>(this, getC(), getPrefixo() + ".path" );
	}
	public ArquivoExtensaoSelect<ArquivoSelect<?>> extensao() {
		return new ArquivoExtensaoSelect<>(this, getC(), getPrefixo() + ".extensao" );
	}
	public SelectString<ArquivoSelect<?>> checksum() {
		return new SelectString<>(this, "checksum");
	}
	public SelectBoolean<ArquivoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ArquivoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ArquivoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ArquivoSelect<?> asc() {
		return id().asc();
	}
}
