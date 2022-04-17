package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class LoginControllerAbstract extends ControllerModelo {

	@Autowired
	protected LoginService loginService;

	@Override
	protected LoginService getService() {
		return loginService;
	}
}
