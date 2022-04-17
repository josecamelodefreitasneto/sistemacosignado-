package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UsuarioControllerAbstract extends ControllerModelo {

	@Autowired
	protected UsuarioService usuarioService;

	@Override
	protected UsuarioService getService() {
		return usuarioService;
	}
}
