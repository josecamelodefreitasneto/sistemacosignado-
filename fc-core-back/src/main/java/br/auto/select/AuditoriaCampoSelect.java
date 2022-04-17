package br.auto.select;

import br.auto.model.AuditoriaCampo;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class AuditoriaCampoSelect<ORIGEM> extends SelectBase<ORIGEM, AuditoriaCampo, AuditoriaCampoSelect<ORIGEM>> {
	public AuditoriaCampoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, AuditoriaCampo.class);
	}
	public SelectInteger<AuditoriaCampoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public AuditoriaEntidadeSelect<AuditoriaCampoSelect<?>> auditoriaEntidade() {
		return new AuditoriaEntidadeSelect<>(this, getC(), getPrefixo() + ".auditoriaEntidade" );
	}
	public CampoSelect<AuditoriaCampoSelect<?>> campo() {
		return new CampoSelect<>(this, getC(), getPrefixo() + ".campo" );
	}
	public SelectString<AuditoriaCampoSelect<?>> de() {
		return new SelectString<>(this, "de");
	}
	public SelectString<AuditoriaCampoSelect<?>> para() {
		return new SelectString<>(this, "para");
	}
	public SelectBoolean<AuditoriaCampoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<AuditoriaCampoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<AuditoriaCampoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public AuditoriaCampoSelect<?> asc() {
		return id().asc();
	}
}
