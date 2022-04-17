package br.tcc.controllers;

import br.tcc.service.PerfilComandoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("perfil-comando/")
public class PerfilComandoController extends ControllerModelo {

	@Autowired PerfilComandoService perfilComandoService;

	@Override
	protected PerfilComandoService getService() {
		return perfilComandoService;
	}
}
