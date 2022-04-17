package br.tcc.select;

import br.tcc.model.Perfil;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class PerfilSelect<ORIGEM> extends SelectBase<ORIGEM, Perfil, PerfilSelect<ORIGEM>> {
	public PerfilSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Perfil.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<PerfilSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<PerfilSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<PerfilSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<PerfilSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<PerfilSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public PerfilSelect<?> asc() {
		return id().asc();
	}
}
