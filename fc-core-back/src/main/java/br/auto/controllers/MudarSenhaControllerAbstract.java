package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.MudarSenhaService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MudarSenhaControllerAbstract extends ControllerModelo {

	@Autowired
	protected MudarSenhaService mudarSenhaService;

	@Override
	protected MudarSenhaService getService() {
		return mudarSenhaService;
	}
}
