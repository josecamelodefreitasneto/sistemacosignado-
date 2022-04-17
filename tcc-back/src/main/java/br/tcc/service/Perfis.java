package br.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.tcc.model.Perfil;

@Component
public class Perfis {

	private static final int ADMINISTRADOR = 1;
	private static final int ATENDENTE = 2;
	
	@Autowired PerfilService perfilService;
	
	public Perfil administrador() {
		return perfilService.find(ADMINISTRADOR);
	}
	public Perfil atendente() {
		return perfilService.find(ATENDENTE);
	}

	public void cargaInicial() {
		perfilService.add(ADMINISTRADOR, "Administrador");
		perfilService.add(ATENDENTE, "Atendente");
	}

}
