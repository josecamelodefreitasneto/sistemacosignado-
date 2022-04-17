package br.tcc.select;

import br.tcc.model.Rubrica;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class RubricaSelect<ORIGEM> extends SelectBase<ORIGEM, Rubrica, RubricaSelect<ORIGEM>> {
	public RubricaSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Rubrica.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<RubricaSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectInteger<RubricaSelect<?>> tipo() {
		return new SelectInteger<>(this, "tipo");
	}
	public SelectString<RubricaSelect<?>> codigo() {
		return new SelectString<>(this, "codigo");
	}
	public SelectString<RubricaSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<RubricaSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<RubricaSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<RubricaSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public RubricaSelect<?> asc() {
		return id().asc();
	}
}
