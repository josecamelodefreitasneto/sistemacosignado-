package br.auto.select;

import br.auto.model.PerfilComando;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class PerfilComandoSelect<ORIGEM> extends SelectBase<ORIGEM, PerfilComando, PerfilComandoSelect<ORIGEM>> {
	public PerfilComandoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, PerfilComando.class);
	}
	public SelectInteger<PerfilComandoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public PerfilSelect<PerfilComandoSelect<?>> perfil() {
		return new PerfilSelect<>(this, getC(), getPrefixo() + ".perfil" );
	}
	public ComandoSelect<PerfilComandoSelect<?>> comando() {
		return new ComandoSelect<>(this, getC(), getPrefixo() + ".comando" );
	}
	public SelectBoolean<PerfilComandoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<PerfilComandoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<PerfilComandoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public PerfilComandoSelect<?> asc() {
		return id().asc();
	}
}
