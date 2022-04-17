package br.tcc.controllers;

import br.tcc.service.ComandoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("comando/")
public class ComandoController extends ControllerModelo {

	@Autowired ComandoService comandoService;

	@Override
	protected ComandoService getService() {
		return comandoService;
	}
}
