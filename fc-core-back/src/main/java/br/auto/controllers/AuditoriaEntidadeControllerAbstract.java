package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.AuditoriaEntidadeService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuditoriaEntidadeControllerAbstract extends ControllerModelo {

	@Autowired
	protected AuditoriaEntidadeService auditoriaEntidadeService;

	@Override
	protected AuditoriaEntidadeService getService() {
		return auditoriaEntidadeService;
	}
}
