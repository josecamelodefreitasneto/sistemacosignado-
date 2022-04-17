package br.auto.select;

import br.auto.model.Campo;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class CampoSelect<ORIGEM> extends SelectBase<ORIGEM, Campo, CampoSelect<ORIGEM>> {
	public CampoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Campo.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<CampoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public EntidadeSelect<CampoSelect<?>> entidade() {
		return new EntidadeSelect<>(this, getC(), getPrefixo() + ".entidade" );
	}
	public EntidadeSelect<CampoSelect<?>> tipo() {
		return new EntidadeSelect<>(this, getC(), getPrefixo() + ".tipo" );
	}
	public SelectString<CampoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectString<CampoSelect<?>> nomeNoBanco() {
		return new SelectString<>(this, "nomeNoBanco");
	}
	public SelectBoolean<CampoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<CampoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<CampoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public CampoSelect<?> asc() {
		return id().asc();
	}
}
