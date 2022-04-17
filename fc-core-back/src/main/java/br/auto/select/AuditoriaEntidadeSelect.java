package br.auto.select;

import br.auto.model.AuditoriaEntidade;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class AuditoriaEntidadeSelect<ORIGEM> extends SelectBase<ORIGEM, AuditoriaEntidade, AuditoriaEntidadeSelect<ORIGEM>> {
	public AuditoriaEntidadeSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, AuditoriaEntidade.class);
	}
	public SelectInteger<AuditoriaEntidadeSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public AuditoriaTransacaoSelect<AuditoriaEntidadeSelect<?>> transacao() {
		return new AuditoriaTransacaoSelect<>(this, getC(), getPrefixo() + ".transacao" );
	}
	public EntidadeSelect<AuditoriaEntidadeSelect<?>> entidade() {
		return new EntidadeSelect<>(this, getC(), getPrefixo() + ".entidade" );
	}
	public SelectInteger<AuditoriaEntidadeSelect<?>> tipo() {
		return new SelectInteger<>(this, "tipo");
	}
	public SelectInteger<AuditoriaEntidadeSelect<?>> registro() {
		return new SelectInteger<>(this, "registro");
	}
	public SelectInteger<AuditoriaEntidadeSelect<?>> numeroDaOperacao() {
		return new SelectInteger<>(this, "numeroDaOperacao");
	}
	public SelectBoolean<AuditoriaEntidadeSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<AuditoriaEntidadeSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<AuditoriaEntidadeSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public AuditoriaEntidadeSelect<?> asc() {
		return id().asc();
	}
}
