package br.tcc.select;

import br.tcc.model.Dia;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectDate;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class DiaSelect<ORIGEM> extends SelectBase<ORIGEM, Dia, DiaSelect<ORIGEM>> {
	public DiaSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Dia.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<DiaSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectDate<DiaSelect<?>> data() {
		return new SelectDate<>(this, "data");
	}
	public SelectString<DiaSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectInteger<DiaSelect<?>> diaDaSemana() {
		return new SelectInteger<>(this, "diaDaSemana");
	}
	public AnoMesSelect<DiaSelect<?>> anoMes() {
		return new AnoMesSelect<>(this, getC(), getPrefixo() + ".anoMes" );
	}
	public SelectBoolean<DiaSelect<?>> feriado() {
		return new SelectBoolean<>(this, "feriado");
	}
	public SelectBoolean<DiaSelect<?>> diaUtil() {
		return new SelectBoolean<>(this, "diaUtil");
	}
	public SelectBoolean<DiaSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<DiaSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<DiaSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public DiaSelect<?> asc() {
		return id().asc();
	}
}
