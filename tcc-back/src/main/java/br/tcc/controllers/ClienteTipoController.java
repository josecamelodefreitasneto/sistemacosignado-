package br.tcc.controllers;

import br.tcc.service.ClienteTipoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("cliente-tipo/")
public class ClienteTipoController extends ControllerModelo {

	@Autowired ClienteTipoService clienteTipoService;

	@Override
	protected ClienteTipoService getService() {
		return clienteTipoService;
	}
}
