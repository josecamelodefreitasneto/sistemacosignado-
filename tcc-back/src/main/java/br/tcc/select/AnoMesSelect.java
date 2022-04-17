package br.tcc.select;

import br.tcc.model.AnoMes;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class AnoMesSelect<ORIGEM> extends SelectBase<ORIGEM, AnoMes, AnoMesSelect<ORIGEM>> {
	public AnoMesSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, AnoMes.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<AnoMesSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<AnoMesSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectInteger<AnoMesSelect<?>> ano() {
		return new SelectInteger<>(this, "ano");
	}
	public SelectInteger<AnoMesSelect<?>> mes() {
		return new SelectInteger<>(this, "mes");
	}
	public SelectBoolean<AnoMesSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<AnoMesSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<AnoMesSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public AnoMesSelect<?> asc() {
		return id().asc();
	}
}
