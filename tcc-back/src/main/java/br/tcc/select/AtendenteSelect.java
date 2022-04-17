package br.tcc.select;

import br.tcc.model.Atendente;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class AtendenteSelect<ORIGEM> extends SelectBase<ORIGEM, Atendente, AtendenteSelect<ORIGEM>> {
	public AtendenteSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Atendente.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<AtendenteSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<AtendenteSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectString<AtendenteSelect<?>> email() {
		return new SelectString<>(this, "email");
	}
	public UsuarioSelect<AtendenteSelect<?>> usuario() {
		return new UsuarioSelect<>(this, getC(), getPrefixo() + ".usuario" );
	}
	public SelectBoolean<AtendenteSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<AtendenteSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<AtendenteSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public AtendenteSelect<?> asc() {
		return id().asc();
	}
}
