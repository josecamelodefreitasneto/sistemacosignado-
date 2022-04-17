package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ImportacaoArquivoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ImportacaoArquivoControllerAbstract extends ControllerModelo {

	@Autowired
	protected ImportacaoArquivoService importacaoArquivoService;

	@Override
	protected ImportacaoArquivoService getService() {
		return importacaoArquivoService;
	}
}
