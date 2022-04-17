package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ArquivoExtensaoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ArquivoExtensaoControllerAbstract extends ControllerModelo {

	@Autowired
	protected ArquivoExtensaoService arquivoExtensaoService;

	@Override
	protected ArquivoExtensaoService getService() {
		return arquivoExtensaoService;
	}
}
