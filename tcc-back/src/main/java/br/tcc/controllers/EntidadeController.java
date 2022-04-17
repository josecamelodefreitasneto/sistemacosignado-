package br.tcc.controllers;

import br.tcc.service.EntidadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("entidade/")
public class EntidadeController extends ControllerModelo {

	@Autowired EntidadeService entidadeService;

	@Override
	protected EntidadeService getService() {
		return entidadeService;
	}
}
