package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.FilaScriptsService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class FilaScriptsControllerAbstract extends ControllerModelo {

	@Autowired
	protected FilaScriptsService filaScriptsService;

	@Override
	protected FilaScriptsService getService() {
		return filaScriptsService;
	}
}
