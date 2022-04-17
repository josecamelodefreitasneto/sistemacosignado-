package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PerfilControllerAbstract extends ControllerModelo {

	@Autowired
	protected PerfilService perfilService;

	@Override
	protected PerfilService getService() {
		return perfilService;
	}
}
