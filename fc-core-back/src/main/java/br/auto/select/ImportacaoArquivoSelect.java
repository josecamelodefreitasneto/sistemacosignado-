package br.auto.select;

import br.auto.model.ImportacaoArquivo;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ImportacaoArquivoSelect<ORIGEM> extends SelectBase<ORIGEM, ImportacaoArquivo, ImportacaoArquivoSelect<ORIGEM>> {
	public ImportacaoArquivoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ImportacaoArquivo.class);
	}
	public SelectInteger<ImportacaoArquivoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public ArquivoSelect<ImportacaoArquivoSelect<?>> arquivo() {
		return new ArquivoSelect<>(this, getC(), getPrefixo() + ".arquivo" );
	}
	public SelectString<ImportacaoArquivoSelect<?>> delimitador() {
		return new SelectString<>(this, "delimitador");
	}
	public SelectBoolean<ImportacaoArquivoSelect<?>> atualizarRegistrosExistentes() {
		return new SelectBoolean<>(this, "atualizarRegistrosExistentes");
	}
	public EntidadeSelect<ImportacaoArquivoSelect<?>> entidade() {
		return new EntidadeSelect<>(this, getC(), getPrefixo() + ".entidade" );
	}
	public SelectInteger<ImportacaoArquivoSelect<?>> status() {
		return new SelectInteger<>(this, "status");
	}
	public SelectInteger<ImportacaoArquivoSelect<?>> totalDeLinhas() {
		return new SelectInteger<>(this, "totalDeLinhas");
	}
	public SelectInteger<ImportacaoArquivoSelect<?>> processadosComSucesso() {
		return new SelectInteger<>(this, "processadosComSucesso");
	}
	public SelectInteger<ImportacaoArquivoSelect<?>> processadosComErro() {
		return new SelectInteger<>(this, "processadosComErro");
	}
	public ArquivoSelect<ImportacaoArquivoSelect<?>> arquivoDeErros() {
		return new ArquivoSelect<>(this, getC(), getPrefixo() + ".arquivoDeErros" );
	}
	public SelectBoolean<ImportacaoArquivoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ImportacaoArquivoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ImportacaoArquivoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ImportacaoArquivoSelect<?> asc() {
		return id().asc();
	}
}
