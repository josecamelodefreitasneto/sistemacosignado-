package br.tcc.select;

import br.tcc.model.Usuario;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class UsuarioSelect<ORIGEM> extends SelectBase<ORIGEM, Usuario, UsuarioSelect<ORIGEM>> {
	public UsuarioSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Usuario.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<UsuarioSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<UsuarioSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectString<UsuarioSelect<?>> login() {
		return new SelectString<>(this, "login");
	}
	public SelectString<UsuarioSelect<?>> senha() {
		return new SelectString<>(this, "senha");
	}
	public SelectBoolean<UsuarioSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<UsuarioSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<UsuarioSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public UsuarioSelect<?> asc() {
		return id().asc();
	}
}
