package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.AuditoriaTransacaoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuditoriaTransacaoControllerAbstract extends ControllerModelo {

	@Autowired
	protected AuditoriaTransacaoService auditoriaTransacaoService;

	@Override
	protected AuditoriaTransacaoService getService() {
		return auditoriaTransacaoService;
	}
}
