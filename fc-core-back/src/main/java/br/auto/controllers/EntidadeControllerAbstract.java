package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.EntidadeService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EntidadeControllerAbstract extends ControllerModelo {

	@Autowired
	protected EntidadeService entidadeService;

	@Override
	protected EntidadeService getService() {
		return entidadeService;
	}
}
