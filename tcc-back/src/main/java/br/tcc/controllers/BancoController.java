package br.tcc.controllers;

import br.tcc.service.BancoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("banco/")
public class BancoController extends ControllerModelo {

	@Autowired BancoService bancoService;

	@Override
	protected BancoService getService() {
		return bancoService;
	}
}
