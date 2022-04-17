package br.tcc.controllers;

import br.tcc.service.PerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("perfil/")
public class PerfilController extends ControllerModelo {

	@Autowired PerfilService perfilService;

	@Override
	protected PerfilService getService() {
		return perfilService;
	}
}
