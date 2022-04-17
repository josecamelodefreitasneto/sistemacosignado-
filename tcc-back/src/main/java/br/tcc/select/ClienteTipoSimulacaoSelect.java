package br.tcc.select;

import br.tcc.model.ClienteTipoSimulacao;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ClienteTipoSimulacaoSelect<ORIGEM> extends SelectBase<ORIGEM, ClienteTipoSimulacao, ClienteTipoSimulacaoSelect<ORIGEM>> {
	public ClienteTipoSimulacaoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ClienteTipoSimulacao.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ClienteTipoSimulacaoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ClienteTipoSimulacaoSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<ClienteTipoSimulacaoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ClienteTipoSimulacaoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ClienteTipoSimulacaoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ClienteTipoSimulacaoSelect<?> asc() {
		return id().asc();
	}
}
