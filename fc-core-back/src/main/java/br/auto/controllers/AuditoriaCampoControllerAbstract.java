package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.AuditoriaCampoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuditoriaCampoControllerAbstract extends ControllerModelo {

	@Autowired
	protected AuditoriaCampoService auditoriaCampoService;

	@Override
	protected AuditoriaCampoService getService() {
		return auditoriaCampoService;
	}
}
