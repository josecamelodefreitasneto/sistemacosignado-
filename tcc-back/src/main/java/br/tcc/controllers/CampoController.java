package br.tcc.controllers;

import br.tcc.service.CampoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("campo/")
public class CampoController extends ControllerModelo {

	@Autowired CampoService campoService;

	@Override
	protected CampoService getService() {
		return campoService;
	}
}
