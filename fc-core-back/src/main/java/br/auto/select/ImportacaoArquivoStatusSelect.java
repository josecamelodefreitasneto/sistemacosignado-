package br.auto.select;

import br.auto.model.ImportacaoArquivoStatus;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ImportacaoArquivoStatusSelect<ORIGEM> extends SelectBase<ORIGEM, ImportacaoArquivoStatus, ImportacaoArquivoStatusSelect<ORIGEM>> {
	public ImportacaoArquivoStatusSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ImportacaoArquivoStatus.class);
	}
	@Override
	protected void beforeSelect() {
		nome().asc();
	}
	public SelectInteger<ImportacaoArquivoStatusSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ImportacaoArquivoStatusSelect<?>> nome() {
		return new SelectString<>(this, "nome");
	}
	public SelectBoolean<ImportacaoArquivoStatusSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ImportacaoArquivoStatusSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ImportacaoArquivoStatusSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ImportacaoArquivoStatusSelect<?> asc() {
		return id().asc();
	}
}
