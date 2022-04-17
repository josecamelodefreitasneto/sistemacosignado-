package br.tcc.controllers;

import br.tcc.service.ArquivoExtensaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("arquivo-extensao/")
public class ArquivoExtensaoController extends ControllerModelo {

	@Autowired ArquivoExtensaoService arquivoExtensaoService;

	@Override
	protected ArquivoExtensaoService getService() {
		return arquivoExtensaoService;
	}
}
