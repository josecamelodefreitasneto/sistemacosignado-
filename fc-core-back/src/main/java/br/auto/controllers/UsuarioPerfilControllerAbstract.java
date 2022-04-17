package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.UsuarioPerfilService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UsuarioPerfilControllerAbstract extends ControllerModelo {

	@Autowired
	protected UsuarioPerfilService usuarioPerfilService;

	@Override
	protected UsuarioPerfilService getService() {
		return usuarioPerfilService;
	}
}
