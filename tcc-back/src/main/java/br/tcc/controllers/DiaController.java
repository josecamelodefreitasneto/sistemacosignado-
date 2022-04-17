package br.tcc.controllers;

import br.tcc.service.DiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("dia/")
public class DiaController extends ControllerModelo {

	@Autowired DiaService diaService;

	@Override
	protected DiaService getService() {
		return diaService;
	}
}
