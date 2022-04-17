package br.tcc.controllers;

import br.tcc.service.MudarSenhaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("mudar-senha/")
public class MudarSenhaController extends ControllerModelo {

	@Autowired MudarSenhaService mudarSenhaService;

	@Override
	protected MudarSenhaService getService() {
		return mudarSenhaService;
	}
}
