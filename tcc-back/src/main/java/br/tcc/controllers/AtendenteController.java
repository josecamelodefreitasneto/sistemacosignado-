package br.tcc.controllers;

import br.tcc.service.AtendenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("atendente/")
public class AtendenteController extends ControllerModelo {

	@Autowired AtendenteService atendenteService;

	@Override
	protected AtendenteService getService() {
		return atendenteService;
	}
}
