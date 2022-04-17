package br.tcc.select;

import br.tcc.model.ClienteSimulacao;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectNumeric2;
import gm.utils.jpa.select.SelectNumeric5;
import gm.utils.jpa.select.SelectString;

public class ClienteSimulacaoSelect<ORIGEM> extends SelectBase<ORIGEM, ClienteSimulacao, ClienteSimulacaoSelect<ORIGEM>> {
	public ClienteSimulacaoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ClienteSimulacao.class);
	}
	public SelectInteger<ClienteSimulacaoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public ClienteSelect<ClienteSimulacaoSelect<?>> cliente() {
		return new ClienteSelect<>(this, getC(), getPrefixo() + ".cliente" );
	}
	public SelectInteger<ClienteSimulacaoSelect<?>> parcelas() {
		return new SelectInteger<>(this, "parcelas");
	}
	public SelectNumeric5<ClienteSimulacaoSelect<?>> indice() {
		return new SelectNumeric5<>(this, "indice");
	}
	public SelectNumeric2<ClienteSimulacaoSelect<?>> valor() {
		return new SelectNumeric2<>(this, "valor");
	}
	public SelectBoolean<ClienteSimulacaoSelect<?>> contratado() {
		return new SelectBoolean<>(this, "contratado");
	}
	public SelectBoolean<ClienteSimulacaoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ClienteSimulacaoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ClienteSimulacaoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ClienteSimulacaoSelect<?> asc() {
		return id().asc();
	}
}
