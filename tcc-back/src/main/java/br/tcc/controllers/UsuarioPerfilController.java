package br.tcc.controllers;

import br.tcc.service.UsuarioPerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("usuario-perfil/")
public class UsuarioPerfilController extends ControllerModelo {

	@Autowired UsuarioPerfilService usuarioPerfilService;

	@Override
	protected UsuarioPerfilService getService() {
		return usuarioPerfilService;
	}
}
