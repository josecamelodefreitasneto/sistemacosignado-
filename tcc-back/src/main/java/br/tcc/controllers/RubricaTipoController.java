package br.tcc.controllers;

import br.tcc.service.RubricaTipoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("rubrica-tipo/")
public class RubricaTipoController extends ControllerModelo {

	@Autowired RubricaTipoService rubricaTipoService;

	@Override
	protected RubricaTipoService getService() {
		return rubricaTipoService;
	}
}
