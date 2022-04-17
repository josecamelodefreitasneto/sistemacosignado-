package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.PerfilComandoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PerfilComandoControllerAbstract extends ControllerModelo {

	@Autowired
	protected PerfilComandoService perfilComandoService;

	@Override
	protected PerfilComandoService getService() {
		return perfilComandoService;
	}
}
