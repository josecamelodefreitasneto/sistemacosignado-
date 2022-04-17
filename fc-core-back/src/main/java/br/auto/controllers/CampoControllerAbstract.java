package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.CampoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CampoControllerAbstract extends ControllerModelo {

	@Autowired
	protected CampoService campoService;

	@Override
	protected CampoService getService() {
		return campoService;
	}
}
