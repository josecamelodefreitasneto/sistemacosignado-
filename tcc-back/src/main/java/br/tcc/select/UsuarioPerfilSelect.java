package br.tcc.select;

import br.tcc.model.UsuarioPerfil;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class UsuarioPerfilSelect<ORIGEM> extends SelectBase<ORIGEM, UsuarioPerfil, UsuarioPerfilSelect<ORIGEM>> {
	public UsuarioPerfilSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, UsuarioPerfil.class);
	}
	public SelectInteger<UsuarioPerfilSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public UsuarioSelect<UsuarioPerfilSelect<?>> usuario() {
		return new UsuarioSelect<>(this, getC(), getPrefixo() + ".usuario" );
	}
	public PerfilSelect<UsuarioPerfilSelect<?>> perfil() {
		return new PerfilSelect<>(this, getC(), getPrefixo() + ".perfil" );
	}
	public SelectBoolean<UsuarioPerfilSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<UsuarioPerfilSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<UsuarioPerfilSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public UsuarioPerfilSelect<?> asc() {
		return id().asc();
	}
}
