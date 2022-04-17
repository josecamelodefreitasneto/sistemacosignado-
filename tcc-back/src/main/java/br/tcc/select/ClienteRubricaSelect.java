package br.tcc.select;

import br.tcc.model.ClienteRubrica;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectNumeric2;
import gm.utils.jpa.select.SelectString;

public class ClienteRubricaSelect<ORIGEM> extends SelectBase<ORIGEM, ClienteRubrica, ClienteRubricaSelect<ORIGEM>> {
	public ClienteRubricaSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ClienteRubrica.class);
	}
	public SelectInteger<ClienteRubricaSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public ClienteSelect<ClienteRubricaSelect<?>> cliente() {
		return new ClienteSelect<>(this, getC(), getPrefixo() + ".cliente" );
	}
	public RubricaSelect<ClienteRubricaSelect<?>> rubrica() {
		return new RubricaSelect<>(this, getC(), getPrefixo() + ".rubrica" );
	}
	public SelectNumeric2<ClienteRubricaSelect<?>> valor() {
		return new SelectNumeric2<>(this, "valor");
	}
	public SelectBoolean<ClienteRubricaSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ClienteRubricaSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ClienteRubricaSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ClienteRubricaSelect<?> asc() {
		return id().asc();
	}
}
