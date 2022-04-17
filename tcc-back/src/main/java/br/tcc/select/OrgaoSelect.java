package br.tcc.select;

import br.tcc.model.Orgao;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class OrgaoSelect<ORIGEM> extends SelectBase<ORIGEM, Orgao, OrgaoSelect<ORIGEM>> {
	public OrgaoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Orgao.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<OrgaoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<OrgaoSelect<?>> codigo() {
		return new SelectString<>(this, "codigo");
	}
	public SelectString<OrgaoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<OrgaoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<OrgaoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<OrgaoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public OrgaoSelect<?> asc() {
		return id().asc();
	}
}
