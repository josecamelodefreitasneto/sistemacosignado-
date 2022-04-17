package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.EsqueciSenhaService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EsqueciSenhaControllerAbstract extends ControllerModelo {

	@Autowired
	protected EsqueciSenhaService esqueciSenhaService;

	@Override
	protected EsqueciSenhaService getService() {
		return esqueciSenhaService;
	}
}
