package br.tcc.select;

import br.tcc.model.ImportacaoArquivoErroMensagem;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ImportacaoArquivoErroMensagemSelect<ORIGEM> extends SelectBase<ORIGEM, ImportacaoArquivoErroMensagem, ImportacaoArquivoErroMensagemSelect<ORIGEM>> {
	public ImportacaoArquivoErroMensagemSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, ImportacaoArquivoErroMensagem.class);
	}
	public SelectInteger<ImportacaoArquivoErroMensagemSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ImportacaoArquivoErroMensagemSelect<?>> mensagem() {
		return new SelectString<>(this, "mensagem");
	}
	public SelectBoolean<ImportacaoArquivoErroMensagemSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ImportacaoArquivoErroMensagemSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ImportacaoArquivoErroMensagemSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ImportacaoArquivoErroMensagemSelect<?> asc() {
		return id().asc();
	}
}
