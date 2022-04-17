package br.tcc.controllers;

import br.tcc.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("usuario/")
public class UsuarioController extends ControllerModelo {

	@Autowired UsuarioService usuarioService;

	@Override
	protected UsuarioService getService() {
		return usuarioService;
	}
}
