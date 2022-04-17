package br.auto.select;

import br.auto.model.AuditoriaTransacao;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectDate;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectNumeric3;
import gm.utils.jpa.select.SelectString;

public class AuditoriaTransacaoSelect<ORIGEM> extends SelectBase<ORIGEM, AuditoriaTransacao, AuditoriaTransacaoSelect<ORIGEM>> {
	public AuditoriaTransacaoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, AuditoriaTransacao.class);
	}
	public SelectInteger<AuditoriaTransacaoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public LoginSelect<AuditoriaTransacaoSelect<?>> login() {
		return new LoginSelect<>(this, getC(), getPrefixo() + ".login" );
	}
	public ComandoSelect<AuditoriaTransacaoSelect<?>> comando() {
		return new ComandoSelect<>(this, getC(), getPrefixo() + ".comando" );
	}
	public SelectDate<AuditoriaTransacaoSelect<?>> data() {
		return new SelectDate<>(this, "data");
	}
	public SelectNumeric3<AuditoriaTransacaoSelect<?>> tempo() {
		return new SelectNumeric3<>(this, "tempo");
	}
	public SelectBoolean<AuditoriaTransacaoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<AuditoriaTransacaoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<AuditoriaTransacaoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public AuditoriaTransacaoSelect<?> asc() {
		return id().asc();
	}
}
