package br.tcc.controllers;

import br.tcc.service.ArquivoPathService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("arquivo-path/")
public class ArquivoPathController extends ControllerModelo {

	@Autowired ArquivoPathService arquivoPathService;

	@Override
	protected ArquivoPathService getService() {
		return arquivoPathService;
	}
}
