package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ArquivoPathService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ArquivoPathControllerAbstract extends ControllerModelo {

	@Autowired
	protected ArquivoPathService arquivoPathService;

	@Override
	protected ArquivoPathService getService() {
		return arquivoPathService;
	}
}
