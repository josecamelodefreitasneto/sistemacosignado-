package br.tcc.select;

import br.tcc.model.Login;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectDate;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class LoginSelect<ORIGEM> extends SelectBase<ORIGEM, Login, LoginSelect<ORIGEM>> {
	public LoginSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Login.class);
	}
	public SelectInteger<LoginSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public UsuarioSelect<LoginSelect<?>> usuario() {
		return new UsuarioSelect<>(this, getC(), getPrefixo() + ".usuario" );
	}
	public SelectDate<LoginSelect<?>> data() {
		return new SelectDate<>(this, "data");
	}
	public SelectString<LoginSelect<?>> token() {
		return new SelectString<>(this, "token");
	}
	public SelectBoolean<LoginSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<LoginSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<LoginSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public LoginSelect<?> asc() {
		return id().asc();
	}
}
