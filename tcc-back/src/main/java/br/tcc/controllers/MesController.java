package br.tcc.controllers;

import br.tcc.service.MesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("mes/")
public class MesController extends ControllerModelo {

	@Autowired MesService mesService;

	@Override
	protected MesService getService() {
		return mesService;
	}
}
