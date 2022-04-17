package br.tcc.controllers;

import br.tcc.service.TipoAuditoriaEntidadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("tipo-auditoria-entidade/")
public class TipoAuditoriaEntidadeController extends ControllerModelo {

	@Autowired TipoAuditoriaEntidadeService tipoAuditoriaEntidadeService;

	@Override
	protected TipoAuditoriaEntidadeService getService() {
		return tipoAuditoriaEntidadeService;
	}
}
