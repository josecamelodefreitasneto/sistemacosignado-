package br.auto.select;

import br.auto.model.ArquivoPath;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ArquivoPathSelect<ORIGEM> extends SelectBase<ORIGEM, ArquivoPath, ArquivoPathSelect<ORIGEM>> {
	public ArquivoPathSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ArquivoPath.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ArquivoPathSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ArquivoPathSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public ArquivoExtensaoSelect<ArquivoPathSelect<?>> extensao() {
		return new ArquivoExtensaoSelect<>(this, getC(), getPrefixo() + ".extensao" );
	}
	public SelectInteger<ArquivoPathSelect<?>> itens() {
		return new SelectInteger<>(this, "itens");
	}
	public SelectBoolean<ArquivoPathSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ArquivoPathSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ArquivoPathSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ArquivoPathSelect<?> asc() {
		return id().asc();
	}
}
