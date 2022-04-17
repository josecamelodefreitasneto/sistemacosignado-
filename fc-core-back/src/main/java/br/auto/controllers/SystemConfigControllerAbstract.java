package br.auto.controllers;

import br.controllers.ControllerModelo;
import br.impl.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SystemConfigControllerAbstract extends ControllerModelo {

	@Autowired
	protected SystemConfigService systemConfigService;

	@Override
	protected SystemConfigService getService() {
		return systemConfigService;
	}
}
