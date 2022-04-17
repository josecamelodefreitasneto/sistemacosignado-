package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ImportacaoArquivoErroService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ImportacaoArquivoErroControllerAbstract extends ControllerModelo {

	@Autowired
	protected ImportacaoArquivoErroService importacaoArquivoErroService;

	@Override
	protected ImportacaoArquivoErroService getService() {
		return importacaoArquivoErroService;
	}
}
