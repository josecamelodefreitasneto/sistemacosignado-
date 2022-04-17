package br.tcc.select;

import br.tcc.model.Telefone;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class TelefoneSelect<ORIGEM> extends SelectBase<ORIGEM, Telefone, TelefoneSelect<ORIGEM>> {
	public TelefoneSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Telefone.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<TelefoneSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectInteger<TelefoneSelect<?>> ddd() {
		return new SelectInteger<>(this, "ddd");
	}
	public SelectString<TelefoneSelect<?>> numero() {
		return new SelectString<>(this, "numero");
	}
	public SelectString<TelefoneSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<TelefoneSelect<?>> whatsapp() {
		return new SelectBoolean<>(this, "whatsapp");
	}
	public SelectBoolean<TelefoneSelect<?>> recado() {
		return new SelectBoolean<>(this, "recado");
	}
	public SelectBoolean<TelefoneSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<TelefoneSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<TelefoneSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public TelefoneSelect<?> asc() {
		return id().asc();
	}
}
