package br.tcc.controllers;

import br.tcc.service.AuditoriaCampoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("auditoria-campo/")
public class AuditoriaCampoController extends ControllerModelo {

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	protected AuditoriaCampoService getService() {
		return auditoriaCampoService;
	}
}
