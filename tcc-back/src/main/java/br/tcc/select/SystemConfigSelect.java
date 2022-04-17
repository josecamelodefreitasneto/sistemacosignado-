package br.tcc.select;

import br.tcc.model.SystemConfig;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class SystemConfigSelect<ORIGEM> extends SelectBase<ORIGEM, SystemConfig, SystemConfigSelect<ORIGEM>> {
	public SystemConfigSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, SystemConfig.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<SystemConfigSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<SystemConfigSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectString<SystemConfigSelect<?>> valor() {
		return new SelectString<>(this, "valor");
	}
	public SelectBoolean<SystemConfigSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<SystemConfigSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<SystemConfigSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public SystemConfigSelect<?> asc() {
		return id().asc();
	}
}
