package br.auto.select;

import br.auto.model.ImportacaoArquivoErro;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ImportacaoArquivoErroSelect<ORIGEM> extends SelectBase<ORIGEM, ImportacaoArquivoErro, ImportacaoArquivoErroSelect<ORIGEM>> {
	public ImportacaoArquivoErroSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ImportacaoArquivoErro.class);
	}
	public SelectInteger<ImportacaoArquivoErroSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public ImportacaoArquivoSelect<ImportacaoArquivoErroSelect<?>> importacaoArquivo() {
		return new ImportacaoArquivoSelect<>(this, getC(), getPrefixo() + ".importacaoArquivo" );
	}
	public SelectInteger<ImportacaoArquivoErroSelect<?>> linha() {
		return new SelectInteger<>(this, "linha");
	}
	public ImportacaoArquivoErroMensagemSelect<ImportacaoArquivoErroSelect<?>> erro() {
		return new ImportacaoArquivoErroMensagemSelect<>(this, getC(), getPrefixo() + ".erro" );
	}
	public SelectBoolean<ImportacaoArquivoErroSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ImportacaoArquivoErroSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ImportacaoArquivoErroSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ImportacaoArquivoErroSelect<?> asc() {
		return id().asc();
	}
}
