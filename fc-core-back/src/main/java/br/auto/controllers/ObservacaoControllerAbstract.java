package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.ObservacaoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ObservacaoControllerAbstract extends ControllerModelo {

	@Autowired
	protected ObservacaoService observacaoService;

	@Override
	protected ObservacaoService getService() {
		return observacaoService;
	}
}
