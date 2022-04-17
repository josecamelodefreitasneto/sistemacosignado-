package br.tcc.controllers;

import br.tcc.service.ClienteTipoSimulacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("cliente-tipo-simulacao/")
public class ClienteTipoSimulacaoController extends ControllerModelo {

	@Autowired ClienteTipoSimulacaoService clienteTipoSimulacaoService;

	@Override
	protected ClienteTipoSimulacaoService getService() {
		return clienteTipoSimulacaoService;
	}
}
