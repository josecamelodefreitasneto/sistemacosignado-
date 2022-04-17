package br.tcc.controllers;

import br.tcc.service.TelefoneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("telefone/")
public class TelefoneController extends ControllerModelo {

	@Autowired TelefoneService telefoneService;

	@Override
	protected TelefoneService getService() {
		return telefoneService;
	}
}
