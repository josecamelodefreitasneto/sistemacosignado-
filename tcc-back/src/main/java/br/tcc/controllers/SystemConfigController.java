package br.tcc.controllers;

import br.tcc.service.SystemConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("system-config/")
public class SystemConfigController extends ControllerModelo {

	@Autowired SystemConfigService systemConfigService;

	@Override
	protected SystemConfigService getService() {
		return systemConfigService;
	}
}
