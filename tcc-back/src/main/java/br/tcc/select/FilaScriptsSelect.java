package br.tcc.select;

import br.tcc.model.FilaScripts;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class FilaScriptsSelect<ORIGEM> extends SelectBase<ORIGEM, FilaScripts, FilaScriptsSelect<ORIGEM>> {
	public FilaScriptsSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, FilaScripts.class);
	}
	public SelectInteger<FilaScriptsSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public AuditoriaTransacaoSelect<FilaScriptsSelect<?>> operacao() {
		return new AuditoriaTransacaoSelect<>(this, getC(), getPrefixo() + ".operacao" );
	}
	public SelectString<FilaScriptsSelect<?>> sql() {
		return new SelectString<>(this, "sql");
	}
	public SelectBoolean<FilaScriptsSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<FilaScriptsSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<FilaScriptsSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public FilaScriptsSelect<?> asc() {
		return id().asc();
	}
}
