package br.tcc.select;

import br.tcc.model.Banco;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class BancoSelect<ORIGEM> extends SelectBase<ORIGEM, Banco, BancoSelect<ORIGEM>> {
	public BancoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Banco.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<BancoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<BancoSelect<?>> codigo() {
		return new SelectString<>(this, "codigo");
	}
	public SelectString<BancoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<BancoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<BancoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<BancoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public BancoSelect<?> asc() {
		return id().asc();
	}
}
