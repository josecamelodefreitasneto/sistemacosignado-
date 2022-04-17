package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ArquivoControllerAbstract extends ControllerModelo {

	@Autowired
	protected ArquivoService arquivoService;

	@Override
	protected ArquivoService getService() {
		return arquivoService;
	}
}
