package br.tcc.controllers;

import br.tcc.service.AuditoriaEntidadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("auditoria-entidade/")
public class AuditoriaEntidadeController extends ControllerModelo {

	@Autowired AuditoriaEntidadeService auditoriaEntidadeService;

	@Override
	protected AuditoriaEntidadeService getService() {
		return auditoriaEntidadeService;
	}
}
