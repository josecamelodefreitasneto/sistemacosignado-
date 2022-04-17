package br.tcc.select;

import br.tcc.model.Indice;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectNumeric5;
import gm.utils.jpa.select.SelectString;

public class IndiceSelect<ORIGEM> extends SelectBase<ORIGEM, Indice, IndiceSelect<ORIGEM>> {
	public IndiceSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Indice.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<IndiceSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<IndiceSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectNumeric5<IndiceSelect<?>> em12() {
		return new SelectNumeric5<>(this, "em12");
	}
	public SelectNumeric5<IndiceSelect<?>> em15() {
		return new SelectNumeric5<>(this, "em15");
	}
	public SelectNumeric5<IndiceSelect<?>> em18() {
		return new SelectNumeric5<>(this, "em18");
	}
	public SelectNumeric5<IndiceSelect<?>> em24() {
		return new SelectNumeric5<>(this, "em24");
	}
	public SelectNumeric5<IndiceSelect<?>> em30() {
		return new SelectNumeric5<>(this, "em30");
	}
	public SelectNumeric5<IndiceSelect<?>> em36() {
		return new SelectNumeric5<>(this, "em36");
	}
	public SelectNumeric5<IndiceSelect<?>> em48() {
		return new SelectNumeric5<>(this, "em48");
	}
	public SelectNumeric5<IndiceSelect<?>> em60() {
		return new SelectNumeric5<>(this, "em60");
	}
	public SelectNumeric5<IndiceSelect<?>> em72() {
		return new SelectNumeric5<>(this, "em72");
	}
	public SelectNumeric5<IndiceSelect<?>> em84() {
		return new SelectNumeric5<>(this, "em84");
	}
	public SelectNumeric5<IndiceSelect<?>> em96() {
		return new SelectNumeric5<>(this, "em96");
	}
	public SelectBoolean<IndiceSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<IndiceSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<IndiceSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public IndiceSelect<?> asc() {
		return id().asc();
	}
}
