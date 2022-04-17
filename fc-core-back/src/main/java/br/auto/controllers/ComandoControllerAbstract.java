package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ComandoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ComandoControllerAbstract extends ControllerModelo {

	@Autowired
	protected ComandoService comandoService;

	@Override
	protected ComandoService getService() {
		return comandoService;
	}
}
