package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.TipoAuditoriaEntidadeService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TipoAuditoriaEntidadeControllerAbstract extends ControllerModelo {

	@Autowired
	protected TipoAuditoriaEntidadeService tipoAuditoriaEntidadeService;

	@Override
	protected TipoAuditoriaEntidadeService getService() {
		return tipoAuditoriaEntidadeService;
	}
}
