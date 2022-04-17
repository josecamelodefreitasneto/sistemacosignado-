package br.tcc.controllers;

import br.tcc.service.AuditoriaTransacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("auditoria-transacao/")
public class AuditoriaTransacaoController extends ControllerModelo {

	@Autowired AuditoriaTransacaoService auditoriaTransacaoService;

	@Override
	protected AuditoriaTransacaoService getService() {
		return auditoriaTransacaoService;
	}
}
