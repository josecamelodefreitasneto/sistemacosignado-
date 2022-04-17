package br.impl.service;

import br.auto.model.ImportacaoArquivoErroMensagem;
import br.auto.service.ImportacaoArquivoErroMensagemServiceAbstract;
import gm.utils.string.UString;

import org.springframework.stereotype.Component;

@Component
public class ImportacaoArquivoErroMensagemService extends ImportacaoArquivoErroMensagemServiceAbstract {
	
	public static final int LINHA_REPETIDA_ID = 1;
	public static final String LINHA_REPETIDA_TEXT = "Linha repetida";
	
	public ImportacaoArquivoErroMensagem get(String msg) {
		String busca = UString.toCampoBusca(msg);
		ImportacaoArquivoErroMensagem o = select().busca().eq(busca).unique();
		if (o == null) {
			o = newO();
			o.setMensagem(msg);
			o.setBusca(busca);
			o = insertSemAuditoria(o);
		}
		return o;
	}

	public void cargaInicial() {
		get(LINHA_REPETIDA_TEXT);
	}
	
}