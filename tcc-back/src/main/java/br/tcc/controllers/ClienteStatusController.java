package br.tcc.controllers;

import br.tcc.service.ClienteStatusService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("cliente-status/")
public class ClienteStatusController extends ControllerModelo {

	@Autowired ClienteStatusService clienteStatusService;

	@Override
	protected ClienteStatusService getService() {
		return clienteStatusService;
	}
}
