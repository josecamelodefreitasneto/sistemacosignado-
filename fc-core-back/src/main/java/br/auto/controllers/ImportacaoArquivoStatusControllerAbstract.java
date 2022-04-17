package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ImportacaoArquivoStatusService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ImportacaoArquivoStatusControllerAbstract extends ControllerModelo {

	@Autowired
	protected ImportacaoArquivoStatusService importacaoArquivoStatusService;

	@Override
	protected ImportacaoArquivoStatusService getService() {
		return importacaoArquivoStatusService;
	}
}
