package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ImportacaoArquivoErroMensagemService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ImportacaoArquivoErroMensagemControllerAbstract extends ControllerModelo {

	@Autowired
	protected ImportacaoArquivoErroMensagemService importacaoArquivoErroMensagemService;

	@Override
	protected ImportacaoArquivoErroMensagemService getService() {
		return importacaoArquivoErroMensagemService;
	}
}
