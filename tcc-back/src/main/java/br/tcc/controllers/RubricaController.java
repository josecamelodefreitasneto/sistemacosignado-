package br.tcc.controllers;

import br.tcc.service.RubricaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("rubrica/")
public class RubricaController extends ControllerModelo {

	@Autowired RubricaService rubricaService;

	@Override
	protected RubricaService getService() {
		return rubricaService;
	}
}
