package br.tcc.select;

import br.tcc.model.Cep;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class CepSelect<ORIGEM> extends SelectBase<ORIGEM, Cep, CepSelect<ORIGEM>> {
	public CepSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Cep.class);
	}
	public SelectInteger<CepSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<CepSelect<?>> numero() {
		return new SelectString<>(this, "numero");
	}
	public SelectString<CepSelect<?>> uf() {
		return new SelectString<>(this, "uf");
	}
	public SelectString<CepSelect<?>> cidade() {
		return new SelectString<>(this, "cidade");
	}
	public SelectString<CepSelect<?>> bairro() {
		return new SelectString<>(this, "bairro");
	}
	public SelectString<CepSelect<?>> logradouro() {
		return new SelectString<>(this, "logradouro");
	}
	public SelectBoolean<CepSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<CepSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<CepSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public CepSelect<?> asc() {
		return id().asc();
	}
}
